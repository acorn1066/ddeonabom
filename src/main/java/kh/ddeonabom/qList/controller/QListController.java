package kh.ddeonabom.qList.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.admin.model.service.AdminService;
import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.qList.model.vo.QList;
import kh.ddeonabom.qList.service.QListService;
import kh.ddeonabom.reply.model.vo.Reply;
import kh.ddeonabom.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qList")
public class QListController {
	private final QListService qListService;
	private final ReplyService replyService;
	private final AdminService aService;

	// 목록 보기
	@GetMapping("list")
	public ModelAndView selectQList(
	        @RequestParam(value="page", defaultValue="1") int currentPage,
	        @RequestParam(value="category", defaultValue="전체") String category,
	        @RequestParam(value="searchType", defaultValue="titleContent") String searchType,
	        @RequestParam(value="searchInput", defaultValue="") String searchInput,
	        ModelAndView mv) {

	    HashMap<String, Object> map = new HashMap<>();
	    map.put("category",    category);
	    map.put("searchType",  searchType);
	    map.put("searchInput", searchInput);

	    // 1) 검색 조건 적용된 총 게시글 수
	    int listCount = qListService.getListCount(map);
	    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 8, 8);

	    // 2) 페이징 정보도 map에 추가 → Mapper XML에서 OFFSET에 사용
	    int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit(); // (현재페이지-1) * 목록수
	    map.put("startRow",  startRow);   
	    map.put("listLimit", pi.getBoardLimit());  // 한 페이지 목록 수

	    // 3) map을 같이 넘겨야 필터링 + 페이징이 적용됨
	    ArrayList<QList> qList = qListService.selectQList(map);
	    ArrayList<AdminNotice> noticeList = aService.selectTopNotice();

	    mv.addObject("qList",      qList)
	      .addObject("pi",         pi)
	      // 4) 검색 조건도 뷰로 전달해야 페이징 링크에서 조건 유지 가능
	      .addObject("category",   category)
	      .addObject("searchType", searchType)
	      .addObject("searchInput",searchInput)
	      .addObject("noticeList", noticeList)
	      .setViewName("views/qList/list");

	    return mv;
	}
	
	@GetMapping("write")
	public String writeQList() {
		return "views/qList/write";
	}
	
	@PostMapping("insert")
	public String insertQList(@ModelAttribute QList q, HttpSession session, Model model) {
		int writerNo = ((Member)session.getAttribute("loginUser")).getMemberNo();
		q.setMemberNo(writerNo);
		
		// 제목/내용 NOT NULL 제약 대응: 클라이언트 검증 우회 시 에러 페이지 대신 알림 모달로 안내
		if (q.getTitle() == null || q.getTitle().isBlank()) {
			model.addAttribute("q", q);
			model.addAttribute("errorMessage", "제목을 입력해주세요.");
			return "views/qList/write";
		}
		if (q.getContent() == null || q.getContent().isBlank()) {
			model.addAttribute("q", q);
			model.addAttribute("errorMessage", "내용을 입력해주세요.");
			return "views/qList/write";
		}
		
		int result = qListService.insertQList(q);
		if(result > 0) {
			return "redirect:/qList/list";
		} else {
			model.addAttribute("q", q);
			model.addAttribute("errorMessage", "글 작성을 실패하였습니다.");
			return "views/qList/write";
		}
	}
	
	@GetMapping("detail")
	public ModelAndView detailQList(@RequestParam("qNo") int qNo, HttpSession session, ModelAndView mv) {
	    
		if (session.getAttribute("loginUser") != null) {
			qListService.updateCount(qNo);  // 로그인 회원만 조회수 +1
		}
	    QList q = qListService.detailQList(qNo);  // 단건 조회 서비스 호출
	    
	    // 회원 공개 글인데 비로그인 상태라면 모달 트리거 플래그 전달
	    boolean loginRequired = "MEMBER".equals(q.getVisibility())
	                            && session.getAttribute("loginUser") == null;

	    ArrayList<Reply> replyList = replyService.getReplyList(qNo, "Q");

	    mv.addObject("q", q)
	    	.addObject("replyList",  replyList)
	    	.addObject("replyCount", replyList.size())
	    	.addObject("loginRequired", loginRequired)
	    	.setViewName("views/qList/detail");
	    
	    return mv;
	}

	@PostMapping("delete")
	public String deleteQList(@RequestParam("qNo") int qNo, HttpSession session, RedirectAttributes redirectAttrs) {
	    Member loginUser = (Member) session.getAttribute("loginUser");

	    // 비로그인 상태에서 URL 직접 접근 시 차단 → 에러 페이지 대신 상세로 돌려보내고 모달 안내
	    if (loginUser == null) {
	        redirectAttrs.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
	        return "redirect:/qList/detail?qNo=" + qNo;
	    }

	    // DB에서 사용자 정보 재조회 후 본인 확인 (프론트 th:if만 짰는 것만으론 부족)
	    QList q = qListService.detailQList(qNo);
	    if (q.getMemberNo() != loginUser.getMemberNo()) {
	        redirectAttrs.addFlashAttribute("errorMessage", "삭제 권한이 없습니다.");
	        return "redirect:/qList/detail?qNo=" + qNo;
	    }

	    // soft delete: STATUS = 'N' 처리
	    int result = qListService.deleteQList(qNo);
	    if (result > 0) {
	        return "redirect:/qList/list";
	    } else {
	        redirectAttrs.addFlashAttribute("errorMessage", "글 삭제를 실패하였습니다.");
	        return "redirect:/qList/detail?qNo=" + qNo;
	    }
	}

	@GetMapping("edit")
	public ModelAndView editQList(@RequestParam("qNo") int qNo, HttpSession session, ModelAndView mv, RedirectAttributes redirectAttrs) {
	    Member loginUser = (Member) session.getAttribute("loginUser");

	    if (loginUser == null) {
	        redirectAttrs.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
	        mv.setViewName("redirect:/qList/detail?qNo=" + qNo);
	        return mv;
	    }

	    QList q = qListService.detailQList(qNo);
	    if (q.getMemberNo() != loginUser.getMemberNo()) {
	        redirectAttrs.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
	        mv.setViewName("redirect:/qList/detail?qNo=" + qNo);
	        return mv;
	    }

	    mv.addObject("q", q)
	      .setViewName("views/qList/edit");

	    return mv;
	}

	@PostMapping("update")
	public String updateQList(@ModelAttribute QList q, HttpSession session, Model model, RedirectAttributes redirectAttrs) {
	    Member loginUser = (Member) session.getAttribute("loginUser");

	    if (loginUser == null) {
	        redirectAttrs.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
	        return "redirect:/qList/detail?qNo=" + q.getQNo();
	    }

	    QList existing = qListService.detailQList(q.getQNo());
	    if (existing.getMemberNo() != loginUser.getMemberNo()) {
	        redirectAttrs.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
	        return "redirect:/qList/detail?qNo=" + q.getQNo();
	    }

	    // 제목/내용 NOT NULL 제약 대응: 클라이언트 검증 우회 시 에러 페이지 대신 알림 모달로 안내
	    if (q.getTitle() == null || q.getTitle().isBlank()) {
	        model.addAttribute("q", q);
	        model.addAttribute("errorMessage", "제목을 입력해주세요.");
	        return "views/qList/edit";
	    }
	    if (q.getContent() == null || q.getContent().isBlank()) {
	        model.addAttribute("q", q);
	        model.addAttribute("errorMessage", "내용을 입력해주세요.");
	        return "views/qList/edit";
	    }

	    int result = qListService.updateQList(q);
	    if (result > 0) {
	        return "redirect:/qList/detail?qNo=" + q.getQNo();
	    } else {
	        model.addAttribute("q", q);
	        model.addAttribute("errorMessage", "글 수정을 실패하였습니다.");
	        return "views/qList/edit";
	    }
	}
}