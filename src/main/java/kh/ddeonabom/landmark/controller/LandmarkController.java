package kh.ddeonabom.landmark.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.landmark.model.service.LandmarkService;
import kh.ddeonabom.landmark.model.vo.Landmark;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/landmark")
public class LandmarkController {
	private final LandmarkService lService;
	
	// 관광지 리스트 가져오기
	@GetMapping("list")
	public String selectList(@RequestParam(value = "page", defaultValue = "1") int currentPage,
								Model model, HttpServletRequest request) {
		
		int listCount = lService.getListCount();
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10, 20);
		ArrayList<Landmark> list = lService.selectLandmarkList(pi);
		
		Map<Integer, String> contentType = new HashMap<>();
		contentType.put(12, "관광지");
		contentType.put(14, "문화시설");
		contentType.put(15, "축제/공연/행사");
		contentType.put(25, "여행코스");
		contentType.put(28, "레포츠");
		contentType.put(32, "숙박");
		contentType.put(38, "쇼핑");
		contentType.put(39, "음식점");
		
		model.addAttribute("contentType", contentType);
		model.addAttribute("loc", request.getRequestURI());
		model.addAttribute("list", list).addAttribute("pi", pi);
		return "views/landmark/list";
	} 
	
	// 관광지 세부사항 가져오기
	@GetMapping("/{contentId}/{page}")
	public String landmarkDetail(@PathVariable("contentId") int contentId, @PathVariable("page") int page,
									Model model) {
		Landmark land = lService.landmarkDetail(contentId);
		//System.out.println(land);
		
		
		
		model.addAttribute("land", land);
		return "views/landmark/detail";			
		
	}
	
	
	
}
