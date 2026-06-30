package kh.ddeonabom.admin.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
import kh.ddeonabom.admin.model.vo.AdminReport;
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
	    map.put("todayMemberCount", aService.getTodayMemberCount()); 

	    return map;
	}
	
	@ResponseBody
	@GetMapping("/logs")
	public TreeMap<String, Integer> getLogs() {
	    File f = new File("C:/logs/ddeonabom/login/");
	    File[] files = f.listFiles();

	    TreeMap<String, Integer> dateCount = new TreeMap<>();
	    BufferedReader br = null;
	    try {
	        for (File file : files) {
	            br = new BufferedReader(new FileReader(file));
	            String data;
	            while ((data = br.readLine()) != null) {
	                String date = data.split(" ")[0];
	                if (!dateCount.containsKey(date)) {
	                    dateCount.put(date, 1);
	                } else {
	                    dateCount.put(date, dateCount.get(date) + 1);
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (br != null) br.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return dateCount;
	}
	
	@ResponseBody
	@GetMapping("/activity")
	public HashMap<String, Object> getBoardActivity() {
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("schedule", aService.selectScheduleActivity());
	    map.put("qlist", aService.selectQlistActivity());
	    map.put("travel", aService.selectTravelActivity());
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
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "keyword", defaultValue = "") String keyword,
	        HttpSession session) {

	    Member loginUser = (Member)session.getAttribute("loginUser");
	    // map 여기서 만들어서 바로 mapper 호출
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("id", loginUser.getId());
	    map.put("keyword", keyword);

	    int listCount = aService.selectMemberCountList(map);  // Service는 map만 받도록
	    PageInfo pi = Pagination.getPageInfo(page, listCount, 10, 10);
	    map.put("startRow", (pi.getCurrentPage() - 1) * pi.getBoardLimit());
	    map.put("listLimit", pi.getBoardLimit());

	    ArrayList<Member> list = aService.selectMembers(map);

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
	public HashMap<String, Object> notice(
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "keyword", defaultValue = "") String keyword) {

	    HashMap<String, Object> map = new HashMap<>();
	    map.put("keyword", keyword);

	    int listCount = aService.getNoticeCount(map);
	    PageInfo pi = Pagination.getPageInfo(page, listCount, 10, 10);

	    map.put("startRow", (pi.getCurrentPage() - 1) * pi.getBoardLimit());
	    map.put("listLimit", pi.getBoardLimit());

	    HashMap<String, Object> data = new HashMap<>();
	    data.put("list", aService.selectNoticeList(map));
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
	
	@GetMapping("/posts")
	@ResponseBody
	public HashMap<String, Object> posts(
	        @RequestParam(value = "category") String category,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "keyword", defaultValue = "") String keyword,
	        @RequestParam(value = "searchType", defaultValue = "전체") String searchType) {

	    HashMap<String, Object> map = new HashMap<>();
	    map.put("keyword", keyword);
	    map.put("searchType", searchType);

	    int listCount = aService.getPostCount(category, map);
	    PageInfo pi = Pagination.getPageInfo(page, listCount, 10, 10);

	    map.put("startRow", (pi.getCurrentPage() - 1) * pi.getBoardLimit());
	    map.put("listLimit", pi.getBoardLimit());

	    HashMap<String, Object> data = new HashMap<>();
	    data.put("list", aService.selectPostList(category, map));
	    data.put("pi", pi);
	    return data;
	}
		
		@ResponseBody
		@PatchMapping("posts")
	    public int updatePostStatus(@RequestBody AdminPost post) {
	        return aService.updatePostStatus(post);
	    }
		
		
//		============================================================ 신고 =================================================================
		
	
		@ResponseBody
		@GetMapping("/reports")
		public HashMap<String, Object> reportList(
		        @RequestParam("targetType") String targetType,
		        @RequestParam(value = "status", defaultValue = "") String status,
		        @RequestParam(value = "keyword", defaultValue = "") String keyword,
		        @RequestParam(value = "page", defaultValue = "1") int page) {

		    int listCount = aService.getReportCount(targetType, status, keyword);
		    PageInfo pi = Pagination.getPageInfo(page, listCount, 10, 10);
		    ArrayList<AdminReport> list = aService.selectReportList(targetType, status, keyword, pi);

		    HashMap<String, Object> data = new HashMap<>();
		    data.put("list", list);
		    data.put("pi", pi);
		    return data;
		}

		@ResponseBody
		@PatchMapping("reports")
		public int updateReportStatus(@RequestBody AdminReport report) {
		    if ("R".equals(report.getReportStatus())) {
		        return aService.updateStatusByTarget(report);
		    }
		    return aService.updateReportStatus(report);
		}
		
		@ResponseBody
		@PatchMapping("reports/process")
		public int processReport(@RequestBody AdminReport report) {
			report.setReportStatus("Y");
		    aService.updateTargetStatus(report);
		    return aService.updateStatusByTarget(report);
		}

	
}
