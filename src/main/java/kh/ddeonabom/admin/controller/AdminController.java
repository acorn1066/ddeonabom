package kh.ddeonabom.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")

public class AdminController {
	
	@GetMapping("dash")
		public String adminDash() {
		return "views/admin/dash";
	}
	
	@GetMapping("member")
		public String adminMember() {
		return "views/admin/member";
	}
	
	@GetMapping("post")
		public String adminPost() {
		return "views/admin/post";
	}
	
	@GetMapping("notice")
		public String adminNotice() {
		return "views/admin/notice";
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
