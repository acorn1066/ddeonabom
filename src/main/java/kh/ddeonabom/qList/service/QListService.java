package kh.ddeonabom.qList.service;

import org.springframework.stereotype.Service;

import kh.ddeonabom.qList.model.mapper.QListMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QListService {
	private final QListMapper qMapper;

	public int getListCount() {
		return qMapper.getListCount();
	}
}
