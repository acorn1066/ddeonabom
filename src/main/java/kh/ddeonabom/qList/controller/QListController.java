package kh.ddeonabom.qList.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.qList.model.vo.QList;
import kh.ddeonabom.qList.service.QListService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qList")
public class QListController {
	private final QListService qListService;
	
	@GetMapping("list")
	public void selectQList(@RequestParam(value="page", defaultValue="1") int currentPage) {
		int listCount = qListService.getListCount();
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 8, 8);
		ArrayList<QList> qList = qListService.selectQList(pi, 1);
		System.out.println(qList);
	}
	
	
}
