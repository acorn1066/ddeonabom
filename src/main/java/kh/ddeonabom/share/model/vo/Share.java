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
public class Share {

    // ── SCHEDULE_MAIN ──
    private int    scheduleNo;
    private String scheduleTitle;
    private String scheduleStartdate;   // TO_CHAR 포맷 문자열 (yyyy.mm.dd)
    private String scheduleEnddate;
    private String scheduleVisibility;  // Y=공개, M=회원공개, N=비공개
    private Date   createDate;

    // ── MEMBER (JOIN) ──
    private int    memberNo;
    private String memberNickname;      // MEMBER.NICKNAME

    // ── 집계 (서브쿼리) ──
    private int    placeCount;          // SCHEDULE_SUB COUNT
    private int    likeCount;           // NICE WHERE POST_BOARD_FX='SR'
    private int    replyCount;          // REPLY WHERE POST_BOARD='S'

    // ── LANDMARK (첫 번째 장소 기반) ──
    private String firstImage;          // LANDMARK.FIRSTIMAGE2 (카드 썸네일)
    private int    firstContentTypeId;  // LANDMARK.CONTENT_TYPE_ID (플레이스홀더용)
    private String firstTitle;          // LANDMARK.TITLE (플레이스홀더 장소명용)
    private String firstAddr;           // LANDMARK.ADDR1 (지역 뱃지용)
    private String coords;              // "mapx,mapy;mapx,mapy;..." (SVG 루트용)
    private String reportStatus;

    public String getMemberNickname() {
        if (this.memberNickname != null && this.memberNickname.contains("_")) {
            return "(탈퇴회원)";
        }
        return this.memberNickname;
    }

}
