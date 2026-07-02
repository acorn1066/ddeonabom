package kh.ddeonabom.reply.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.reply.model.vo.Reply;
import kh.ddeonabom.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyAjaxController {
	private final ReplyService replyService;

	// ─── Ajax 댓글 CRUD (전체 게시판 공통: qList / share / review) ───────────

	// Ajax 댓글 등록
	@PostMapping("ajax/insert")
	public Map<String, Object> ajaxInsertReply(@RequestBody Reply reply, HttpSession session) {
		Map<String, Object> res = new HashMap<>();
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			res.put("success", false); res.put("message", "로그인이 필요합니다."); return res;
		}
		if (reply.getContent() == null || reply.getContent().isBlank()) {
			res.put("success", false); res.put("message", "댓글 내용을 입력해주세요."); return res;
		}

		reply.setMemberNo(loginUser.getMemberNo());
		int result = replyService.insertReply(reply);

		if (result > 0) {
			res.put("success",    true);
			res.put("replyNo",    reply.getReplyNo());
			res.put("writer",     loginUser.getNickname());
			res.put("content",    reply.getContent());
			res.put("memberNo",   loginUser.getMemberNo());
			res.put("createDate", new java.text.SimpleDateFormat("yyyy.MM.dd").format(new java.util.Date()));
		} else {
			res.put("success", false); res.put("message", "댓글 등록에 실패했습니다.");
		}
		return res;
	}

	// Ajax 댓글 수정
	@PostMapping("ajax/update")
	public Map<String, Object> ajaxUpdateReply(@RequestBody Reply reply, HttpSession session) {
		Map<String, Object> res = new HashMap<>();
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			res.put("success", false); res.put("message", "로그인이 필요합니다."); return res;
		}
		if (reply.getContent() == null || reply.getContent().isBlank()) {
			res.put("success", false); res.put("message", "댓글 내용을 입력해주세요."); return res;
		}

		reply.setMemberNo(loginUser.getMemberNo());
		int result = replyService.updateReply(reply);
		res.put("success", result > 0);
		if (result <= 0) res.put("message", "댓글 수정에 실패했습니다.");
		return res;
	}

	// Ajax 댓글 삭제
	@PostMapping("ajax/delete")
	public Map<String, Object> ajaxDeleteReply(@RequestBody Map<String, Object> body, HttpSession session) {
		Map<String, Object> res = new HashMap<>();
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			res.put("success", false); res.put("message", "로그인이 필요합니다."); return res;
		}

		int replyNo = (int) body.get("replyNo");
		int result = replyService.deleteReply(replyNo, loginUser.getMemberNo());
		res.put("success", result > 0);
		if (result <= 0) res.put("message", "댓글 삭제에 실패했습니다.");
		return res;
	}

}
