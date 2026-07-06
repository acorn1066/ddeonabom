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
	
	 private Integer reportNo;
	    private String targetType;
	    private Integer targetNo;
	    private String reason;
	    private String reportStatus;
	    private Date reportDate;
	    private Integer memberNo;
	    
	    // 조회용 추가 필드 신고자 이름 제목 등
	    private String targetTitle;
	    private String reporterName; 
	    private String targetWriter;
	    
	    private Integer postNo;
	    private String postBoard;

}
