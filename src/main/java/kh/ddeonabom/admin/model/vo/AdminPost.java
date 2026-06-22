package kh.ddeonabom.admin.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class AdminPost {
	private String title;
	private String nickname;
	private Date createDate;
	private String status;
	private String content;
	
    private int postNo;     
    private String boardType;
    
    private String contentTitle;     // CONTENT_TITLE (부제목)
    private String subContent; 

}
