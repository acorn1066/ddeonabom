package kh.ddeonabom.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.admin.model.service.AdminService;
import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.admin.model.vo.AdminPost;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.member.model.vo.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")

public class AdminController {
	
	 private final AdminService aService;
	
	@GetMapping("dash")
		public String adminDash() {
		return "redirect:http://localhost:5173";
	}
	
	@GetMapping("/dashboard")
	@ResponseBody
	public Map<String, Object> dashboard() {

	    Map<String, Object> map = new HashMap<>();

	    map.put("memberCount", aService.selectMemberCount());
	    map.put("boardCount",
	            aService.selectQlistCount()
	          + aService.selectTravelCount()
	          + aService.selectScheduleCount());

	    map.put("replyCount", aService.selectReplyCount());
	    map.put("reportCount", aService.selectReportCount());

	    return map;
	}
	
	@ResponseBody
	@GetMapping("users")
	public Member getAdmin(HttpSession session) {
		return (Member)session.getAttribute("loginUser");
		
	}
	
//	============================================================ 회원 =================================================================
	
	@ResponseBody
	@GetMapping("/members")
	public HashMap<String, Object> members(
	        @RequestParam(value = "page", defaultValue = "1") int page, HttpSession session) {

	    Member loginUser = (Member)session.getAttribute("loginUser");
	    int listCount = aService.selectMemberCountList(loginUser.getId());
	    PageInfo pi = Pagination.getPageInfo(page, listCount, 10, 10);

	    ArrayList<Member> list = aService.selectMembers(loginUser.getId(), pi);

	    HashMap<String, Object> data = new HashMap<>();

	    data.put("list", list);
	    data.put("pi", pi);

	    return data;
	}
	
	@ResponseBody
	@PatchMapping("members")
	public int updateMemberStatus(@RequestBody HashMap<String, String> map) {
	    map.put("col", "status");
	    return aService.updateMemberStatus(map);
	}
	
//	============================================================ 공지사항 =================================================================
	
	@ResponseBody
	@PostMapping("write")
	public int insertNotice(@RequestBody AdminNotice notice, HttpSession session) {

	    Member loginUser = (Member) session.getAttribute("loginUser");

	    if (loginUser == null || !"Y".equals(loginUser.getIsAdmin())) {
	        return 0;
	    }

	    notice.setMemberNo(loginUser.getMemberNo());

	    return aService.insertNotice(notice);
	}
	
	@ResponseBody
	@GetMapping("notice/{noticeNo}")
	public AdminNotice selectNotice(@PathVariable("noticeNo") int noticeNo) {
	    return aService.selectNotice(noticeNo);

	}

	@ResponseBody
	@GetMapping("notice")
	public HashMap<String, Object> noticeList(@RequestParam(value = "page", defaultValue = "1") int page) {

	    int listCount = aService.getNoticeCount();
	    
//	    System.out.println("listCount = " + listCount);

	    PageInfo pi = Pagination.getPageInfo(page, listCount, 10, 10);
	    ArrayList<AdminNotice> list = aService.selectNoticeList(pi);
	    HashMap<String, Object> data = new HashMap<>();

	    data.put("list", list);
	    data.put("pi", pi);
	    return data;
	}
	
	@ResponseBody
	@PatchMapping("notice")
	public int updateNoticeStatus(@RequestBody AdminNotice notice) {
		return aService.updateNoticeStatus(notice);
	}
	
	@ResponseBody
	@PutMapping("notice/{noticeNo}")
	public int updateNotice(@PathVariable("noticeNo") int noticeNo, @RequestBody AdminNotice notice) {
	    notice.setNoticeNo(noticeNo);
	    return aService.updateNotice(notice);
	}
	
	@ResponseBody
	@GetMapping("notice/top")
	public ArrayList<AdminNotice> selectTopNotice() {

	    return aService.selectTopNotice();

	}
	
	
	
	
//	============================================================ 게시글 =================================================================
	
	@ResponseBody
	@GetMapping("/posts")
	public HashMap<String, Object> postList(@RequestParam("category") String category, @RequestParam(value = "page", defaultValue = "1") int page) {

	    int listCount = aService.getPostCount(category);
	    
//	    System.out.println("category = " + category);
//	    System.out.println("listCount = " + listCount);
	    PageInfo pi = Pagination.getPageInfo(page, listCount, 10, 10);
	    ArrayList<AdminPost> list = aService.selectPostList(category, pi);
	    HashMap<String, Object> data = new HashMap<>();

	    data.put("list", list);
	    data.put("pi", pi);
	    return data;
	}
		
		@ResponseBody
		@PatchMapping("posts")
	    public int updatePostStatus(@RequestBody AdminPost post) {
	        return aService.updatePostStatus(post);
	    }
		
		
//		============================================================ 신고 =================================================================
		
	
	@GetMapping("report")
		public String adminReport() {
		return "views/admin/report";
	}

	
}
