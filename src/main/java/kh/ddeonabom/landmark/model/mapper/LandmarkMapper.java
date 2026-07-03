package kh.ddeonabom.landmark.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import kh.ddeonabom.landmark.model.vo.LandReview;
import kh.ddeonabom.landmark.model.vo.Landmark;
import kh.ddeonabom.review.model.vo.Image;
import kh.ddeonabom.review.model.vo.Review;
import kh.ddeonabom.review.model.vo.ReviewSub;

@Mapper
public interface LandmarkMapper {

	// 관광지 개수 가져오기
	int getListCount(@Param("contentTypeId") Integer contentTypeId, @Param("area") String area, @Param("keyword") String keyword);
	
	// 관광지 리스트 가져오기
	ArrayList<Landmark> selectLandmarkList(RowBounds rowBounds, @Param("contentTypeId") Integer contentTypeId, @Param("area") String area,
											@Param("keyword") String keyword, @Param("sort") String sort);
	
	ArrayList<Landmark> searchLandmarks(@Param("q") String q, @Param("region") String region, @Param("offset") int offset, @Param("size") int size);
	int countLandmarks(@Param("q") String q, @Param("region") String region);

	// 관광지 세부사항 가져오기
	Landmark landmarkDetail(int contentId);

	// 관광지 찜 여부확인
	int landmarkNice(@Param("lNumber") int lNumber,@Param("memberNo") int memberNo);

	// 찜 삭제
	void deleteNice(@Param("lNumber") int lNumber,@Param("memberNo") int memberNo);
	
	// 찜 추가
	void insertNice(@Param("lNumber") int lNumber,@Param("memberNo") int memberNo);

	int getWishListCount(int memberNo);

	ArrayList<Landmark> selectMyWishList(HashMap<String, Object> map);

	Set<Integer> niceList(int memberNo);

	int getWishListCountByParam(Map<String, Object> param);
	
	//관광지 별 후기 개수 가져오기
	int reviewCount(int contentId);
	
	// 관광지 후기 가져오기
	ArrayList<LandReview> review(int contentId, RowBounds rowBounds);
	
	// 별점 평균
	double rating(int contentId);
	
	// 후기 이미지 가져오기
	ArrayList<Image> image(int travelSubNo);
	
	// 조회 수 가져오기
	int landCount(int contentId);



}
