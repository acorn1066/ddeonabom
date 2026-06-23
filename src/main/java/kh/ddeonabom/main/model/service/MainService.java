package kh.ddeonabom.main.model.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kh.ddeonabom.landmark.model.vo.Landmark;
import kh.ddeonabom.main.model.mapper.MainMapper;
import kh.ddeonabom.schedule.model.vo.RoutePoint;
import kh.ddeonabom.schedule.model.vo.ScheduleMain;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MainMapper mainMapper;
    @Value("${kakao.mobility.rest-key}")
    private String kakaoMobilityKey;
    
    public Map<String, Object> getMainStats() {
        return mainMapper.selectMainStats();
    }

    public List<Landmark> getPopularLandmarks() {
        return mainMapper.selectPopularLandmarks();
    }

	public ScheduleMain selectFeaturedSchedule() {
		return mainMapper.selectFeaturedSchedule();
	}
	
	public List<Map<String, Double>> getFeaturedRoute(String coords) {
	    List<Map<String, Double>> path = new ArrayList<>();
	    if (coords == null || coords.isBlank()) return path;

	    List<RoutePoint> points = Arrays.stream(coords.split(";"))
	        .map(pair -> {
	            String[] xy = pair.split(",");
	            return new RoutePoint(Double.parseDouble(xy[1]), Double.parseDouble(xy[0]));
	        })
	        .collect(Collectors.toList());

	    if (points.size() < 2) return path;
	    if (points.size() > 7) points = points.subList(0, 7);

	    RoutePoint origin = points.get(0);
	    RoutePoint dest   = points.get(points.size() - 1);

	    StringBuilder waypoints = new StringBuilder();
	    for (int i = 1; i < points.size() - 1; i++) {
	        if (waypoints.length() > 0) waypoints.append("|");
	        RoutePoint w = points.get(i);
	        waypoints.append(w.getLng()).append(",").append(w.getLat());
	    }

	    try {
	        StringBuilder url = new StringBuilder("https://apis-navi.kakaomobility.com/v1/directions");
	        url.append("?origin=").append(origin.getLng()).append(",").append(origin.getLat());
	        url.append("&destination=").append(dest.getLng()).append(",").append(dest.getLat());
	        if (waypoints.length() > 0) {
	            url.append("&waypoints=").append(URLEncoder.encode(waypoints.toString(), StandardCharsets.UTF_8));
	        }

	        HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Authorization", "KakaoAK " + kakaoMobilityKey);
	        conn.setRequestProperty("Content-Type", "application/json");

	        if (conn.getResponseCode() != 200) return path;

	        ObjectMapper om = new ObjectMapper();
	        JsonNode root = om.readTree(conn.getInputStream());
	        JsonNode sections = root.path("routes").path(0).path("sections");

	        for (JsonNode section : sections) {
	            for (JsonNode road : section.path("roads")) {
	                JsonNode vertexes = road.path("vertexes");
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

	    return path;
	}
}