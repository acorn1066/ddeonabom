package kh.ddeonabom.landmark.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.landmark.model.mapper.LandmarkMapper;
import kh.ddeonabom.landmark.model.vo.LandReview;
import kh.ddeonabom.landmark.model.vo.Landmark;
import kh.ddeonabom.review.model.vo.Image;
import kh.ddeonabom.review.model.vo.Review;
import kh.ddeonabom.review.model.vo.ReviewSub;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandmarkService {
	private final LandmarkMapper mapper;
	
	public ArrayList<Landmark> searchLandmarks(String q, String region, int page, int size) {
		int offset = page * size;
	    return mapper.searchLandmarks(q, region, offset, size);
	}

	public int countLandmarks(String q, String region) {
	    return mapper.countLandmarks(q, region);
	}
	
	// 관광지 개수 가져오기
	public int getListCount(Integer contentTypeId, String area, String keyword) {
		return mapper.getListCount(contentTypeId, area, keyword);
	}
	
	// 관광지 리스트 가져오기
	public ArrayList<Landmark> selectLandmarkList(PageInfo pi, Integer contentTypeId, String area, String keyword, String sort) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectLandmarkList(rowBounds, contentTypeId, area, keyword, sort);
	}
	
	//관광지 세부 가져오기
	public Landmark landmarkDetail(int contentId) {
		Landmark l = mapper.landmarkDetail(contentId);
		int result = mapper.landCount(contentId);
		if(result > 0) {
			l.setLandCount(l.getLandCount() + 1);
		}
		return l;
	}
	
	// 관광지 찜 여부 확인
	public int landmarkNice(int lNumber,int memberNo) {
		return mapper.landmarkNice(lNumber, memberNo);
	}
	
	// 찜 삭제
	public void deleteNice(int lNumber, int memberNo) {
		mapper.deleteNice(lNumber, memberNo);
	}
	
	// 찜 추가
	public void insertNice(int lNumber, int memberNo) {
		mapper.insertNice(lNumber, memberNo);
	}

	
	

	public ArrayList<Landmark> selectMyWishList(HashMap<String, Object> map) {
		return mapper.selectMyWishList(map);
	}

	public Set<Integer> niceList(int memberNo) {
		return mapper.niceList(memberNo);
	}

	public int getWishListCountByParam(Map<String, Object> param) {
	    return mapper.getWishListCountByParam(param);
	}
	
	//관광지 별 후기 개수 가져오기
	public int reviewCount(int contentId) {
		return mapper.reviewCount(contentId);
	}
	
	//관광지 후기 가져오기
	public ArrayList<LandReview> review(int contentId, PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.review(contentId, rowBounds);
	}
	
	// 별점 평균
	public double rating(int contentId) {
		return mapper.rating(contentId);
	}
	
	// 이미지 경로 가져오기
	public ArrayList<Image> image(int travelSubNo) {
		return mapper.image(travelSubNo);
	}

	public int getWishListCount(HashMap<String, Object> map) {
		
		return mapper.getWishListCount(map);
	}

}
