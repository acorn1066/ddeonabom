package kh.ddeonabom.reply.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import kh.ddeonabom.reply.model.mapper.ReplyMapper;
import kh.ddeonabom.reply.model.vo.Reply;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
	private final ReplyMapper replyMapper;

	public ArrayList<Reply> getReplyList(int postNo, String postBoard) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("postNo",    postNo);
		map.put("postBoard", postBoard);
		return replyMapper.getReplyList(map);
	}

	public int insertReply(Reply reply) {
		return replyMapper.insertReply(reply);
	}

	public int updateReply(Reply reply) {
		return replyMapper.updateReply(reply);
	}

	public int deleteReply(int replyNo, int memberNo) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("replyNo",  replyNo);
		map.put("memberNo", memberNo);
		return replyMapper.deleteReply(map);
	}

	public int getMyCommentCount(HashMap<String, Object> map) {
		return replyMapper.getMyCommentCount(map);
	}

	public ArrayList<Reply> selectMyCommentList(HashMap<String, Object> map) {
		return replyMapper.selectMyCommentList(map);
	}

}
