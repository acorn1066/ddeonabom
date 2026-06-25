package kh.ddeonabom.share.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.share.model.vo.Share;
import kh.ddeonabom.share.model.vo.ShareDetail;
import kh.ddeonabom.share.model.vo.SharePlace;

@Mapper
public interface ShareMapper {

    /** 페이징·필터 적용된 공개 일정 총 개수 */
    int getShareListCount(HashMap<String, Object> map);

    /** 페이징·필터 적용된 공개 일정 목록 */
    ArrayList<Share> selectShareList(HashMap<String, Object> map);

    /** 상세 정보 단건 조회 */
    ShareDetail selectShareDetail(int scheduleNo);

    /** 장소 flat 목록 (DAY 그룹핑용) */
    ArrayList<SharePlace> selectSharePlaceList(int scheduleNo);

    /** 추천 여부 (0 or 1) */
    int isLiked(HashMap<String, Object> map);

    /** 찜 여부 (0 or 1) */
    int isWished(HashMap<String, Object> map);

    /** 추천 등록 */
    int insertLike(HashMap<String, Object> map);

    /** 추천 취소 */
    int deleteLike(HashMap<String, Object> map);

    /** 전체 추천 수 */
    int getLikeCount(int scheduleNo);

    /** 찜 등록 */
    int insertWish(HashMap<String, Object> map);

    /** 찜 취소 */
    int deleteWish(HashMap<String, Object> map);

	int getWishPlanCount(int memberNo);

	ArrayList<Share> selectMyWishPlanList(HashMap<String, Object> map);

	int selectMyShareCount(int memberNo);

	ArrayList<Share> selectMyShareList(HashMap<String, Object> map);
}
