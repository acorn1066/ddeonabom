package kh.ddeonabom.qList.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.qList.model.vo.QList;

@Mapper
public interface QListMapper {

	int getListCount(HashMap<String, Object> map);

	ArrayList<QList> selectQList(HashMap<String, Object> map);

	QList detailQList(int qNo);

	int getMyListCount(HashMap<String, Object> map);

	ArrayList<QList> selectMyBoardList(HashMap<String, Object> map);

	int insertQList(QList q);

	int updateCount(int qNo);

}
