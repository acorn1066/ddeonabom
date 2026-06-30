package kh.ddeonabom.review.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.admin.model.service.AdminService;
import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.landmark.model.service.LandmarkService;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.reply.model.vo.Reply;
import kh.ddeonabom.reply.service.ReplyService;
import kh.ddeonabom.review.model.service.ReviewService;
import kh.ddeonabom.review.model.vo.Image;
import kh.ddeonabom.review.model.vo.Review;
import kh.ddeonabom.review.model.vo.ReviewSub;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import kh.ddeonabom.schedule.model.vo.ScheduleSub;
import kh.ddeonabom.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 

public class ReviewController {
	private final ReviewService reviewService;
	private final ReplyService replyService;
	private final AdminService aService;
	private final ScheduleService sService;
	private final LandmarkService landmarkService;
	
	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}") 
	private String bucket;

	@Value("${kakao.api.key}")
	private String kakaoApiKey;
	
	
	
	@GetMapping("/reviews/list")
	public String reviewListPage(Model model) {
        ArrayList<AdminNotice> noticeList = aService.selectTopNotice();
        model.addAttribute("noticeList", noticeList);   
        return "views/review/reviews";
    }
	
// ==================================================================== 공지사항 ==============================================================
	@GetMapping("/reviews/notice")
	public String noticeDetail(@RequestParam("noticeNo") int noticeNo, Model model) {
	    AdminNotice notice = aService.selectNotice(noticeNo);
	    model.addAttribute("notice", notice);
	    model.addAttribute("from", "reviews");
	    return "views/admin/memberNotice";
	}
// ==================================================================== 공지사항 ==============================================================
	
	@GetMapping("/selectReviewList")
    @ResponseBody 
    public Map<String, Object> selectReviewList(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
    											@RequestParam(value="region", defaultValue="") String region,
            									@RequestParam(value="keyword", defaultValue="") String keyword,
            									@RequestParam(value = "sort", defaultValue = "latest") String sort,
            									HttpSession session) {
        
		Member loginUser = (Member) session.getAttribute("loginUser");
	    Integer loginUserNo = (loginUser != null) ? loginUser.getMemberNo() : null;
	    
        int listCount = reviewService.selectListCount(keyword, region, loginUserNo); 
        int pageLimit = 5;   
        int boardLimit = 12;  

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, pageLimit, boardLimit);
        ArrayList<Review> list = reviewService.selectReviewList(pi, keyword, region, loginUserNo, sort);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("list", list);
        responseData.put("pi", pi);
        
        return responseData; 
    }
	
	@GetMapping("/reviews/write")
	public String reviewWrite(@RequestParam(name = "scheduleNo", required = false) Integer scheduleNo,
	                          Model model, HttpSession session) {
	    Member loginUser = (Member) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        return "redirect:/member/login?targetUrl=/reviews/write";
	    }

	    //일정에서 후기 작성하러 들어왔을 경우
	    if (scheduleNo != null) {
	    	if (scheduleNo != null) {
	    	    ScheduleMain main = sService.selectScheduleDetail(scheduleNo);
	    	    if (main != null && main.getMemberNo() == loginUser.getMemberNo()) {
	    	        List<ScheduleSub> subList = sService.selectScheduleSubList(scheduleNo);
	    	        
	    	        //일정이랑 후기랑 이름이 안맞는 구간이 있어서 수정용
	    	       
	    	        List<Map<String, Object>> mappedList = subList.stream().map(sub -> {
	    	            Map<String, Object> m = new HashMap<>();
	    	            m.put("contentTitle", sub.getTitle());
	    	            m.put("contentId", sub.getContentId());
	    	            m.put("lat", sub.getMapy() != null ? Double.parseDouble(sub.getMapy()) : 0.0);  // String → double
	    	            m.put("lng", sub.getMapx() != null ? Double.parseDouble(sub.getMapx()) : 0.0);  // String → double
	    	            return m;
	    	        }).collect(Collectors.toList());
	    	        Map<String, Object> review = new HashMap<>();
	    	        review.put("subList", mappedList);
	    	        
	    	        model.addAttribute("review", review);
	    	        model.addAttribute("scheduleTitle", main.getScheduleTitle());
	    	        model.addAttribute("scheduleStartdate", main.getScheduleStartdate());
	    	        model.addAttribute("scheduleEnddate", main.getScheduleEnddate());
	    	    }
	    	}
	    }

	    model.addAttribute("kakaoApiKey", kakaoApiKey);
	    return "views/review/write";
	}
	
	@GetMapping("/reviews/sdwrite")
	public String writeForm(@RequestParam("travelNo") Long travelNo, Model model) {
		
	    if (travelNo != null) {
	        Review review = reviewService.getTravelWithSubList(travelNo); // 제목 + subList(관광지들) 같이 조회
	        model.addAttribute("review", review);
	        
	    }
	    return "views/review/write";
	}

    @PostMapping("/reviews/insert")
    public String insertReviews(@ModelAttribute Review r, HttpServletRequest request, HttpSession session) {

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser != null) {
            r.setMemberNo(loginUser.getMemberNo());
        }
        
        int result = reviewService.insertReview(r);
        if (result > 0) {
            // [변경] 더 이상 로컬 디렉토리(C:/reviews)를 생성할 필요가 없습니다.
            
            if (r.getSubList() != null) {
                for (int i = 0; i < r.getSubList().size(); i++) {
                    ReviewSub sub = r.getSubList().get(i);
                    sub.setTravelNo(r.getTravelNo());   
                    sub.setTravelSubSeq(i + 1);

                    reviewService.insertReviewSub(sub); 

                    List<MultipartFile> cardFiles = sub.getImageFiles();
                    if (cardFiles != null) {
                        for (MultipartFile file : cardFiles) {
                            if (file != null && !file.isEmpty()) {
                                String original = file.getOriginalFilename();
                                // S3에 저장될 중복 없는 파일명 생성
                                String saved = UUID.randomUUID().toString() + "_" + original;
                                
                                try {
                                    // [변경] 1. S3 업로드를 위한 메타데이터 설정
                                    ObjectMetadata metadata = new ObjectMetadata();
                                    metadata.setContentLength(file.getSize());
                                    metadata.setContentType(file.getContentType());

                                    // [변경] 2. AWS S3로 파일 업로드 실행
                                    amazonS3.putObject(new PutObjectRequest(bucket, saved, file.getInputStream(), metadata));

                                    // [변경] 3. S3에 저장된 파일의 실제 인터넷 주소(URL) 가져오기
                                    String s3Url = amazonS3.getUrl(bucket, saved).toString();

                                    // [변경] 4. DB에 이미지 정보 저장
                                    Image img = new Image();
                                    img.setFileName(original);    // 원본 파일명
                                    img.setRenameFile(saved);     // S3에 저장된 파일명
                                    img.setImagePath(s3Url);      // [핵심] 로컬 경로 대신 S3 URL 주소를 통째로 저장!
                                    img.setTravelSubNo(sub.getTravelSubNo()); 
                                    
                                    reviewService.insertImage(img);
                                    
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    // 실패 시 예외 처리 로직 (필요시 추가)
                                }
                            }
                        }
                    }
                }
            }
            return "redirect:/reviews/list";
        } else {
            return "redirect:/reviews/write";
        }
    }

	
	
	@GetMapping("/reviews/detail")
	public String reviewDetail(@RequestParam("travelNo") int travelNo, HttpSession session, Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");
	    Integer loginUserNo = (loginUser != null) ? loginUser.getMemberNo() : null;

	    reviewService.increaseCount(travelNo);
	    Review review = reviewService.getReviewDetail(travelNo, loginUserNo);
	    

	    if (review == null) {
	        return "redirect:/reviews/list";
	    }
	    if ("MEMBER".equals(review.getVisibility()) && loginUser == null) {
	        model.addAttribute("alertMsg", "회원 전용 게시글입니다. 로그인 후 이용해주세요.");
	        return "member/login";
	    }
	    ArrayList<Reply> replyList = replyService.getReplyList(travelNo, "T");
	    model.addAttribute("replyList", replyList);
	    model.addAttribute("review", review);
	    model.addAttribute("kakaoApiKey", kakaoApiKey);

	    return "views/review/detail";
	}
	
	
	@PostMapping("/reviews/like")
	@ResponseBody
	public Map<String, Object> like(@RequestBody Map<String, Integer> param,
	                                HttpSession session) {

	    int travelNo = param.get("travelNo");

	    Member loginUser = (Member) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        throw new RuntimeException("로그인이 필요합니다.");
	    }
	    int memberNo = loginUser.getMemberNo();

	    int likeCount = reviewService.toggleLike(travelNo, memberNo);

	    Map<String, Object> result = new HashMap<>();
	    result.put("likeCount", likeCount);

	    return result;
	}
	
	@GetMapping("/reviews/update")
	public String reviewUpdateForm(@RequestParam("travelNo") int travelNo, Model model, HttpSession session) {
	    // 1. 로그인 유저 체크 (내 글이 맞는지 검증용)
	    Member loginUser = (Member) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        return "redirect:/login"; 
	    }

	    // 2. DB에서 기존 게시글 정보 + 관광지 서브 리스트(subList)까지 싹 다 긁어오기
	    // (보통 서비스 단에서 기존 상세조회 로직을 재활용합니다)
	    Review review = reviewService.reviewUpdate(travelNo);
	    
	    System.out.println("====== [백엔드 점검 1] review 객체 전체: " + review);
	    if (review != null) {
	        System.out.println("====== [백엔드 점검 2] subList 주머니 상태: " + review.getSubList());
	    }

	    // 3. 본인 글이 맞는지 검증
	    if (review.getMemberNo() != loginUser.getMemberNo()) {
	        return "redirect:/reviews/list";
	    }

	    // 4. 꺼내온 데이터를 'review'라는 이름으로 모델에 담아 화면으로 토스!
	    model.addAttribute("review", review);

	    // 후기 작성 폼(write.html)을 함께 쓰거나 별도의 update.html을 리턴합니다.
	    return "views/review/rewrite"; 
	}
	
	@PostMapping("/reviews/update")
	@ResponseBody
	public ResponseEntity<String> reviewUpdate(MultipartHttpServletRequest request) {

	    try {
	        Review review = new Review();

	        int travelNo = Integer.parseInt(request.getParameter("travelNo"));
	        review.setTravelNo(travelNo);
	        review.setTravelTitle(request.getParameter("travelTitle"));
	        review.setRegion(request.getParameter("region"));
	        review.setVisibility(request.getParameter("visibility"));

	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	        String start = request.getParameter("travelStartDate");
	        String end = request.getParameter("travelEndDate");

	        if (start != null && !start.isEmpty()) {
	            review.setTravelStartDate(new java.sql.Date(df.parse(start).getTime()));
	        }
	        if (end != null && !end.isEmpty()) {
	            review.setTravelEndDate(new java.sql.Date(df.parse(end).getTime()));
	        }

	        // =========================
	        // SUB LIST
	        // =========================
	        List<ReviewSub> subList = new ArrayList<>();

	        for (int i = 0; i < 100; i++) {

	            String title = request.getParameter("subList[" + i + "].contentTitle");
	            if (title == null) continue;

	            ReviewSub sub = new ReviewSub();

	            sub.setContentTitle(title);
	            sub.setTravelSubNo(parseIntSafe(request.getParameter("subList[" + i + "].travelSubNo")));
	            sub.setLat(parseDoubleSafe(request.getParameter("subList[" + i + "].lat")));
	            sub.setLng(parseDoubleSafe(request.getParameter("subList[" + i + "].lng")));

	            sub.setTravelSubContent(
	                request.getParameter("subList[" + i + "].travelSubContent") == null
	                    ? ""
	                    : request.getParameter("subList[" + i + "].travelSubContent")
	            );

	            sub.setRating(parseIntSafe(request.getParameter("subList[" + i + "].rating")));
	            sub.setImagePath(request.getParameter("subList[" + i + "].imagePath"));

	            int contentId = parseIntSafe(request.getParameter("subList[" + i + "].contentId"));
	            if (contentId <= 0) continue;

	            sub.setContentId(contentId);

	            // 🔥 핵심: 여기 추가해야 사진 들어감
	            List<MultipartFile> files =
	                request.getFiles("subList[" + i + "].imageFiles");

	            sub.setImageFiles(files);   // ⭐⭐⭐ 이거 없으면 사진 안 들어감

	            subList.add(sub);
	        }

	        review.setSubList(subList);

	        int result = reviewService.updateReview(review);

	        return result > 0
	            ? ResponseEntity.ok("success")
	            : ResponseEntity.status(500).body("fail");

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(500).body("error");
	    }
	}

	private int parseIntSafe(String v) {
	    try {
	        return (v == null || v.isBlank()) ? 0 : Integer.parseInt(v);
	    } catch (Exception e) {
	        return 0;
	    }
	}
	private double parseDoubleSafe(String v) {
	    try {
	        return (v == null || v.isBlank()) ? 0.0 : Double.parseDouble(v);
	    } catch (Exception e) {
	        return 0.0;
	    }
	}
	
	
	@PostMapping("/reviews/delete")
	public String deleteReview(@RequestParam("travelNo") int travelNo) {
		
		reviewService.deleteReview(travelNo);
		return "redirect:/reviews/list";
	}
	
}
