package kh.ddeonabom.review.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Review {
	private int travelNo;        
    private String travelTitle;  
    private String status;       
    private Date createDate;    
    private Date modifyDate;     
    private int count;           
    private int memberNo;        
    private Integer scheduleNo;
    private int likeCount;

}
