package kh.ddeonabom.share.model.vo;

import java.sql.Date;
import java.util.List;

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
public class ShareDay {

    private Date             subDate;    // 해당 DAY의 날짜
    private List<SharePlace> placeList;  // 그 날의 장소 목록
}
