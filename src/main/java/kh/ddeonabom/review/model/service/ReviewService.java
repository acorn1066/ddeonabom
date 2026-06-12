package kh.ddeonabom.review.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.review.model.mappers.ReviewMapper;
import kh.ddeonabom.review.model.vo.Image;
import kh.ddeonabom.review.model.vo.Review;
import kh.ddeonabom.review.model.vo.ReviewSub;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	private final ReviewMapper reviewMapper;

    public ArrayList<Review> selectReviewList(PageInfo pi, String keyword, String region, Integer loginUserNo) {
        return reviewMapper.selectReviewList(pi, keyword, region, loginUserNo);
    }

	public int selectListCount(String keyword, String region, Integer loginUserNo) {
		return reviewMapper.selectListCount(keyword, region, loginUserNo);
	}

	

	public void insertReviewSub(ReviewSub sub) {
		
	}

	public void insertImage(Image img) {
		
	}

	public int insertReview(Review r) {
		return 0;
	}


	

	



}
