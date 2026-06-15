package kh.ddeonabom.schedule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.ddeonabom.schedule.model.mappers.ScheduleMapper;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import kh.ddeonabom.schedule.model.vo.ScheduleSub;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

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
	
	
	public String getScheduleDetailJson(int scheduleNo, int memberNo) {
	    ScheduleMain main = mapper.selectScheduleDetail(scheduleNo);
	    if (main == null || main.getMemberNo() != memberNo) {
	        return null;
	    }

	    List<ScheduleSub> subList = mapper.selectScheduleSubList(scheduleNo);

	    Map<String, List<Map<String, Object>>> placesByDate = new LinkedHashMap<>();
	    for (ScheduleSub sub : subList) {
	        String dateKey = sub.getScheduleSubDate();
	        placesByDate.computeIfAbsent(dateKey, k -> new ArrayList<>());

	        Map<String, Object> place = new LinkedHashMap<>();
	        place.put("contentId", sub.getContentId());
	        place.put("title", sub.getTitle());
	        place.put("addr1", sub.getAddr1());
	        place.put("firstimage", sub.getFirstimage());
	        place.put("mapx", sub.getMapx());
	        place.put("mapy", sub.getMapy());
	        placesByDate.get(dateKey).add(place);
	    }

	    Map<String, Object> data = new LinkedHashMap<>();
	    data.put("scheduleNo", main.getScheduleNo());
	    data.put("scheduleTitle", main.getScheduleTitle());
	    data.put("scheduleStartdate", main.getScheduleStartdate());
	    data.put("scheduleEnddate", main.getScheduleEnddate());
	    data.put("scheduleVisibility", main.getScheduleVisibility());
	    data.put("placesByDate", placesByDate);

	    try {
	        return new ObjectMapper().writeValueAsString(data);
	    } catch (Exception e) {
	        return null;
	    }
	}
	
	
	@Transactional
	public int updateSchedule(ScheduleMain schedule) {
	    int updated = mapper.updateScheduleMain(schedule);
	    if (updated == 0) {
	        throw new IllegalStateException("수정 권한이 없는 일정입니다.");
	    }

	    int scheduleNo = schedule.getScheduleNo();
	    mapper.deleteScheduleSubAll(scheduleNo);

	    List<ScheduleSub> places = schedule.getPlaces();
	    if (places != null) {
	        for (ScheduleSub place : places) {
	            place.setScheduleNo(scheduleNo);
	            mapper.insertScheduleSub(place);
	        }
	    }
	    return scheduleNo;
	}
	
	@Transactional
	public String toggleVisibility(int scheduleNo, int memberNo) {
	    ScheduleMain main = mapper.selectScheduleDetail(scheduleNo);
	    if (main == null || main.getMemberNo() != memberNo) {
	        throw new IllegalStateException("권한이 없는 일정입니다.");
	    }

	    String next = "Y".equals(main.getScheduleVisibility()) ? "N" : "Y";

	    Map<String, Object> param = new HashMap<>();
	    param.put("scheduleNo", scheduleNo);
	    param.put("memberNo", memberNo);
	    param.put("visibility", next);
	    mapper.updateScheduleVisibility(param);

	    return next;
	}
}