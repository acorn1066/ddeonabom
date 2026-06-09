package kh.ddeonabom.admin.model.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kh.ddeonabom.admin.model.mapper.LandmarkApiMapper;
import kh.ddeonabom.admin.model.vo.ApiSyncLog;
import kh.ddeonabom.landmark.model.vo.Landmark;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandmarkApiService {

    private final LandmarkApiMapper mapper;
    private final RestTemplate restTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(LandmarkApiService.class);

    @Value("${tourapi.key}")
    private String apiKey;

    @Value("${tourapi.base-url}")
    private String baseUrl;

    private static final int NUM_OF_ROWS = 100;
    private static final int DAILY_LIMIT = 1000;


    // 현재 상태 조회
    public ApiSyncLog getSyncLog() {
        ApiSyncLog syncLog = mapper.selectSyncLog(apiKey);
        if (syncLog == null) {
            syncLog = new ApiSyncLog();
            syncLog.setApiKey(apiKey);
            mapper.insertSyncLog(syncLog);
            syncLog = mapper.selectSyncLog(apiKey);
        }
        syncLog.setCount(mapper.selectLandmarkCount());
        syncLog.setOverviewCount(mapper.selectOverviewCount());
        return syncLog;
    }

    // 이어서 수집
    public ApiSyncLog collect() {
    	ApiSyncLog syncLog = mapper.selectSyncLog(apiKey);

        // 일일 호출 초기화 체크
        resetDailyCallsIfNeeded(syncLog);

        int budget = DAILY_LIMIT - syncLog.getDailyCalls();
        if (budget <= 0) throw new RuntimeException("오늘 API 호출 한도를 초과했어요.");

        int startPage = syncLog.getLastPage() + 1;
        int maxPages = Math.min(60, budget);
        int endPage = startPage + maxPages - 1;

        // 첫 호출 시 totalPages 계산
        if (syncLog.getTotalPages() == 0) {
            int total = fetchTotalCount();
            syncLog.setTotalPages((int) Math.ceil((double) total / NUM_OF_ROWS));
        }

        endPage = Math.min(endPage, syncLog.getTotalPages());

        syncLog.setStatus("IN_PROGRESS");
        mapper.updateSyncLog(syncLog);

        for (int page = startPage; page <= endPage; page++) {
            List<Map<String, Object>> items = fetchPage(page);
            for (Map<String, Object> item : items) {
                Landmark vo = conversionAPI(item);
                mapper.mergeLandmark(vo);
            }
            logger.info("페이지 수집 완료: {}/{}", page, syncLog.getTotalPages());
            syncLog.setLastPage(page);
            syncLog.setDailyCalls(syncLog.getDailyCalls() + 1);
        }

        syncLog.setCollectSync(new java.sql.Date(new Date().getTime()));
        syncLog.setStatus(syncLog.getLastPage() >= syncLog.getTotalPages() ? "DONE" : "IDLE");
        mapper.updateSyncLog(syncLog);
        syncLog.setCount(mapper.selectLandmarkCount());
        syncLog.setOverviewCount(mapper.selectOverviewCount());

        return syncLog;
    }

    // 전체 업데이트
    public ApiSyncLog update() {
    	ApiSyncLog syncLog = mapper.selectSyncLog(apiKey);
        resetDailyCallsIfNeeded(syncLog);

        int budget = DAILY_LIMIT - syncLog.getDailyCalls();
        if (budget <= 0) throw new RuntimeException("오늘 API 호출 한도를 초과했어요.");

        // 업데이트 시작이면 0부터, 이어서면 lastPage부터
        int startPage = syncLog.getStatus().equals("UPDATING") ? syncLog.getLastPage() + 1 : 1;
        int endPage = Math.min(startPage + Math.min(60, budget) - 1, syncLog.getTotalPages());

        syncLog.setStatus("UPDATING");
        mapper.updateSyncLog(syncLog);

        for (int page = startPage; page <= endPage; page++) {
            List<Map<String, Object>> items = fetchPage(page);
            for (Map<String, Object> item : items) {
                mapper.mergeLandmark(conversionAPI(item));
            }
            logger.info("업데이트 완료: {}/{}", page, syncLog.getTotalPages());
            syncLog.setLastPage(page);
            syncLog.setDailyCalls(syncLog.getDailyCalls() + 1);
        }

        if (syncLog.getLastPage() >= syncLog.getTotalPages()) {
            syncLog.setStatus("DONE");
            syncLog.setUpdateSync(new java.sql.Date(new Date().getTime()));
            syncLog.setLastPage(0); // 다음 업데이트를 위해 초기화
        }

        mapper.updateSyncLog(syncLog);
        
        syncLog.setCount(mapper.selectLandmarkCount());
        syncLog.setOverviewCount(mapper.selectOverviewCount());
        
        return syncLog;
    }

    // API 호출 - 전체 건수
    private int fetchTotalCount() {
        String url = UriComponentsBuilder.fromUriString(baseUrl + "/areaBasedList2")
                .queryParam("serviceKey", apiKey)
                .queryParam("numOfRows", 1)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "ddeonabom")
                .queryParam("_type", "json")
                .queryParam("arrange", "A")
                .build().toUriString();

        Map res = restTemplate.getForObject(url, Map.class);
        Map body = (Map) ((Map) res.get("response")).get("body");
        return Integer.parseInt(body.get("totalCount").toString());
    }

    // API 호출 - 페이지별
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> fetchPage(int page) {
    	String url = UriComponentsBuilder.fromUriString(baseUrl + "/areaBasedList2")
    	        .queryParam("serviceKey", apiKey)
    	        .queryParam("numOfRows", NUM_OF_ROWS)
    	        .queryParam("pageNo", page)
    	        .queryParam("MobileOS", "ETC")
    	        .queryParam("MobileApp", "ddeonabom")
    	        .queryParam("_type", "json")
    	        .queryParam("arrange", "C")
    	        .build().toUriString();

        Map res = restTemplate.getForObject(url, Map.class);
        Map body = (Map) ((Map) res.get("response")).get("body");
        Map items = (Map) body.get("items");
        
        if (items == null) return List.of();
        return (List<Map<String, Object>>) items.get("item");
    }

    // API 응답 → VO 변환
    private Landmark conversionAPI(Map<String, Object> item) {
        Landmark landmark = new Landmark();
        landmark.setContentId(Integer.parseInt(item.getOrDefault("contentid", "0").toString()));
        landmark.setContentTypeId(Integer.parseInt(item.getOrDefault("contenttypeid", "0").toString()));
        landmark.setTitle(item.getOrDefault("title", "").toString());
        landmark.setAddr1(item.getOrDefault("addr1", "").toString());
        landmark.setAddr2(item.getOrDefault("addr2", "").toString());
        landmark.setFirstimage(item.getOrDefault("firstimage", "").toString());
        landmark.setFirstimage2(item.getOrDefault("firstimage2", "").toString()); // 버그 수정
        landmark.setMapx(parseDouble(item.get("mapx")));
        landmark.setMapy(parseDouble(item.get("mapy")));
        landmark.setTel(item.getOrDefault("tel", "").toString());
        return landmark;
    }
    
    
    private double parseDouble(Object val) {
        if (val == null) return 0.0;
        String s = val.toString().trim();
        if (s.isEmpty() || s.equals("null")) return 0.0;
        return Double.parseDouble(s);
    }

    // 일일 호출 수 초기화
    private void resetDailyCallsIfNeeded(ApiSyncLog log) {
        Date today = new Date();
        if (log.getLastResetDate() == null || !isSameDay(log.getLastResetDate(), today)) {
            log.setDailyCalls(0);
            log.setLastResetDate(new java.sql.Date(today.getTime()));
        }
    }

    private boolean isSameDay(Date d1, Date d2) {
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return c1.get(java.util.Calendar.YEAR) == c2.get(java.util.Calendar.YEAR)
            && c1.get(java.util.Calendar.DAY_OF_YEAR) == c2.get(java.util.Calendar.DAY_OF_YEAR);
    }
    
    public ApiSyncLog collectOverview() {
    	ApiSyncLog log = mapper.selectSyncLog(apiKey);
        resetDailyCallsIfNeeded(log);

        int budget = DAILY_LIMIT - log.getDailyCalls();
        if (budget <= 0) throw new RuntimeException("오늘 API 호출 한도를 초과했어요.");

        int limit = Math.min(budget, 500); // 한 번에 최대 500건
        List<Integer> contentIds = mapper.selectContentIdsForOverview(log.getLastOverviewId(), limit);

        for (int contentId : contentIds) {
            try {
                String overview = fetchOverview(contentId);
                mapper.updateOverview(contentId, overview);
                log.setLastOverviewId(contentId);
                log.setDailyCalls(log.getDailyCalls() + 1);
            } catch (Exception e) {
            	System.err.println("overview 수집 실패 contentId=" + contentId + " : " + e.getMessage());
            }
        }

        mapper.updateSyncLog(log);
        log.setCount(mapper.selectLandmarkCount());
        log.setOverviewCount(mapper.selectOverviewCount());
        return log;
    }

    private String fetchOverview(int contentId) {
        String url = UriComponentsBuilder.fromUriString(baseUrl + "/detailCommon2")
                .queryParam("serviceKey", apiKey)
                .queryParam("contentId", contentId)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "ddeonabom")
                .queryParam("_type", "json")
                .build().toUriString();

        Map res = restTemplate.getForObject(url, Map.class);
        Map body = (Map) ((Map) res.get("response")).get("body");
        Map items = (Map) body.get("items");
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");
        if (itemList == null || itemList.isEmpty()) return "";
        return itemList.get(0).getOrDefault("overview", "").toString();
    }
}