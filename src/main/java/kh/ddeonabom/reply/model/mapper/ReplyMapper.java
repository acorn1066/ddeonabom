package kh.ddeonabom.reply.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.reply.model.vo.Reply;

@Mapper
public interface ReplyMapper {

	ArrayList<Reply> getReplyList(HashMap<String, Object> map);

}
