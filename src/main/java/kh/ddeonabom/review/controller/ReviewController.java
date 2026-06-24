package kh.ddeonabom.review.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
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
	
	private final ScheduleService sService;

	@Value("${kakao.api.key}")
	private String kakaoApiKey;
	
	
	@GetMapping("/reviews/list")
    
    public String reviewListPage() {
       
        return "views/review/reviews";
    }
	
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
	public String insertReviews(@ModelAttribute Review r, HttpServletRequest request, HttpSession session) {

		Member loginUser = (Member) session.getAttribute("loginUser");
	    if (loginUser != null) {
	        r.setMemberNo(loginUser.getMemberNo());
	    }
	    int result = reviewService.insertReview(r);
	    if (result > 0) {
	    	String uploadPath = "C:/reviews"; 
	        
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdirs();
	        }
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
	    model.addAttribute("review", review);
	    Map<String, Object> map = new HashMap<>();
	    map.put("postNo", travelNo);
	    map.put("postBoard", "T");

	    List<Reply> replyList = replyService.sReplyList(map);
	    model.addAttribute("replyList", replyList);
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
	
	
}
