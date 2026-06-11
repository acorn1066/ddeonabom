package kh.ddeonabom.landmark.model.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kh.ddeonabom.landmark.model.vo.Landmark;

@Mapper
public interface LandmarkMapper {


	ArrayList<Landmark> searchLandmarks(@Param("q") String q, @Param("offset") int offset, @Param("size") int size);
	int countLandmarks(@Param("q") String q);
	
}
