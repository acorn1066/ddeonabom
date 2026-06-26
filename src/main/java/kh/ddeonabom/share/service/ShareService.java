package kh.ddeonabom.share.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import kh.ddeonabom.share.model.mapper.ShareMapper;
import kh.ddeonabom.share.model.vo.Share;
import kh.ddeonabom.share.model.vo.ShareDay;
import kh.ddeonabom.share.model.vo.ShareDetail;
import kh.ddeonabom.share.model.vo.SharePlace;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareMapper shareMapper;

    /** 페이징·필터 적용된 공개 일정 총 개수 */
    public int getShareListCount(HashMap<String, Object> map) {
        return shareMapper.getShareListCount(map);
    }

    /** 페이징·필터 적용된 공개 일정 목록 */
    public ArrayList<Share> selectShareList(HashMap<String, Object> map) {
        return shareMapper.selectShareList(map);
    }

    /** 상세 정보 단건 조회 */
    public ShareDetail selectShareDetail(int scheduleNo) {
        return shareMapper.selectShareDetail(scheduleNo);
    }

    /** 장소 flat 조회 → 날짜별 DAY 그룹핑 */
    public ArrayList<ShareDay> selectShareDayList(int scheduleNo) {
        ArrayList<SharePlace> places = shareMapper.selectSharePlaceList(scheduleNo);
        ArrayList<ShareDay> dayList = new ArrayList<>();
        ShareDay currentDay = null;

        for (SharePlace p : places) {
            if (currentDay == null || !currentDay.getSubDate().equals(p.getSubDate())) {
                currentDay = new ShareDay();
                currentDay.setSubDate(p.getSubDate());
                currentDay.setPlaceList(new ArrayList<>());
                dayList.add(currentDay);
            }
            currentDay.getPlaceList().add(p);
        }
        return dayList;
    }

    /** 추천 여부 */
    public boolean isLiked(int scheduleNo, int memberNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("scheduleNo", scheduleNo);
        map.put("memberNo",   memberNo);
        return shareMapper.isLiked(map) > 0;
    }

    /** 찜 여부 */
    public boolean isWished(int scheduleNo, int memberNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("scheduleNo", scheduleNo);
        map.put("memberNo",   memberNo);
        return shareMapper.isWished(map) > 0;
    }

    /** 추천 toggle: true = 추천됨, false = 취소됨 */
    public boolean toggleLike(int scheduleNo, int memberNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("scheduleNo", scheduleNo);
        map.put("memberNo",   memberNo);
        if (shareMapper.isLiked(map) > 0) {
            shareMapper.deleteLike(map);
            return false;
        } else {
            shareMapper.insertLike(map);
            return true;
        }
    }

    /** 전체 추천 수 */
    public int getLikeCount(int scheduleNo) {
        return shareMapper.getLikeCount(scheduleNo);
    }

    /** 찜 toggle: true = 찜됨, false = 취소됨 */
    public boolean toggleWish(int scheduleNo, int memberNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("scheduleNo", scheduleNo);
        map.put("memberNo",   memberNo);
        if (shareMapper.isWished(map) > 0) {
            shareMapper.deleteWish(map);
            return false;
        } else {
            shareMapper.insertWish(map);
            return true;
        }
    }

	public int getWishPlanCount(int memberNo) {
		return shareMapper.getWishPlanCount(memberNo);
	}

	public ArrayList<Share> selectMyWishPlanList(HashMap<String, Object> map) {
		return shareMapper.selectMyWishPlanList(map);
	}

	public int selectMyShareCount(int memberNo) {
		return shareMapper.selectMyShareCount(memberNo);
	}

	public ArrayList<Share> selectMyShareList(HashMap<String, Object> map) {
		return shareMapper.selectMyShareList(map);
	}
}
