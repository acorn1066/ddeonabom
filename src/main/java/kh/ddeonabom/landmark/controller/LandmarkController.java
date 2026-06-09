package kh.ddeonabom.landmark.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
//	@GetMapping("landmark")
//	@ResponseBody
//	public String getLandmark() {
//		StringBuilder sb = new StringBuilder();
//		try {
//			StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/KorService2/areaCode2"); /*URL*/
//			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=00656293aba3713243da5226862c9b26b483fe08e8266ab5fc94b7c60851e7cb"); /*Service Key*/
//			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
//			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
//			urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("WEB", "UTF-8")); /*OS 구분*/
//			urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("WebTest", "UTF-8")); /*서비스명*/
//			urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
//			urlBuilder.append("&" + URLEncoder.encode("arrange","UTF-8") + "=" + URLEncoder.encode("C", "UTF-8")); /*거리반경*/
//			urlBuilder.append("&" + URLEncoder.encode("mapX","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*지점의 X 좌표값*/
//			urlBuilder.append("&" + URLEncoder.encode("mapY","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*지점의 Y 좌표값*/
//			urlBuilder.append("&" + URLEncoder.encode("radius","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*정렬 구분*/
//			urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*관광타입 ID*/
//			urlBuilder.append("&" + URLEncoder.encode("lDongRegnCd","UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*법정동 시도 코드*/
//			urlBuilder.append("&" + URLEncoder.encode("lDongSignguCd","UTF-8") + "=" + URLEncoder.encode("140", "UTF-8")); /*법정동 시군구 코드*/
//			
//			URL url = (new URI(urlBuilder.toString())).toURL();
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("GET");
//			conn.setRequestProperty("Content-type", "application/json");
//			System.out.println("Response code: " + conn.getResponseCode());
//			BufferedReader rd;
//			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			} else {
//				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//			}
//			
//			String line;
//			while ((line = rd.readLine()) != null) {
//				sb.append(line);
//			}
//			rd.close();
//			conn.disconnect();
//			System.out.println(sb.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		return sb.toString();
//	}
	
}
