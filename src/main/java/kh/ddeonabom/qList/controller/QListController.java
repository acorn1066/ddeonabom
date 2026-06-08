package kh.ddeonabom.qList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.ddeonabom.qList.service.QListService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qList")
public class QListController {
	private final QListService qService;
	
	@GetMapping("list")
	public String qList() {
		int listCount = qService.getListCount();
		System.out.println(listCount);
		
		return "views/qList/qBoard";
	}
	
	
}
