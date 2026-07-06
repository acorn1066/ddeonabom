package kh.ddeonabom.landmark.model.vo;

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
	private Integer contentId;
	private String addr1;
	private String addr2;
	private Double mapx;
	private Double mapy;
	private String tel;
	private String title;
	private Integer landCount;
	private Integer contentTypeId;
	private String overview;
	private String firstimage;
	private String firstimage2;
	
}
