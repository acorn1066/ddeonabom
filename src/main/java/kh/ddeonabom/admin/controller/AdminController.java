package kh.ddeonabom.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.admin.model.service.AdminService;
import kh.ddeonabom.admin.model.vo.AdminNotice;
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
	
	@ResponseBody
	@GetMapping("/members")
	public ArrayList<Member> selectMembers(HttpSession session) {
		String id = ((Member)session.getAttribute("loginUser")).getId();
	    return aService.selectMembers(id);
	}
	
	@ResponseBody
	@PutMapping("members")
	public int updateMemberStatus(@RequestBody HashMap<String, String> map) {

	    map.put("col", "status");

	    return aService.updateMemberStatus(map);
	}
	
	
	
	
//	@PostMapping("/member/status")
//	public ResponseEntity<?> updateMemberStatus(
//	        @RequestParam("memberNo") int memberNo,
//	        @RequestParam("status") String status) {
//
//	    AdminService.updateMemberStatus(memberNo, status);
//
//	    return ResponseEntity.ok().build();
//	}
	
	
	@ResponseBody
	@GetMapping("/notice")
	public ArrayList<AdminNotice> selectNoticeList() {
	    return aService.selectNoticeList();
	}
	
	
	@GetMapping("post")
	public String adminPost() {
		return "views/admin/post";
	}
	
	@GetMapping("report")
		public String adminReport() {
		return "views/admin/report";
	}
	@GetMapping("nwrite")
		public String adminNoticeWirte() {
		return "views/admin/nwrite";
	}
	
	@GetMapping("nedit")
		public String adminNoticeEdit() {
		return "views/admin/nedit";
	}
	
}
