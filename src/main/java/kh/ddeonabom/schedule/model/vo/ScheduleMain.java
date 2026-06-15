package kh.ddeonabom.schedule.model.vo;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ScheduleMain {
	private int scheduleNo;
	private String scheduleTitle;
	private String scheduleStatus;
	private Date createDate;
	private Date modifyDate;
	private String scheduleStartdate;
	private String scheduleEnddate;
	private String scheduleVisibility;
	private int memberNo;
	
	private List<ScheduleSub> places;
	private Integer placeCount;
	private String coords;
	
	private String firstAddr;
	private int likeCount;

}
