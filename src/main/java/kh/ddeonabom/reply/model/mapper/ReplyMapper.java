package kh.ddeonabom.reply.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	List<Reply> sReplyList(Map<String, Object> map);

	int rInsertReply(Reply reply);

	int rUpdateReply(Reply reply);

	int rDeleteReply(Reply reply);


}
