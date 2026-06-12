package kh.ddeonabom.landmark.model.mapper;

import java.util.ArrayList;
import java.util.List;

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
}
