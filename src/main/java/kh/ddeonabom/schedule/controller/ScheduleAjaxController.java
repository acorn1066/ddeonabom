package kh.ddeonabom.schedule.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import kh.ddeonabom.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleAjaxController {
    private final ScheduleService sService;

    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> saveSchedule(@RequestBody ScheduleMain schedule, HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        schedule.setMemberNo(loginUser.getMemberNo());

        try {
            int scheduleNo = sService.saveSchedule(schedule);
            result.put("success", true);
            result.put("scheduleNo", scheduleNo);
        } catch (Exception e) {
        	 e.printStackTrace();
            result.put("success", false);
            result.put("message", "저장 중 오류가 발생했습니다.");
        }
        return result;
    }
    
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteSchedule(@RequestParam("scheduleNo") int scheduleNo,
                                              HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        Member member = (Member) session.getAttribute("loginUser");
        if (member == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        int rows = sService.deleteSchedule(scheduleNo, member.getMemberNo());
        result.put("success", rows > 0);
        return result;
    }
    
}