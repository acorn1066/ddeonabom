package kh.ddeonabom.landmark.model.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

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

}
