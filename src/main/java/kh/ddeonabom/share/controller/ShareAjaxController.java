package kh.ddeonabom.share.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.share.service.ShareService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/share")
public class ShareAjaxController {

    private final ShareService shareService;

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

        boolean wished = shareService.toggleWish(scheduleNo, loginUser.getMemberNo());

        return Map.of("success", true, "wished", wished);
    }
}
