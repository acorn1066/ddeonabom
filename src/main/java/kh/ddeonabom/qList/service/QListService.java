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
}
