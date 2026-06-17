package kh.ddeonabom.landmark.model.service;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.landmark.model.mapper.LandmarkMapper;
import kh.ddeonabom.landmark.model.vo.Landmark;
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
	public int getListCount() {
		return mapper.getListCount();
	}
	
	// 관광지 리스트 가져오기
	public ArrayList<Landmark> selectLandmarkList(PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectLandmarkList(rowBounds);
	}
	
	//관광지 세부 가져오기
	public Landmark landmarkDetail(int contentId) {
		return mapper.landmarkDetail(contentId);
	}

}
