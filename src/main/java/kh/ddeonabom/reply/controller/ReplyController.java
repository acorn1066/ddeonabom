package kh.ddeonabom.reply.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("detail")
	public String detail(int travelNo, Model model) {

	    ArrayList<Reply> replyList = replyService.selectReplyList("T", travelNo);
	    model.addAttribute("replyList", replyList);
	    return "reviews/detail";
	}
	
	@PostMapping("insert")
	public String insertReply(Reply r, HttpSession session) {

	    Member loginUser = (Member)session.getAttribute("loginUser");
	    r.setMemberNo(loginUser.getMemberNo());
	    r.setPostBoard("T");
	    int result = replyService.insertReply(r);

	    System.out.println("postNo = " + r.getPostNo());
	    return "redirect:/reviews/detail?travelNo=" + r.getPostNo();
	}
}
