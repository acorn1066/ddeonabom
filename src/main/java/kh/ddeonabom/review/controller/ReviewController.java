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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.admin.model.service.AdminService;
import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.landmark.model.service.LandmarkService;
import kh.ddeonabom.landmark.model.vo.Landmark;
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
        int boardLimit = 9;  

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
	    	            m.put("lat", sub.getMapy());
	    	            m.put("lng", sub.getMapx());
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
	public String insertReviews(MultipartHttpServletRequest request, HttpSession session) {

		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/reviews/write";
		}

		Review r = new Review();
		r.setMemberNo(loginUser.getMemberNo());

		// 메인 정보 수동 파싱
		r.setTravelTitle(request.getParameter("travelTitle"));
		r.setRegion(request.getParameter("region"));
		r.setVisibility(request.getParameter("visibility"));

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String startDateStr = request.getParameter("travelStartDate");
		String endDateStr   = request.getParameter("travelEndDate");
		try {
			if (startDateStr != null && !startDateStr.trim().isEmpty()) {
				r.setTravelStartDate(new java.sql.Date(dateFormat.parse(startDateStr.trim()).getTime()));
			}
			if (endDateStr != null && !endDateStr.trim().isEmpty()) {
				r.setTravelEndDate(new java.sql.Date(dateFormat.parse(endDateStr.trim()).getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 서브 리스트 수동 파싱
		List<ReviewSub> subList = new ArrayList<>();
		int i = 0;
		while (request.getParameter("subList[" + i + "].contentTitle") != null) {
			ReviewSub sub = new ReviewSub();
			sub.setContentTitle(request.getParameter("subList[" + i + "].contentTitle"));
			String rawContent = request.getParameter("subList[" + i + "].travelSubContent");
		sub.setTravelSubContent((rawContent != null && !rawContent.trim().isEmpty()) ? rawContent : " ");

			String rawContentId = request.getParameter("subList[" + i + "].contentId");
			sub.setContentId(rawContentId != null && !rawContentId.trim().isEmpty() ? Integer.parseInt(rawContentId.trim()) : 0);

			String latStr = request.getParameter("subList[" + i + "].lat");
			String lngStr = request.getParameter("subList[" + i + "].lng");
			String rtgStr = request.getParameter("subList[" + i + "].rating");

			sub.setLat(latStr != null && !latStr.trim().isEmpty() ? Double.parseDouble(latStr.trim()) : 0.0);
			sub.setLng(lngStr != null && !lngStr.trim().isEmpty() ? Double.parseDouble(lngStr.trim()) : 0.0);
			sub.setRating(rtgStr != null && !rtgStr.trim().isEmpty() ? Integer.parseInt(rtgStr.trim()) : 0);

			subList.add(sub);
			i++;
		}
		r.setSubList(subList);

		int result = reviewService.insertReview(r);
		if (result > 0) {
			String uploadPath = "C:/reviews";
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			List<MultipartFile> imageFiles = request.getFiles("imageFiles");
			int fileIdx = 0;

			for (int j = 0; j < r.getSubList().size(); j++) {
				ReviewSub sub = r.getSubList().get(j);
				sub.setTravelNo(r.getTravelNo());
				sub.setTravelSubSeq(j + 1);
				reviewService.insertReviewSub(sub);

				// imageFiles는 단일 키로 모아져 있으므로 순서대로 배분
				if (imageFiles != null && fileIdx < imageFiles.size()) {
					MultipartFile file = imageFiles.get(fileIdx);
					if (file != null && !file.isEmpty()) {
						String original = file.getOriginalFilename();
						String saved = UUID.randomUUID().toString() + "_" + original;
						try {
							file.transferTo(new File(uploadPath + "/" + saved));
							Image img = new Image();
							img.setFileName(original);
							img.setRenameFile(saved);
							img.setImagePath("/uploads");
							img.setTravelSubNo(sub.getTravelSubNo());
							reviewService.insertImage(img);
						} catch (Exception e) {
							e.printStackTrace();
						}
						fileIdx++;
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

	            String contentIdStr = request.getParameter("subList[" + i + "].contentId");

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

	            // =========================
	            // 🔥 핵심: contentId 안전 처리
	            // =========================
	            int contentId = parseIntSafe(contentIdStr);

	            if (contentId <= 0) {
	                // 잘못된 데이터는 아예 저장 안 함
	                continue;
	            }

	            sub.setContentId(contentId);

	            subList.add(sub);
	        }

	        review.setSubList(subList);

	        // =========================
	        // 이미지
	        // =========================
	        List<MultipartFile> imageFiles = request.getFiles("imageFiles");

	        int result = reviewService.updateReview(review, imageFiles);

	        if (result > 0) {
	            return ResponseEntity.ok("success");
	        } else {
	            return ResponseEntity.status(500).body("fail");
	        }

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
