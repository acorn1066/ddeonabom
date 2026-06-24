package kh.ddeonabom.reply.controller;

import java.util.Map;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.reply.model.vo.Reply;
import kh.ddeonabom.reply.service.ReplyService;
import kh.ddeonabom.review.model.service.ReviewService;
import kh.ddeonabom.review.model.vo.Review;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
	private final ReplyService replyService;
	

	// 게시판 코드 → 상세 URL 매핑 (게시판 추가 시 여기에만 추가)
	private static final Map<String, Function<Integer, String>> BOARD_URL = Map.of(
		"Q", no -> "/qList/detail?qNo=" + no,
		"S", no -> "/share/detail/"     + no
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
		
}
