package kh.ddeonabom.landmark.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.landmark.model.service.LandmarkService;
import kh.ddeonabom.landmark.model.vo.Landmark;
import kh.ddeonabom.member.model.vo.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/landmark")
public class LandmarkController {
	private final LandmarkService lService;
	
	// 관광지 리스트 가져오기
	@GetMapping("list")
	public String selectList(@RequestParam(value = "page", defaultValue = "1") int currentPage,
								@RequestParam(value = "contentTypeId", required = false) Integer contentTypeId,
								@RequestParam(value = "area", required = false) String area,
								@RequestParam(value = "keyword", required = false) String keyword,
								Model model, HttpServletRequest request, HttpSession session) {
		
		int listCount = lService.getListCount(contentTypeId, area, keyword);
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10, 20);
		ArrayList<Landmark> list = lService.selectLandmarkList(pi, contentTypeId, area, keyword);
		
		Map<Integer, String> contentType = new HashMap<>();
		contentType.put(12, "관광지");
		contentType.put(14, "문화시설");
		contentType.put(15, "축제/공연/행사");
		contentType.put(25, "여행코스");
		contentType.put(28, "레포츠");
		contentType.put(32, "숙박");
		contentType.put(38, "쇼핑");
		contentType.put(39, "음식점");
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		if(loginUser != null) {
			int memberNo = loginUser.getMemberNo();
			Set<Integer> niceList = lService.niceList(memberNo);
			model.addAttribute("niceList", niceList);
		}
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("area", area);
		model.addAttribute("contentTypeId", contentTypeId);
		model.addAttribute("contentType", contentType);
		model.addAttribute("loc", request.getRequestURI());
		model.addAttribute("list", list).addAttribute("pi", pi);
		return "views/landmark/list";
	} 
	
	// 관광지 세부사항 가져오기
	@GetMapping("/{contentId}/{page}")
	public String landmarkDetail(@PathVariable("contentId") int contentId, @PathVariable("page") int page,
									Model model, HttpSession session) {
		Landmark land = lService.landmarkDetail(contentId);
		
		//System.out.println(land);
		Map<Integer, String> contentType = new HashMap<>();
		contentType.put(12, "관광지");
		contentType.put(14, "문화시설");
		contentType.put(15, "축제/공연/행사");
		contentType.put(25, "여행코스");
		contentType.put(28, "레포츠");
		contentType.put(32, "숙박");
		contentType.put(38, "쇼핑");
		contentType.put(39, "음식점");
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		if(loginUser != null) {
			int memberNo = loginUser.getMemberNo();			
			int isNice = lService.landmarkNice(contentId, memberNo);
			model.addAttribute("isNice", isNice);			
		} 
		
		
		model.addAttribute("contentType", contentType);
		
		model.addAttribute("land", land);
		return "views/landmark/detail";			
		
	}
	
	@ResponseBody
	@PostMapping("nice")
	public int landmarkNice(@RequestParam("lNumber") int lNumber, @RequestParam("memberNo") int memberNo) {
		int result = lService.landmarkNice(lNumber, memberNo);
		
		int newState;
		if(result > 0) {
			lService.deleteNice(lNumber,memberNo);
			newState = 0;
		} else {
			lService.insertNice(lNumber, memberNo);
			newState = 1;
		}
		
		return newState;
	}
	
	@ResponseBody
	@PostMapping("listNice")
	public int listNice(@RequestParam("contentId") int contentId, @RequestParam("memberNo") int memberNo) {
		
		int result = lService.landmarkNice(contentId, memberNo);
		System.out.println(result);
		
		int newState;
		if(result > 0) {
			lService.deleteNice(contentId, memberNo);
			newState = 0;
		} else {
			lService.insertNice(contentId, memberNo);
			newState = 1;
		}
		
		return newState;
	}
	
	
	
	
}
