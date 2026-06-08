package kh.ddeonabom.schedule.model.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.schedule.model.vo.ScheduleMain;

@Mapper
public interface ScheduleMapper {

	ArrayList<ScheduleMain> selectMainAll(int memberNo);

}
