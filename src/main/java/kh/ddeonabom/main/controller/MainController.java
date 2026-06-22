package kh.ddeonabom.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kh.ddeonabom.main.model.service.MainService;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MainService mainService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("stats", mainService.getMainStats());
        model.addAttribute("popularLandmarks", mainService.getPopularLandmarks());
        ScheduleMain featured = mainService.selectFeaturedSchedule();
        model.addAttribute("featuredSchedule", featured);

        if (featured != null) {
            model.addAttribute("featuredRoute", mainService.getFeaturedRoute(featured.getCoords()));
        }
        return "/index";
    }
}