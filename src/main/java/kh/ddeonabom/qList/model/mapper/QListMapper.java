package kh.ddeonabom.qList.model.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.qList.model.vo.QList;

@Mapper
public interface QListMapper {

	int getListCount();

	ArrayList<QList> selectQList(PageInfo pi);

	QList detailQList(int qNo);

}
