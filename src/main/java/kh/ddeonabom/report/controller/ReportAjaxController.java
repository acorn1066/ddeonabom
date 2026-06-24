package kh.ddeonabom.report.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.admin.model.service.AdminService;
import kh.ddeonabom.admin.model.vo.AdminReport;
import kh.ddeonabom.member.model.vo.Member;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportAjaxController {

	private final AdminService adminService;

	@PostMapping("/insert")
	public Map<String, Object> insertReport(
			@RequestParam("targetType") String targetType,
			@RequestParam("targetNo")   int    targetNo,
			@RequestParam("reason")     String reason,
			HttpSession session) {

		Map<String, Object> result = new HashMap<>();
		Member loginUser = (Member) session.getAttribute("loginUser");

		// 비로그인 차단
		if (loginUser == null) {
			result.put("status", "error");
			result.put("message", "로그인이 필요합니다.");
			return result;
		}

		AdminReport report = new AdminReport();
		report.setMemberNo(loginUser.getMemberNo());
		report.setTargetType(targetType);
		report.setTargetNo(targetNo);
		report.setReason(reason);

		int res = adminService.insertReport(report);

		if (res == -1) {
			result.put("status", "duplicate");
			result.put("message", "이미 신고한 콘텐츠입니다.");
		} else if (res > 0) {
			result.put("status", "success");
			result.put("message", "신고가 접수되었습니다.");
		} else {
			result.put("status", "error");
			result.put("message", "오류가 발생했습니다.");
		}

		return result;
	}

}
