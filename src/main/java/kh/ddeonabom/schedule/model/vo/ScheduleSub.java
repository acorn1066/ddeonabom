package kh.ddeonabom.schedule.model.vo;

import java.sql.Date;

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

public class ScheduleSub {
	private int scheduleSubNo;
	private String scheduleSubDate;
	private int scheduleSubSeq;
	private int scheduleNo;
	private int contentId;
	
	private String title;
    private String addr1;
    private String firstimage;
    private String mapx;
    private String mapy;
}
