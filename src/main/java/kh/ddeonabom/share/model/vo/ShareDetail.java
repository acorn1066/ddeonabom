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
public class ShareDetail {

    // ── SCHEDULE_MAIN ──
    private int    scheduleNo;
    private String scheduleTitle;
    private String scheduleStatus;      // 삭제 여부(Y/N) - URL 직접 접속 우회 차단용
    private Date   scheduleStartdate;   // java.sql.Date → #dates.format() 사용 가능
    private Date   scheduleEnddate;
    private Date   createDate;
    private int    memberNo;

    // ── MEMBER (JOIN) ──
    private String memberNickname;

    // ── LANDMARK (서브쿼리) ──
    private String firstAddr;           // 첫 장소 주소 (지역 뱃지용)
    private String coords;              // 전체 장소 좌표 "mapx,mapy;..." (SVG 루트용)

    // ── 집계 (서브쿼리) ──
    private int    likeCount;           // NICE WHERE POST_BOARD_FX='SR'
    private int    placeCount;          // SCHEDULE_SUB COUNT
    
    public String getMemberNickname() {
        if (this.memberNickname != null && this.memberNickname.contains("_")) {
            return "(탈퇴회원)";
        }
        return this.memberNickname;
    }
}
