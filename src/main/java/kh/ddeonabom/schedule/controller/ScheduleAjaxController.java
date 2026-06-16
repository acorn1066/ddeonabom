package kh.ddeonabom.schedule.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kh.ddeonabom.member.model.vo.Member;
import kh.ddeonabom.schedule.model.vo.RoutePoint;
import kh.ddeonabom.schedule.model.vo.RouteRequest;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import kh.ddeonabom.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleAjaxController {
    private final ScheduleService sService;
    @Value("${kakao.mobility.rest-key}")
    private String kakaoMobilityKey;
    
    
    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> saveSchedule(@RequestBody ScheduleMain schedule, HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            result.put("success", false);
            result.put("needLogin", true);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        schedule.setMemberNo(loginUser.getMemberNo());

        try {
            int scheduleNo;
            if (schedule.getScheduleNo() != 0) {
                // scheduleNo 있으면 수정
                scheduleNo = sService.updateSchedule(schedule);
            } else {
                // 없으면 신규
                scheduleNo = sService.saveSchedule(schedule);
            }
            result.put("success", true);
            result.put("scheduleNo", scheduleNo);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "저장 중 오류가 발생했습니다.");
        }
        return result;
    }
    
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteSchedule(@RequestParam("scheduleNo") int scheduleNo,
                                              HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            result.put("success", false);
            result.put("needLogin", true);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        int rows = sService.deleteSchedule(scheduleNo, loginUser.getMemberNo());
        result.put("success", rows > 0);
        return result;
    }
    
    
    @PostMapping("/route")
    public Map<String, Object> getRoute(@RequestBody RouteRequest req) {
        List<RoutePoint> points = req.getPoints();
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Double>> path = new ArrayList<>();

        if (points == null || points.size() < 2) {
            result.put("path", path);
            return result;
        }

        // origin / destination / waypoints 조립 (경도,위도 순)
        RoutePoint origin = points.get(0);
        RoutePoint dest = points.get(points.size() - 1);
        String originStr = origin.getLng() + "," + origin.getLat();
        String destStr = dest.getLng() + "," + dest.getLat();

        StringBuilder waypoints = new StringBuilder();
        for (int i = 1; i < points.size() - 1; i++) {
            if (waypoints.length() > 0) waypoints.append("|");
            RoutePoint w = points.get(i);
            waypoints.append(w.getLng()).append(",").append(w.getLat());
        }

        try {
            StringBuilder url = new StringBuilder("https://apis-navi.kakaomobility.com/v1/directions");
            url.append("?origin=").append(originStr);
            url.append("&destination=").append(destStr);
            if (waypoints.length() > 0) {
                url.append("&waypoints=").append(
                    URLEncoder.encode(waypoints.toString(), StandardCharsets.UTF_8));
            }

            HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "KakaoAK " + kakaoMobilityKey);
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() != 200) {
                result.put("path", path);
                return result;
            }

            // 응답 파싱: routes[0].sections[].roads[].vertexes
            ObjectMapper om = new ObjectMapper();
            JsonNode root = om.readTree(conn.getInputStream());
            JsonNode sections = root.path("routes").path(0).path("sections");

            for (JsonNode section : sections) {
                for (JsonNode road : section.path("roads")) {
                    JsonNode vertexes = road.path("vertexes");
                    // [x, y, x, y, ...] → 짝수=경도, 홀수=위도
                    for (int i = 0; i + 1 < vertexes.size(); i += 2) {
                        Map<String, Double> c = new HashMap<>();
                        c.put("lng", vertexes.get(i).asDouble());
                        c.put("lat", vertexes.get(i + 1).asDouble());
                        path.add(c);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.put("path", path);
        return result;
    }
    
    
    @PostMapping("/visibility")
    @ResponseBody
    public Map<String, Object> toggleVisibility(@RequestParam("scheduleNo") int scheduleNo,
                                                HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            result.put("success", false);
            result.put("needLogin", true);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        try {
            String visibility = sService.toggleVisibility(scheduleNo, loginUser.getMemberNo());
            result.put("success", true);
            result.put("visibility", visibility);   // 바뀐 값 'Y'/'N'
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "공유 설정 변경에 실패했어요.");
        }
        return result;
    }
}