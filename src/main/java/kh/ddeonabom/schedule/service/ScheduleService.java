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


	public void setVisibility(int scheduleNo, int memberNo, String visibility) {
		Map<String, Object> param = new HashMap<>();
	    param.put("scheduleNo", scheduleNo);
	    param.put("memberNo", memberNo);
	    param.put("visibility", visibility);
	    mapper.updateScheduleVisibility(param);
	}

	
	public List<ScheduleSub> selectScheduleSubList(int scheduleNo){
		return mapper.selectScheduleSubList(scheduleNo);
	}


	public ScheduleMain selectScheduleDetail(Integer scheduleNo) {
		return mapper.selectScheduleDetail(scheduleNo);
	}

	// 공유 게시판 일정 복사: 원본 scheduleNo → 내 일정으로 새로 저장
	@Transactional
	public int copySchedule(int scheduleNo, int memberNo) {
		// 원본 조회 (member 확인 없이 — 공개 일정이므로)
		ScheduleMain original = mapper.selectScheduleDetail(scheduleNo);
		if (original == null) return 0;

		// 본인 일정은 복사 불가
		if (original.getMemberNo() == memberNo) return -1;

		List<ScheduleSub> subList = mapper.selectScheduleSubList(scheduleNo);

		// 복사본 새 SCHEDULE_MAIN 생성 (기본 비공개)
		ScheduleMain copy = new ScheduleMain();
		copy.setScheduleTitle(original.getScheduleTitle());
		copy.setScheduleStartdate(original.getScheduleStartdate());
		copy.setScheduleEnddate(original.getScheduleEnddate());
		copy.setScheduleVisibility("N");
		copy.setMemberNo(memberNo);

		mapper.insertScheduleMain(copy); // selectKey → copy.scheduleNo 자동 세팅
		int newNo = copy.getScheduleNo();

		// 장소 목록 복사
		for (ScheduleSub sub : subList) {
			ScheduleSub newSub = new ScheduleSub();
			newSub.setScheduleNo(newNo);
			newSub.setScheduleSubDate(sub.getScheduleSubDate());
			newSub.setScheduleSubSeq(sub.getScheduleSubSeq());
			newSub.setContentId(sub.getContentId());
			mapper.insertScheduleSub(newSub);
		}

		return newNo; // 새 일정 번호 반환
	}
}