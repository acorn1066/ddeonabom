package kh.ddeonabom.qList.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.qList.model.mapper.QListMapper;
import kh.ddeonabom.qList.model.vo.QList;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QListService {
	private final QListMapper qMapper;

	public int getListCount() {
		return qMapper.getListCount();
	}

	public ArrayList<QList> selectQList(PageInfo pi, int i) {
		return qMapper.selectQList(pi, i);
	}
}
