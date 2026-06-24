package kh.ddeonabom.schedule.model.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kh.ddeonabom.landmark.model.vo.Landmark;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import kh.ddeonabom.schedule.model.vo.ScheduleSub;

@Mapper
public interface ScheduleMapper {

	ArrayList<ScheduleMain> selectMainAll(int memberNo);

	void insertScheduleMain(ScheduleMain schedule);

	void insertScheduleSub(ScheduleSub place);

	int updateScheduleStatus(Map<String, Object> param);

	List<ScheduleSub> selectScheduleSubList(int scheduleNo);

	ScheduleMain selectScheduleDetail(int scheduleNo);
	
	int updateScheduleMain(ScheduleMain schedule);
	
	int deleteScheduleSubAll(int scheduleNo);
	
	int updateScheduleVisibility(Map<String, Object> param);

	void updateVisibility(int scheduleNo, int memberNo, String visibility);

}
