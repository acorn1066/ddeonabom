package kh.ddeonabom.share.model.vo;

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
public class SharePlace {

    private Date   subDate;   // SCHEDULE_SUB.SCHEDULE_SUB_DATE (DAY 그룹핑 기준)
    private int    subSeq;    // SCHEDULE_SUB.SCHEDULE_SUB_SEQ  (순서)
    private String title;     // LANDMARK.TITLE
    private String addr1;     // LANDMARK.ADDR1
    private String mapX;      // LANDMARK.MAPX (경도)
    private String mapY;      // LANDMARK.MAPY (위도)
}
