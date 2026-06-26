package kh.ddeonabom.qList.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.qList.service.QListService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qList")
public class QListAjaxController {

    private final QListService qListService;

    @PostMapping("/like")
    public Map<String, Object> toggleLike(
            @RequestParam("qNo") int qNo,
            HttpSession session) {

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }

        boolean liked = qListService.toggleLike(qNo, loginUser.getMemberNo());
        int     count = qListService.getLikeCount(qNo);

        return Map.of("success", true, "liked", liked, "count", count);
    }
}
