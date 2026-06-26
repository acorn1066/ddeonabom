package kh.ddeonabom.reply.controller;

import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.reply.model.vo.Reply;
import kh.ddeonabom.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
	private final ReplyService replyService;
	

	// 게시판 코드 → 상세 URL 매핑 (게시판 추가 시 여기에만 추가)
	private static final Map<String, Function<Integer, String>> BOARD_URL = Map.of(
		"Q", no -> "/qList/detail?qNo="    + no,
		"S", no -> "/share/detail/"        + no,
		"T", no -> "/reviews/detail?travelNo=" + no
	);

	private String redirectUrl(String postBoard, int postNo) {
		if (postBoard == null) return "redirect:/qList/detail?qNo=" + postNo;
		var fn = BOARD_URL.getOrDefault(postBoard, no -> "/qList/detail?qNo=" + no);
		return "redirect:" + fn.apply(postNo);
	}

	// 댓글 등록
	@PostMapping("insert")
	public String insertReply(@ModelAttribute Reply reply, HttpSession session, RedirectAttributes redirectAttrs) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			redirectAttrs.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
			return redirectUrl(reply.getPostBoard(), reply.getPostNo());
		}

		if (reply.getContent() == null || reply.getContent().isBlank()) {
			redirectAttrs.addFlashAttribute("errorMessage", "댓글 내용을 입력해주세요.");
			return redirectUrl(reply.getPostBoard(), reply.getPostNo());
		}

		reply.setMemberNo(loginUser.getMemberNo());

		int result = replyService.insertReply(reply);
		
		if (result <= 0) {
			redirectAttrs.addFlashAttribute("errorMessage", "댓글 등록을 실패하였습니다.");
		}
		return redirectUrl(reply.getPostBoard(), reply.getPostNo());
	}

	// 댓글 수정
	@PostMapping("update")
	public String updateReply(@ModelAttribute Reply reply, HttpSession session, RedirectAttributes redirectAttrs) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			redirectAttrs.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
			return redirectUrl(reply.getPostBoard(), reply.getPostNo());
		}

		if (reply.getContent() == null || reply.getContent().isBlank()) {
			redirectAttrs.addFlashAttribute("errorMessage", "댓글 내용을 입력해주세요.");
			return redirectUrl(reply.getPostBoard(), reply.getPostNo());
		}

		reply.setMemberNo(loginUser.getMemberNo());

		int result = replyService.updateReply(reply);
		if (result <= 0) {
			redirectAttrs.addFlashAttribute("errorMessage", "댓글 수정을 실패하였습니다.");
		}
		return redirectUrl(reply.getPostBoard(), reply.getPostNo());
	}

	// 댓글 삭제
	@PostMapping("delete")
	public String deleteReply(@RequestParam("replyNo")   int replyNo,
	                          @RequestParam("postNo")    int postNo,
	                          @RequestParam("postBoard") String postBoard,
	                          HttpSession session, RedirectAttributes redirectAttrs) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			redirectAttrs.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
			return redirectUrl(postBoard, postNo);
		}

		int result = replyService.deleteReply(replyNo, loginUser.getMemberNo());
		if (result <= 0) {
			redirectAttrs.addFlashAttribute("errorMessage", "댓글 삭제를 실패하였습니다.");
		}
		return redirectUrl(postBoard, postNo);
	}

	// ─── Ajax 전용 (qList / share) ────────────────────────────────────────

	// Ajax 댓글 등록
	@ResponseBody
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
	@ResponseBody
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
	@ResponseBody
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
