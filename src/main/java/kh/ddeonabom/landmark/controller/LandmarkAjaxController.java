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
            @RequestParam(name = "q", defaultValue = "") String q,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "region", defaultValue = "") String region) {
        
        Map<String, Object> result = new HashMap<>();
        
        ArrayList<Landmark> content = lService.searchLandmarks(q, region, page, size);
        
        int total = lService.countLandmarks(q,region);
        
        result.put("content", content);
        result.put("totalElements", total);
        return result;
    }
}