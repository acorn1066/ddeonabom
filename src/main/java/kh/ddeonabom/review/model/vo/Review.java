package kh.ddeonabom.review.model.vo;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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

public class Review {
	private int travelNo;        
	//게시글 제목
    private String travelTitle;  
    private String status;       
    private Date createDate;    
    private Date modifyDate; 
    private Date travelStartDate;
    private Date travelEndDate;
    private int count;           
    private int memberNo;        
    private Integer scheduleNo;
    private int likeCount;
    private String region;
    // 하위 데이터 묶음(하나에 게시글에 여러개 장소)
    private List<ReviewSub> subList; 
    private String keyword;
    private String visibility;
    private String nickname;
    private String reportStatus;
    
    public String getNickname() {
        if (this.nickname != null && this.nickname.contains("_")) {
            return "(탈퇴회원)";
        }
        return this.nickname;
    }

}
