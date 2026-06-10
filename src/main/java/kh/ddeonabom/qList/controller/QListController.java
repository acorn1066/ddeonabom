package kh.ddeonabom.qList.controller;

import java.util.ArrayList;
import java.util.HashMap;

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
	public ModelAndView selectQList(
	        @RequestParam(value="page", defaultValue="1") int currentPage,
	        @RequestParam(value="category", defaultValue="전체") String category,
	        @RequestParam(value="searchType", defaultValue="titleContent") String searchType,
	        @RequestParam(value="searchInput", defaultValue="") String searchInput,
	        ModelAndView mv) {

	    HashMap<String, Object> map = new HashMap<>();
	    map.put("category",    category);
	    map.put("searchType",  searchType);
	    map.put("searchInput", searchInput);

	    // 1) 검색 조건 적용된 총 게시글 수
	    int listCount = qListService.getListCount(map);
	    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 8, 8);

	    // 2) 페이징 정보도 map에 추가 → Mapper XML에서 OFFSET에 사용
	    int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit(); // (현재페이지-1) * 목록수
	    map.put("startRow",  startRow);   
	    map.put("listLimit", pi.getBoardLimit());  // 한 페이지 목록 수

	    // 3) map을 같이 넘겨야 필터링 + 페이징이 적용됨
	    ArrayList<QList> qList = qListService.selectQList(map);

	    mv.addObject("qList",      qList)
	      .addObject("pi",         pi)
	      // 4) 검색 조건도 뷰로 전달해야 페이징 링크에서 조건 유지 가능
	      .addObject("category",   category)
	      .addObject("searchType", searchType)
	      .addObject("searchInput",searchInput)
	      .setViewName("views/qList/list");

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
