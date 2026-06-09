package kh.ddeonabom.review.model.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.review.model.vo.Review;

@Mapper
public interface ReviewMapper {

	ArrayList<Review> selectReviewList();

	int selectListCount();

	ArrayList<Review> selectReviewList(PageInfo pi);

}
