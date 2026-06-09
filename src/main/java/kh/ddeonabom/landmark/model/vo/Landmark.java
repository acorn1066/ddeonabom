package kh.ddeonabom.landmark.model.vo;

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
public class Landmark {
	private int contentId;
	private String addr1;
	private String addr2;
	private String firstimage;
	private String firstimage2;
	private double mapx;
	private double mapy;
	private String tel;
	private String title;
	private int landCount;
	private int contentTypeId;
	private String overview;
}
