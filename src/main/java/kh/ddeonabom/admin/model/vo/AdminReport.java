package kh.ddeonabom.admin.model.vo;

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

public class AdminReport {
	
	 private int reportNo;
	    private String targetType;
	    private int targetNo;
	    private String reason;
	    private String reportStatus;
	    private Date reportDate;
	    private int memberNo;
	    
	    // 조회용 추가 필드 (JOIN으로 가져올 값들)
	    private String targetTitle;    // 게시글 제목 or 댓글 내용
	    private String reporterName;   // 신고자 닉네임

}
