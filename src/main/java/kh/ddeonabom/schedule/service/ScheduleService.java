package kh.ddeonabom.schedule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.ddeonabom.schedule.model.mappers.ScheduleMapper;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import kh.ddeonabom.schedule.model.vo.ScheduleSub;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	private final ScheduleMapper mapper;
	
	public ArrayList<ScheduleMain> selectMainAll(int memberNo) {
		return mapper.selectMainAll(memberNo);
	}


	@Transactional
	public int saveSchedule(ScheduleMain schedule) {
		mapper.insertScheduleMain(schedule);
	    int scheduleNo = schedule.getScheduleNo();

	    List<ScheduleSub> places = schedule.getPlaces();
	    if (places != null) {
	        for (ScheduleSub place : places) {
	            place.setScheduleNo(scheduleNo);
	            mapper.insertScheduleSub(place);
	        }
	    }
	    return scheduleNo;
	}
	
	public int deleteSchedule(int scheduleNo, int memberNo) {
	    Map<String, Object> param = new HashMap<>();
	    param.put("scheduleNo", scheduleNo);
	    param.put("memberNo", memberNo);
	    return mapper.updateScheduleStatus(param);
	}
}