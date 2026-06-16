package kh.ddeonabom.review.model.mappers;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.review.model.vo.Image;
import kh.ddeonabom.review.model.vo.Review;
import kh.ddeonabom.review.model.vo.ReviewSub;

@Mapper
public interface ReviewMapper {

	ArrayList<Review> selectReviewList(@Param("pi") PageInfo pi, @Param("keyword") String keyword, @Param("region") String region,  @Param("loginUserNo")Integer loginUserNo);

	int selectListCount(@Param("keyword") String keyword, @Param("region") String region,  @Param("loginUserNo")Integer loginUserNo);

	int insertReview(Review r);

	int insertReviewSub(ReviewSub sub);

	int insertImage(Image img);

	ArrayList<Review> selectMyReviewList(int memberNo);

	Review getReviewDetail(int travelNo);

	List<ReviewSub> getReviewSubList(int travelNo);

	List<Image> getImageListBySubNo(int travelSubNo);

}
