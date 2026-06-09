package kh.ddeonabom.landmark.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.ddeonabom.landmark.model.exception.LandmarkException;
import kh.ddeonabom.landmark.model.service.LandmarkService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/landmark")
public class LandmarkController {
	private final LandmarkService lService;
	
	@GetMapping("list")
	public String selectList() {
		return "views/landmark/list.html";
	}
	
	@PostMapping("update")
	public String updateLandmark() {
		String result = lService.updateLandmark();
		
		if(result != null) {
			return "redirect:/landmark/list"; 
		} else{
			throw new LandmarkException("관광지 업데이트를 실패하였습니다.");
		}
		
	}
	
}
