package kh.ddeonabom.landmark.controller;


import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5, 20);
		ArrayList<Landmark> list = lService.selectLandmarkList(pi);
		
		model.addAttribute("loc", request.getRequestURI());
		model.addAttribute("list", list).addAttribute("pi", pi);
		return "views/landmark/list";
		
	} 
	
	
	
}
