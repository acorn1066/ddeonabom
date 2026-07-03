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
	    
	    // 조회용 추가 필드 신고자 이름 제목 등
	    private String targetTitle;
	    private String reporterName; 
	    private String targetWriter;

}
