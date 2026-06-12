package kh.ddeonabom.landmark.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kh.ddeonabom.landmark.model.service.LandmarkService;
import kh.ddeonabom.landmark.model.vo.Landmark;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/landmark")
public class LandmarkAjaxController {
    private final LandmarkService lService;

    @GetMapping("/search")
    public Map<String, Object> search(
    		@RequestParam(name = "wished", defaultValue = "false") boolean wished,
            @RequestParam(name = "q", defaultValue = "") String q,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "region", defaultValue = "") String region) {
        
        Map<String, Object> result = new HashMap<>();
        ArrayList<Landmark> content = null;
        int total = 0;
        if(!wished) {
        	content = lService.searchLandmarks(q, region, page, size);
        	total = lService.countLandmarks(q,region);
        }
        else {
        	content = new ArrayList<Landmark>();
        	total = 0;
        }
        
        result.put("content", content);
        result.put("totalElements", total);
        return result;
    }
}