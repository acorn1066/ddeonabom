package kh.ddeonabom.reply.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kh.ddeonabom.reply.model.vo.Reply;

@Mapper
public interface ReplyMapper {

	ArrayList<Reply> getReplyList(HashMap<String, Object> map);

	ArrayList<Reply> selectReplyList( @Param("postBoard") String postBoard, @Param("postNo") int postNo);

	int insertReply(Reply reply);

	int updateReply(Reply reply);

	int deleteReply(HashMap<String, Object> map);

	int getMyCommentCount(HashMap<String, Object> map);

	ArrayList<Reply> selectMyCommentList(HashMap<String, Object> map);


}
