package kh.ddeonabom.schedule.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import kh.ddeonabom.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	
	@GetMapping("/schedule/list")
	public String scheduleList(Model model,HttpSession session) {
		Member member = (Member)session.getAttribute("loginUser");
		
		int mainNum = 0;
		int showNum = 0;
		int memberNum = 0;
		int hideNum = 0;
		ArrayList<ScheduleMain> sMain = null;
		
		
		if(member!=null) {
			sMain = scheduleService.selectMainAll(member.getMemberNo());
	        mainNum = sMain.size();
	        if (mainNum > 0) {
	            for(ScheduleMain main : sMain) {
	                if(main.getScheduleVisibility().equals("N")) hideNum++;
	                else if(main.getScheduleVisibility().equals("M")) memberNum++;
	                else showNum++;
	            }
	        }
		}
		
		model.addAttribute("scheduleList", sMain);
		model.addAttribute("mainNum", mainNum);
		model.addAttribute("memberNum", memberNum);
		model.addAttribute("showNum", showNum);
		model.addAttribute("hideNum", hideNum);
		model.addAttribute("isLogin", member!=null?"Y":"N");
		
		
		return "views/schedule/my-list";
	}
	
	@GetMapping("/schedule/new")
	public String newSchedule(HttpSession session) {
		Member member = (Member) session.getAttribute("loginUser");
			if (member == null) {
			    return "redirect:/member/login";
		}
		return "views/schedule/write";
	}
	
	@GetMapping("/schedule/detail/{scheduleNo}")
	public String scheduleDetail(@PathVariable("scheduleNo") int scheduleNo,
	                             HttpSession session, Model model) {
	    Member member = (Member) session.getAttribute("loginUser");
	    if (member == null) {
	        return "redirect:/member/login";
	    }

	    String scheduleJson = scheduleService.getScheduleDetailJson(scheduleNo, member.getMemberNo());
	    if (scheduleJson == null) {
	        return "redirect:/schedule/list";   // 없거나 권한 없으면 목록으로
	    }

	    model.addAttribute("scheduleJson", scheduleJson);
	    return "views/schedule/write";
	}
}
