package kh.ddeonabom.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.main.model.service.MainService;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MainService mainService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("stats", mainService.getMainStats());
        model.addAttribute("popularLandmarks", mainService.getPopularLandmarks());

        Member loginUser = (Member) session.getAttribute("loginUser");
        int memberNo = (loginUser != null) ? loginUser.getMemberNo() : 0;
        model.addAttribute("recentSharedList", mainService.getTopSharedSchedules(memberNo));
        ScheduleMain featured = mainService.selectFeaturedSchedule();
        model.addAttribute("featuredSchedule", featured);

        if (featured != null) {
            model.addAttribute("featuredRoute", mainService.getFeaturedRoute(featured.getCoords()));
        }
        return "/index";
    }
}