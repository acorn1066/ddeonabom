package kh.ddeonabom.review.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.review.model.service.ReviewService;
import kh.ddeonabom.review.model.vo.Image;
import kh.ddeonabom.review.model.vo.Review;
import kh.ddeonabom.review.model.vo.ReviewSub;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor


public class ReviewController {
	private final ReviewService reviewService;
	
	@GetMapping("/reviews/list")
    
    public String reviewListPage() {
       
        return "views/review/reviews";
    }
	
	@GetMapping("/selectReviewList")
    @ResponseBody 
    public Map<String, Object> selectReviewList(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
    											@RequestParam(value="region", defaultValue="") String region,
            									@RequestParam(value="keyword", defaultValue="") String keyword,
            									HttpSession session) {
        
		Member loginUser = (Member) session.getAttribute("loginUser");
	    Integer loginUserNo = (loginUser != null) ? loginUser.getMemberNo() : null;
	    
        int listCount = reviewService.selectListCount(keyword, region, loginUserNo); 
        int pageLimit = 5;   
        int boardLimit = 9;  

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, pageLimit, boardLimit);
        ArrayList<Review> list = reviewService.selectReviewList(pi, keyword, region, loginUserNo);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("list", list);
        responseData.put("pi", pi);
        
        return responseData; 
    }
	
	@GetMapping("/reviews/write")
	public String reviewWrite(Model model, HttpSession session) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {
	        return "redirect:/member/login?targetUrl=/reviews/write";
	    }
		model.addAttribute("kakaoApiKey", "77218df82558088a0b690733061ba6f2");
		 
	    return "views/review/write"; 
	}

	@PostMapping("/reviews/insert")
	public String insertReviews(@ModelAttribute Review r, HttpServletRequest request) {

	    int result = reviewService.insertReview(r);

	    if (result > 0) {
	        String uploadPath = request.getServletContext().getRealPath("/uploads/reviews");
	        
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
	                                img.setImagePath("/uploads/reviews");
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
	
	
	
	
	
}
