package kh.ddeonabom.landmark.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/landmark")
public class LandmarkController {
//	private final LandmarkService lService;
	
	@GetMapping("list")
	public String selectList() {
		return "views/landmark/list.html";
	}
	
//	@PostMapping("update") 
//	public String insertLandmark() {
//		ArrayList<Landmark> result = lService.insertLandmark();
		
//		if(result != null) {
//			return "redirect:/landmark/list"; 
//		} else{
//			throw new LandmarkException("관광지 업데이트를 실패하였습니다.");
//		}
		
//	}
	
}
