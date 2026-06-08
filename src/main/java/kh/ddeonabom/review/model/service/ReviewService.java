package kh.ddeonabom.review.model.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import kh.ddeonabom.review.model.mappers.ReviewMapper;
import kh.ddeonabom.review.model.vo.Review;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	private final ReviewMapper reviewMapper;

    public ArrayList<Review> selectReviewList() {
        return reviewMapper.selectReviewList();
    }



}
