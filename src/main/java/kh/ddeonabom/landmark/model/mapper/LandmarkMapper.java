package kh.ddeonabom.landmark.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import kh.ddeonabom.landmark.model.vo.Landmark;

@Mapper
public interface LandmarkMapper {

	// 관광지 개수 가져오기
	int getListCount();
	
	// 관광지 리스트 가져오기
	ArrayList<Landmark> selectLandmarkList(RowBounds rowBounds);
	
	ArrayList<Landmark> searchLandmarks(@Param("q") String q, @Param("region") String region, @Param("offset") int offset, @Param("size") int size);
	int countLandmarks(@Param("q") String q, @Param("region") String region);

	// 관광지 세부사항 가져오기
	Landmark landmarkDetail(int contentId);

	// 관광지 찜 여부확인
	int landmarkNice(@Param("lNumber") int lNumber,@Param("memberNo") int memberNo);

	void deleteNice(@Param("lNumber") int lNumber,@Param("memberNo") int memberNo);

	void insertNice(@Param("lNumber") int lNumber,@Param("memberNo") int memberNo);

	int getWishListCount(int memberNo);

	ArrayList<Landmark> selectMyWishList(HashMap<String, Object> map);

	Set<Integer> niceList(int memberNo);
}
