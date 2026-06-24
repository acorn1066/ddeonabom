package kh.ddeonabom.review.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    public ArrayList<Review> selectReviewList(PageInfo pi, String keyword, String region, Integer loginUserNo, String sort) {
        return reviewMapper.selectReviewList(pi, keyword, region, loginUserNo, sort);
    }

	public int selectListCount(String keyword, String region, Integer loginUserNo) {
		return reviewMapper.selectListCount(keyword, region, loginUserNo);
	}

	public int insertReview(Review r) {
		return reviewMapper.insertReview(r);
	}


	public void insertReviewSub(ReviewSub sub) {
		reviewMapper.insertReviewSub(sub);
	}

	public void insertImage(Image img) {
		reviewMapper.insertImage(img);
	}

	public Review getReviewDetail(int travelNo, Integer loginUserNo) {
		Review review = reviewMapper.getReviewDetail(travelNo, loginUserNo);   
		if(review == null) {
			return null;
		}
	    List<ReviewSub> subList = reviewMapper.getReviewSubList(travelNo); 
	    for (ReviewSub sub : subList) {
	        List<Image> images = reviewMapper.getImageListBySubNo(sub.getTravelSubNo());
	        if (images == null) {
	            images = new ArrayList<>();
	        }
	        sub.setImages(images.stream()
	                            .map(img -> img.getImagePath() + "/" + img.getRenameFile())
	                            .collect(Collectors.toList()));
	    }
	    review.setSubList(subList); 
	    return review; 
	}

	public void increaseCount(int travelNo) {
		reviewMapper.increaseCount(travelNo);
	}

	public int toggleLike(int travelNo, int memberNo) {

	    int cnt = reviewMapper.existsLike(travelNo, memberNo);

	    if (cnt > 0) {
	        reviewMapper.deleteLike(travelNo, memberNo);
	    } else {
	        reviewMapper.insertLike(travelNo, memberNo);
	    }

	    return reviewMapper.selectLikeCount(travelNo);
	}
	public ArrayList<Review> selectMyReviewList(HashMap<String, Object> reviewMap) {
		return reviewMapper.selectMyReviewList(reviewMap);
	}

	public int getMyReviewCount(int memberNo) {
		
		return reviewMapper.getMyReviewCount(memberNo);
	}

	public Review getTravelWithSubList(Long travelNo) {
		return reviewMapper.getTravelWithSubList(travelNo);
	}

	public Review sReview(int travelNo) {
		return reviewMapper.sReview(travelNo);
	}

	
	



}
