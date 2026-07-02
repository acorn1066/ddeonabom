package kh.ddeonabom.landmark.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.services.s3.AmazonS3;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.common.paging.Pagination;
import kh.ddeonabom.landmark.model.service.LandmarkService;
import kh.ddeonabom.landmark.model.vo.LandReview;
import kh.ddeonabom.landmark.model.vo.Landmark;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.review.model.vo.Image;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/landmark")
public class LandmarkController {
	private final LandmarkService lService;
	
	@Value("${kakao.map.api-key}")
	private String kakaoMapApiKey;
	
	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}") 
	private String bucket;
	
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
//		System.out.println(list);
		Map<Integer, Double> rating = new HashMap<>();
		Map<Integer, Integer> review = new HashMap<>();
		for(Landmark l : list) {
			double listRating = lService.rating(l.getContentId());
			rating.put(l.getContentId(), listRating);

			int reviewCount = lService.reviewCount(l.getContentId());
			review.put(l.getContentId(), reviewCount);
		}

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
		
		model.addAttribute("review", review);
		model.addAttribute("rating", rating);
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
									Model model, HttpSession session, @RequestParam(value="page", defaultValue="1") int currentPage,
									HttpServletRequest request) {
		Landmark land = lService.landmarkDetail(contentId); 
		int reviewCount = lService.reviewCount(contentId);
		double reviewRating = lService.rating(contentId);
//		System.out.println(reviewRating);
//		System.out.println(reviewCount);
		PageInfo pi = Pagination.getPageInfo(currentPage, reviewCount, 5, 6);
		Map<Integer, List<Image>> reviewImage = new HashMap<>();
		ArrayList<LandReview> landReview = lService.review(contentId, pi);
		for(LandReview re : landReview) {
			ArrayList<Image> image = lService.image(re.getTravelSubNo());
			reviewImage.put(re.getTravelSubNo(), image);
		}
//		ArrayList<Image> reviewImage = lService.image(contentId); 
		
		System.out.println(landReview);
		
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
		
		model.addAttribute("reviewImage", reviewImage);
		model.addAttribute("reviewRating", reviewRating);
		model.addAttribute("loc", request.getRequestURI());
		model.addAttribute("landReview", landReview).addAttribute("pi", pi);
		model.addAttribute("contentType", contentType);		
		model.addAttribute("kakaoMapApiKey", kakaoMapApiKey);
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
