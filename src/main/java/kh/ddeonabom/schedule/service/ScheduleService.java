package kh.ddeonabom.schedule.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import kh.ddeonabom.schedule.model.mappers.ScheduleMapper;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	private final ScheduleMapper mapper;
	
	
	public ArrayList<ScheduleMain> selectMainAll(int memberNo) {
		
		return mapper.selectMainAll(memberNo);
	}
	
}