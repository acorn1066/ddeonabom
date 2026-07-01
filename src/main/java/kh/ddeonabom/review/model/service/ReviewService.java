package kh.ddeonabom.review.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

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
	private final AmazonS3 amazonS3; 
	
	@Value("${cloud.aws.s3.bucket}") 
	private String bucket;

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
	                            // 뒤에 파일명을 붙이지 않고 S3 URL 주소 그대로 가져옵니다.
	                            .map(img -> img.getImagePath()) 
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


	public Review getTravelWithSubList(Long travelNo) {
		return reviewMapper.getTravelWithSubList(travelNo);
	}

	public Review sReview(int travelNo) {
		return reviewMapper.sReview(travelNo);
	}

	public Review reviewUpdate(int travelNo) {

	    Review review = reviewMapper.ReviewDetail(travelNo);

	    if(review == null){
	        return null;
	    }

	    List<ReviewSub> subList = reviewMapper.getReviewSubList(travelNo);

	    for(ReviewSub sub : subList){

	        List<Image> imgs =
	                reviewMapper.getImageListBySubNo(sub.getTravelSubNo());

	        sub.setImages(
	                imgs.stream()
	                        .map(Image::getImagePath)
	                        .collect(Collectors.toList())
	        );
	    }

	    review.setSubList(subList);

	    return review;
	}
	@Transactional
	public int updateReview(Review review) {

	    reviewMapper.reviewUpdateAction(review);

	    int result = 1;

	    for (ReviewSub sub : review.getSubList()) {
	    	
	    	List<String> deleteImages = sub.getDeleteImages();

	    	if (deleteImages != null && !deleteImages.isEmpty()) {
	    	    for (String path : deleteImages) {
	    	        if (path != null && !path.trim().isEmpty()) {
	    	            reviewMapper.deleteImageByPath(path.trim());
	    	        }
	    	    }
	    	}

	        if (sub.getTravelSubNo() == 0) {
	            reviewMapper.insertReviewSub(sub);
	        } else {
	            reviewMapper.reviewSubUpdate(sub);
	        }

	        // sub별 파일만 처리
	        List<MultipartFile> files = sub.getImageFiles();

	        if (files != null && !files.isEmpty()) {

	            for (MultipartFile file : files) {

	                if (!file.isEmpty()) {

	                    try {
	                        String original = file.getOriginalFilename();
	                        String saved = UUID.randomUUID() + "_" + original;

	                        ObjectMetadata metadata = new ObjectMetadata();
	                        metadata.setContentLength(file.getSize());
	                        metadata.setContentType(file.getContentType());

	                        amazonS3.putObject(
	                            new PutObjectRequest(bucket, saved, file.getInputStream(), metadata)
	                        );

	                        String s3Url = amazonS3.getUrl(bucket, saved).toString();

	                        Image img = new Image();
	                        img.setFileName(original);
	                        img.setRenameFile(saved);
	                        img.setImagePath(s3Url);
	                        img.setTravelSubNo(sub.getTravelSubNo());

	                        reviewMapper.insertImage(img);

	                    } catch (Exception e) {
	                        e.printStackTrace();
	                        result = 0;
	                    }
	                }
	            }
	        }
	    }

	    return result;
	}

	public void deleteReview(int travelNo) {
		reviewMapper.deleteReview(travelNo);
	}

	public int getMyReviewCount(HashMap<String, Object> map) {
		return reviewMapper.getMyReviewCount(map);
	}

	

}
