package kh.ddeonabom.qList.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.qList.model.mapper.QListMapper;
import kh.ddeonabom.qList.model.vo.QList;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QListService {
	private final QListMapper qMapper;

	public int getListCount(HashMap<String, Object> map) {
        return qMapper.getListCount(map);
    }

    public ArrayList<QList> selectQList(HashMap<String, Object> map) {
        return qMapper.selectQList(map);
    }


	public QList detailQList(int qNo) {
		return qMapper.detailQList(qNo);
	}

	public int getMyListCount(HashMap<String, Object> map) {
		return qMapper.getMyListCount(map);
	}

	public ArrayList<QList> selectMyBoardList(HashMap<String, Object> map) {
		return qMapper.selectMyBoardList(map);
	}

	public int insertQList(QList q) {
		return qMapper.insertQList(q);
	}

	public int updateCount(int qNo) {
		return qMapper.updateCount(qNo);
	}

	// Mapper로 soft delete 위임
	public int deleteQList(int qNo) {
		return qMapper.deleteQList(qNo);
	}

	// Mapper로 글 수정 위임
	public int updateQList(QList q) {
		return qMapper.updateQList(q);
	}

	// 추천 toggle: true = 추천됨, false = 취소됨
	public boolean toggleLike(int qNo, int memberNo) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("qNo",      qNo);
		map.put("memberNo", memberNo);

		if (qMapper.isLiked(map) > 0) {
			qMapper.deleteLike(map);
			return false;
		} else {
			qMapper.insertLike(map);
			return true;
		}
	}

	public int getLikeCount(int qNo) {
		return qMapper.getLikeCount(qNo);
	}

	public boolean isLiked(int qNo, int memberNo) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("qNo",      qNo);
		map.put("memberNo", memberNo);
		return qMapper.isLiked(map) > 0;
	}
}
