package kh.ddeonabom.schedule.model.mappers;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kh.ddeonabom.landmark.model.vo.Landmark;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;

@Mapper
public interface ScheduleMapper {

	ArrayList<ScheduleMain> selectMainAll(int memberNo);

}
