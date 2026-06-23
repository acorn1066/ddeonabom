package kh.ddeonabom.main.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.landmark.model.vo.Landmark;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;

@Mapper
public interface MainMapper {

	Map<String, Object> selectMainStats();

	List<Landmark> selectPopularLandmarks();

	ScheduleMain selectFeaturedSchedule();

}
