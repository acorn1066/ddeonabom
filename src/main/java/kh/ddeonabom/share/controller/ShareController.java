package kh.ddeonabom.share.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.admin.model.service.AdminService;
import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.reply.model.vo.Reply;
import kh.ddeonabom.reply.service.ReplyService;
import kh.ddeonabom.share.model.vo.Share;
import kh.ddeonabom.share.model.vo.ShareDay;
import kh.ddeonabom.share.model.vo.ShareDetail;
import kh.ddeonabom.share.service.ShareService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("share")
public class ShareController {

    private final ShareService shareService;
    private final ReplyService replyService;
    private final AdminService aService;

    @GetMapping("list")
    public ModelAndView shareList(
            @RequestParam(value = "page",        defaultValue = "1")   int currentPage,
            @RequestParam(value = "region",      defaultValue = "")    String region,
            @RequestParam(value = "searchInput", defaultValue = "")    String searchInput,
            HttpSession session,
            ModelAndView mv) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("region",      region);
        map.put("searchInput", searchInput);

        // 로그인 여부에 따라 visibility 조건 분기용
        Member loginUser = (Member) session.getAttribute("loginUser");
        map.put("memberNo", loginUser != null ? loginUser.getMemberNo() : 0);

        // 1) 총 게시글 수 → 페이징 계산 (페이지 번호 최대 5개, 한 페이지 9개)
        int listCount = shareService.getShareListCount(map);
        PageInfo pi   = Pagination.getPageInfo(currentPage, listCount, 5, 9);

        // 2) 페이징 오프셋 추가
        map.put("startRow",  (pi.getCurrentPage() - 1) * pi.getBoardLimit());
        map.put("listLimit", pi.getBoardLimit());

        // 3) 목록 조회
        ArrayList<Share> shareList = shareService.selectShareList(map);
        ArrayList<AdminNotice> noticeList = aService.selectTopNotice();

        mv.addObject("shareList",   shareList)
          .addObject("pi",          pi)
          .addObject("region",      region)
          .addObject("searchInput", searchInput)
          .addObject("noticeList",  noticeList)
          .setViewName("views/share/list");

        return mv;
    }
    
 // ==================================================================== 공지사항 상세 ==============================================================
    
    @GetMapping("notice")
    public ModelAndView noticeDetail(@RequestParam("noticeNo") int noticeNo, ModelAndView mv) {

        AdminNotice notice = aService.selectNotice(noticeNo);
        mv.addObject("notice", notice)
          .addObject("from", "share")
          .setViewName("views/admin/memberNotice");
        return mv;
    }
    
 // ==================================================================== 공지사항 상세 ==============================================================
    

    @GetMapping("detail/{no}")
    public ModelAndView shareDetail(
            @PathVariable("no") int scheduleNo,
            HttpSession session,
            ModelAndView mv) {

        ShareDetail schedule = shareService.selectShareDetail(scheduleNo);

        // 글이 존재하지 않으면 흰 배경 + 안내 모달 → 확인 시 목록 이동
        if (schedule == null) {
            mv.addObject("message",     "존재하지 않는 게시글입니다.")
              .addObject("redirectUrl", "/share/list")
              .setViewName("views/common/blocked");
            return mv;
        }

        // 로그인 사용자 기준 추천·찜 여부 (관리자 우회 판단에도 재사용)
        Member loginUser = (Member) session.getAttribute("loginUser");

        // 삭제(schedule_status='N')된 글은 관리자만 조회 가능(신고 처리용), 일반 사용자는 URL 직접 접근 차단
        boolean isAdmin = loginUser != null && "Y".equals(loginUser.getIsAdmin());
        if ("N".equals(schedule.getScheduleStatus()) && !isAdmin) {
            mv.addObject("message",     "삭제된 게시글입니다.")
              .addObject("redirectUrl", "/share/list")
              .setViewName("views/common/blocked");
            return mv;
        }

        ArrayList<ShareDay> dayList = shareService.selectShareDayList(scheduleNo);
        ArrayList<Reply> replyList  = replyService.getReplyList(scheduleNo, "S");

        // 박수 계산 (종료일 - 시작일)
        long totalNights = 0;
        if (schedule.getScheduleStartdate() != null && schedule.getScheduleEnddate() != null) {
            totalNights = (schedule.getScheduleEnddate().getTime()
                         - schedule.getScheduleStartdate().getTime()) / (1000L * 60 * 60 * 24);
        }

        boolean isLiked  = false;
        boolean isWished = false;
        if (loginUser != null) {
            isLiked  = shareService.isLiked(scheduleNo, loginUser.getMemberNo());
            isWished = shareService.isWished(scheduleNo, loginUser.getMemberNo());
        }

        mv.addObject("schedule",    schedule)
          .addObject("dayList",     dayList)
          .addObject("replyList",   replyList)
          .addObject("replyCount",  replyList.size())
          .addObject("totalNights", totalNights)
          .addObject("isLiked",     isLiked)
          .addObject("isWished",    isWished)
          .setViewName("views/share/detail");

        return mv;
    }
}
