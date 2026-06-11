package kh.ddeonabom.review.model.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.review.model.vo.Image;
import kh.ddeonabom.review.model.vo.Review;
import kh.ddeonabom.review.model.vo.ReviewSub;

@Mapper
public interface ReviewMapper {

	ArrayList<Review> selectReviewList();

	int selectListCount();

	ArrayList<Review> selectReviewList(PageInfo pi);

	int insertReview(Review r);

	int insertReviewSub(ReviewSub sub);

	int insertImage(Image img);

}
