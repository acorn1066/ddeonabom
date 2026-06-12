package kh.ddeonabom.review.model.service;

import java.util.ArrayList;

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

    public ArrayList<Review> selectReviewList() {
        return reviewMapper.selectReviewList();
    }

	public int selectListCount() {
		return reviewMapper.selectListCount();
	}

	

	public void insertReviewSub(ReviewSub sub) {
		
	}

	public void insertImage(Image img) {
		
	}

	public int insertReview(Review r) {
		return 0;
	}

	public ArrayList<Review> selectReviewList(PageInfo pi) {
		return reviewMapper.selectReviewList(pi);
	}

	public ArrayList<Review> selectMyReviewList(int memberNo) {
		return reviewMapper.selectMyReviewList(memberNo);
	}



}
