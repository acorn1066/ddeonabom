package kh.ddeonabom.review.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.ddeonabom.review.model.service.ReviewService;
import kh.ddeonabom.review.model.vo.Review;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor

public class ReviewController {
	private final ReviewService reviewService;
	
	@GetMapping("/reviews")
    
    public String reviewListPage() {
       
        return "views/review/reviews";
    }
	
	@GetMapping("/selectReviewList")
    @ResponseBody 
    public ArrayList<Review> selectReviewList() {
        return reviewService.selectReviewList(); 
    }

}
