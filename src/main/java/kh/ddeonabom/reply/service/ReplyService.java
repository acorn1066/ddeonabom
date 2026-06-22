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

	public int insertReply(Reply r) {
		return replyMapper.insertReply(r);
	}

	public ArrayList<Reply> selectReplyList(String postBoard, int postNo) {
	return replyMapper.selectReplyList(postBoard, postNo);
	}

}
