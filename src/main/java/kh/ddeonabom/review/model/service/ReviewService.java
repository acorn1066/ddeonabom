package kh.ddeonabom.review.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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


	public Review getTravelWithSubList(Long travelNo) {
		return reviewMapper.getTravelWithSubList(travelNo);
	}

	public Review sReview(int travelNo) {
		return reviewMapper.sReview(travelNo);
	}

	public Review reviewUpdate(int travelNo) {
		return reviewMapper.ReviewDetail(travelNo);
	}

	@Transactional(rollbackFor = Exception.class)
	public int reviewUpdateAction(Review review, List<MultipartFile> imageFiles) {
		
		// 1. 메인 테이블 수정
		int result = reviewMapper.reviewUpdateAction(review);
		
		if (result > 0) {
			List<ReviewSub> subList = review.getSubList();
			
			if (subList != null && !subList.isEmpty()) {
				int fileIdx = 0; 
				
				for (int i = 0; i < subList.size(); i++) {
					ReviewSub sub = subList.get(i);
					
					sub.setTravelNo(review.getTravelNo()); 
					sub.setTravelSubSeq(i + 1);            
					
					// 2. 관광지 기본 정보 수정 (위度, 경도 등)
					reviewMapper.reviewSubUpdate(sub); 
					if (imageFiles != null && imageFiles.size() > fileIdx) {
						MultipartFile file = imageFiles.get(fileIdx);
						
						// 비어있지 않은 진짜 파일인지 체크
						if (file != null && !file.isEmpty()) {
							String savePath = "C:/reviews/"; // 실제 물리 경로
							
							try {
							    String originalName = file.getOriginalFilename();
							    String ext = originalName.substring(originalName.lastIndexOf("."));
							    String rename = java.util.UUID.randomUUID().toString() + ext;
							    
							    // 하드디스크에 실물 파일 물리 저장
							    file.transferTo(new java.io.File(savePath + rename));
							    Image img = new Image();
							    
							    img.setTravelSubNo(sub.getTravelSubNo());
							    img.setImagePath("/uploads");              
							    img.setFileName(originalName);  
							    img.setRenameFile(rename);               
							    
							    // 이미지 레벨 세터는 VO에 없으므로 과감히 제외합니다.
							    
							    this.insertImage(img);						    
							} catch (Exception e) {							  
							    e.printStackTrace();
							}
						}
						fileIdx++; 
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
