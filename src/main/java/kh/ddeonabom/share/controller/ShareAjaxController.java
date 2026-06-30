package kh.ddeonabom.share.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.schedule.service.ScheduleService;
import kh.ddeonabom.share.model.vo.ShareDetail;
import kh.ddeonabom.share.service.ShareService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/share")
public class ShareAjaxController {

    private final ShareService shareService;
    private final ScheduleService scheduleService;

    @PostMapping("/like")
    public Map<String, Object> toggleLike(
            @RequestParam("scheduleNo") int scheduleNo,
            HttpSession session) {

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }

        boolean liked     = shareService.toggleLike(scheduleNo, loginUser.getMemberNo());
        int     likeCount = shareService.getLikeCount(scheduleNo);

        return Map.of("success", true, "liked", liked, "likeCount", likeCount);
    }

    @PostMapping("/wish")
    public Map<String, Object> toggleWish(
            @RequestParam("scheduleNo") int scheduleNo,
            HttpSession session) {

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }

        // 본인 글 찜 방지
        ShareDetail schedule = shareService.selectShareDetail(scheduleNo);
        if (schedule != null && schedule.getMemberNo() == loginUser.getMemberNo()) {
            return Map.of("success", false, "message", "내 글은 찜할 수 없어요.");
        }

        boolean wished = shareService.toggleWish(scheduleNo, loginUser.getMemberNo());

        return Map.of("success", true, "wished", wished);
    }

    @PostMapping("/copy")
    public Map<String, Object> copySchedule(
            @RequestParam("scheduleNo") int scheduleNo,
            HttpSession session) {

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }

        int newNo = scheduleService.copySchedule(scheduleNo, loginUser.getMemberNo());
        if (newNo == 0) {
            return Map.of("success", false, "message", "일정을 찾을 수 없습니다.");
        }
        if (newNo == -1) {
            return Map.of("success", false, "message", "본인 일정은 가져올 수 없습니다.");
        }

        return Map.of("success", true);
    }
}
