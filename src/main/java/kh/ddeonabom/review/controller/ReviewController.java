package kh.ddeonabom.review.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        
        // [STEP 1] DB에 있는 전체 후기 게시글 개수 가져오기
        int listCount = reviewService.selectListCount(); 
        
        // [STEP 2] 한 페이지에 보여줄 번호 수(5개)와 게시글 카드 수(6개) 지정
        int pageLimit = 5;   
        int boardLimit = 9;  
        
        // [STEP 3] 공용 Pagination 유틸로 페이징 계산 후 PageInfo(pi) 객체 생성
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, pageLimit, boardLimit);
        
        // [STEP 4] 계산된 pi를 서비스로 넘겨서 딱 해당 페이지 분량(6개)만 조회
        ArrayList<Review> list = reviewService.selectReviewList(pi);
        
        // [STEP 5] 자바스크립트(Ajax)가 받아서 쓸 수 있게 Map에 묶어서 반환
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("list", list);
        responseData.put("pi", pi);
        
        return responseData; 
    }

}
