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
public class LandReview {
	private Integer travelNo;
	private String nickName;
	private Date createDate;
	private Integer rating;
	private String travelTitle;
	private String travelSubContent;
	private String visibility;
	private Integer travelSubNo;
	
	public String getNickName() {
        if (this.nickName != null && this.nickName.contains("_")) {
            return "(탈퇴회원)";
        }
        return this.nickName;
    }
}
