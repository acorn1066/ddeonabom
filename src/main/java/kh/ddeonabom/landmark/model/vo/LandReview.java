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
	private int travelNo;
	private String nickName;
	private Date createDate;
	private int rating;
	private String travelTitle;
	private String travelSubContent;
	private int imageNo;
	private String imagePath;
	private String fileName;
	private String renameFile;
	private int travelSubNo;
	
	public String getNickName() {
        if (this.nickName != null && this.nickName.contains("_")) {
            return "(탈퇴회원)";
        }
        return this.nickName;
    }
}
