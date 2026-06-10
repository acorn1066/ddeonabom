package kh.ddeonabom.admin.controller;

import kh.ddeonabom.admin.model.service.LandmarkApiService;
import kh.ddeonabom.admin.model.vo.ApiSyncLog;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/landmark")
public class LandmarkApiController {

    private final LandmarkApiService landmarkApiService;

    // 현재 상태 조회
    @GetMapping("/status")
    public ResponseEntity<ApiSyncLog> status() {
        return ResponseEntity.ok(landmarkApiService.getSyncLog());
    }

    // 이어서 수집
    @PostMapping("/collect")
    public ResponseEntity<?> collect() {
        try {
            return ResponseEntity.ok(landmarkApiService.collect());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // overview 수집
    @PostMapping("/collect-overview")
    public ResponseEntity<?> collectOverview() {
        try {
            return ResponseEntity.ok(landmarkApiService.collectOverview());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 전체 업데이트
    @PostMapping("/update")
    public ResponseEntity<?> update() {
        try {
            return ResponseEntity.ok(landmarkApiService.update());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}