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
	private Integer scheduleSubNo;
	private String scheduleSubDate;
	private Integer scheduleSubSeq;
	private Integer scheduleNo;
	private Integer contentId;
	
	private String title;
    private String addr1;
    private String firstimage;
    private String mapx;
    private String mapy;
    
    private String contentTitle;
    private Double lat;
    private Double lng;
}
