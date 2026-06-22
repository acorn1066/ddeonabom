package kh.ddeonabom.reply.controller;

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
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
	private final ReplyService replyService;

	// 댓글 등록
	@PostMapping("insert")
	public String insertReply(@ModelAttribute Reply reply, HttpSession session, RedirectAttributes redirectAttrs) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			redirectAttrs.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
			return "redirect:/qList/detail?qNo=" + reply.getPostNo();
		}

		if (reply.getContent() == null || reply.getContent().isBlank()) {
			redirectAttrs.addFlashAttribute("errorMessage", "댓글 내용을 입력해주세요.");
			return "redirect:/qList/detail?qNo=" + reply.getPostNo();
		}

		reply.setMemberNo(loginUser.getMemberNo());

		int result = replyService.insertReply(reply);
		if (result <= 0) {
			redirectAttrs.addFlashAttribute("errorMessage", "댓글 등록을 싴패하였습니다.");
		}
		return "redirect:/qList/detail?qNo=" + reply.getPostNo();
	}

	// 댓글 수정
	@PostMapping("update")
	public String updateReply(@ModelAttribute Reply reply, HttpSession session, RedirectAttributes redirectAttrs) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			redirectAttrs.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
			return "redirect:/qList/detail?qNo=" + reply.getPostNo();
		}

		if (reply.getContent() == null || reply.getContent().isBlank()) {
			redirectAttrs.addFlashAttribute("errorMessage", "댓글 내용을 입력해주세요.");
			return "redirect:/qList/detail?qNo=" + reply.getPostNo();
		}

		reply.setMemberNo(loginUser.getMemberNo());

		int result = replyService.updateReply(reply);
		if (result <= 0) {
			redirectAttrs.addFlashAttribute("errorMessage", "댓글 수정을 싴패하였습니다.");
		}
		return "redirect:/qList/detail?qNo=" + reply.getPostNo();
	}

	// 댓글 삭제
	@PostMapping("delete")
	public String deleteReply(@RequestParam("replyNo") int replyNo,
	                          @RequestParam("postNo")  int postNo,
	                          HttpSession session, RedirectAttributes redirectAttrs) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			redirectAttrs.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
			return "redirect:/qList/detail?qNo=" + postNo;
		}

		int result = replyService.deleteReply(replyNo, loginUser.getMemberNo());
		if (result <= 0) {
			redirectAttrs.addFlashAttribute("errorMessage", "댓글 삭제를 싴패하였습니다.");
		}
		return "redirect:/qList/detail?qNo=" + postNo;
	}
}