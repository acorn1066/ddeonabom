package kh.ddeonabom.review.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.review.model.service.ReviewService;
import kh.ddeonabom.review.model.vo.Review;
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
    public Map<String, Object> selectReviewList(@RequestParam(value="currentPage", defaultValue="1") int currentPage) {
        
        int listCount = reviewService.selectListCount(); 
        int pageLimit = 5;   
        int boardLimit = 9;  

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, pageLimit, boardLimit);
        ArrayList<Review> list = reviewService.selectReviewList(pi);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("list", list);
        responseData.put("pi", pi);
        
        return responseData; 
    }
	
	@GetMapping("/reviews/write")
	public String reviewWrite(Model model) {
		 model.addAttribute("kakaoApiKey", "77218df82558088a0b690733061ba6f2");
		 
	    return "views/review/write"; 
	}

	@PostMapping("/reviews/insert")
	public String insertReviews(@ModelAttribute Review r,@RequestParam("upfile") MultipartFile upfile) {
		
		return "redirect:review/list";
	}
	
	
}
