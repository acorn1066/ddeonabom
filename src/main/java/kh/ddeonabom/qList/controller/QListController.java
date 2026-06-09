package kh.ddeonabom.qList.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView selectQList(@RequestParam(value="page", defaultValue="1") int currentPage, ModelAndView mv) {
		int listCount = qListService.getListCount();
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 8, 8);
		ArrayList<QList> qList = qListService.selectQList(pi);
//		System.out.println(qList);
		
		mv.addObject("qList", qList).addObject("pi", pi).setViewName("views/qList/list");
		
		return mv;
	}
	
	@GetMapping("write")
	public String writeQList() {
		return "views/qList/write";
	}
	
	@GetMapping("detail")
	public ModelAndView detailQList(@RequestParam("qNo") int qNo, ModelAndView mv) {
	    
	    QList q = qListService.detailQList(1);  // 단건 조회 서비스 호출
	    System.out.println(q);
	    mv.addObject("q", q)
	      .setViewName("views/qList/detail");
	    
	    return mv;
	}
}
