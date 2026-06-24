# 신고 기능 구현 — 변경 요약

질문 게시판 글/댓글 신고 기능 추가 작업입니다.
로그인 상태에서 타인의 글·댓글에만 신고 버튼이 노출되며, 중복 신고는 차단합니다.

---

## 변경 파일 목록

| # | 파일 | 유형 |
|---|------|------|
| 1 | `src/main/resources/mappers/admin-mapper.xml` | 수정 |
| 2 | `src/main/java/kh/ddeonabom/admin/model/mapper/AdminMapper.java` | 수정 |
| 3 | `src/main/java/kh/ddeonabom/admin/model/service/AdminService.java` | 수정 |
| 4 | `src/main/java/kh/ddeonabom/report/controller/ReportAjaxController.java` | **신규** |
| 5 | `src/main/resources/templates/views/qList/detail.html` | 수정 |

---

## 1. admin-mapper.xml

중복 체크 SELECT + 신고 INSERT 쿼리 추가.
`REPORT_STATUS` 컬럼은 `data_default = 'N'`이므로 INSERT 컬럼 목록에서 제외.

```diff
+ <select id="checkDuplicateReport" parameterType="AdminReport" resultType="_int">
+     SELECT COUNT(*)
+     FROM REPORT
+     WHERE MEMBER_NO   = #{memberNo}
+       AND TARGET_TYPE = #{targetType}
+       AND TARGET_NO   = #{targetNo}
+ </select>

+ <insert id="insertReport" parameterType="AdminReport">
+     INSERT INTO REPORT (REPORT_NO, TARGET_TYPE, TARGET_NO, REASON, REPORT_DATE, MEMBER_NO)
+     VALUES (SEQ_REPORT.NEXTVAL, #{targetType}, #{targetNo}, #{reason}, SYSDATE, #{memberNo})
+ </insert>
```

---

## 2. AdminMapper.java

인터페이스에 메서드 2개 추가.

```diff
  int updateTargetStatus(AdminReport report);
  int selectMemberCountList(HashMap<String, Object> map);
+
+ int insertReport(AdminReport report);
+ int checkDuplicateReport(AdminReport report);
```

---

## 3. AdminService.java

중복 체크 후 INSERT. 중복이면 `-1` 반환.

```diff
  public int processReport(AdminReport report) { ... }
+
+ public int insertReport(AdminReport report) {
+     if (mapper.checkDuplicateReport(report) > 0) return -1;
+     return mapper.insertReport(report);
+ }
```

### 반환값 규약

| 반환값 | 의미 |
|--------|------|
| `-1` | 중복 신고 (같은 회원이 같은 대상을 이미 신고함) |
| `1` | 신고 INSERT 성공 |
| `0` | INSERT 실패 |

> **중복 신고 기준**: `MEMBER_NO + TARGET_TYPE + TARGET_NO` 3개 조합이 REPORT 테이블에 이미 존재하는 경우

---

## 4. ReportAjaxController.java (신규)

`@RestController` — JSON 응답 반환, 페이지 이동 없음.

```diff
+ package kh.ddeonabom.report.controller;
+
+ @RestController
+ @RequestMapping("/report")
+ @RequiredArgsConstructor
+ public class ReportAjaxController {
+
+     private final AdminService adminService;
+
+     @PostMapping("/insert")
+     public Map<String, Object> insertReport(
+             @RequestParam String targetType,
+             @RequestParam int    targetNo,
+             @RequestParam String reason,
+             HttpSession session) {
+
+         Map<String, Object> result = new HashMap<>();
+         Member loginUser = (Member) session.getAttribute("loginUser");
+
+         if (loginUser == null) {
+             result.put("status", "error");
+             result.put("message", "로그인이 필요합니다.");
+             return result;
+         }
+
+         AdminReport report = new AdminReport();
+         report.setMemberNo(loginUser.getMemberNo());
+         report.setTargetType(targetType);
+         report.setTargetNo(targetNo);
+         report.setReason(reason);
+
+         int res = adminService.insertReport(report);
+
+         if (res == -1) {
+             result.put("status", "duplicate");
+             result.put("message", "이미 신고한 콘텐츠입니다.");
+         } else if (res > 0) {
+             result.put("status", "success");
+             result.put("message", "신고가 접수되었습니다.");
+         } else {
+             result.put("status", "error");
+             result.put("message", "오류가 발생했습니다.");
+         }
+         return result;
+     }
+ }
```

### targetType 값 규약

| 값 | 대상 |
|----|------|
| `question` | 질문 게시판 글 (QLIST.Q_NO) |
| `reply` | 댓글 (REPLY.REPLY_NO) |

### reason 값 규약

| 값 | 표시 텍스트 |
|----|-------------|
| `A` | 광고 |
| `B` | 욕설 |
| `C` | 음란 |
| `D` | 도배 |

---

## 5. detail.html

### 글 신고 버튼

수정/삭제 div 바로 아래 — 본인 글이 아닐 때만 노출.

```diff
  <!-- 수정/삭제: 본인 글일 때 -->
  <div class="ml-auto flex gap-2 text-13 text-muted"
       th:if="${session.loginUser != null and session.loginUser.memberNo == q.memberNo}">
    ...
  </div>
+
+ <!-- 글 신고: 본인 글이 아닐 때 -->
+ <div class="ml-auto flex gap-2 text-13 text-muted"
+      th:if="${session.loginUser != null and session.loginUser.memberNo != q.memberNo}">
+   <button type="button" class="hover:text-red-500"
+           th:onclick="|openReportModal('question', [[${q.qNo}]])|">신고</button>
+ </div>
```

### 댓글 신고 버튼

수정/삭제 span 바로 아래 — 본인 댓글이 아닐 때만 노출.

```diff
  <!-- 수정/삭제: 본인 댓글일 때 -->
  <span th:if="${session.loginUser != null and session.loginUser.memberNo == reply.memberNo}"
        class="ml-auto flex gap-2.5 text-xs text-muted">
    ...
  </span>
+
+ <!-- 댓글 신고: 본인 댓글이 아닐 때 -->
+ <span th:if="${session.loginUser != null and session.loginUser.memberNo != reply.memberNo}"
+       class="ml-auto flex gap-2.5 text-xs text-muted">
+   <button type="button" class="hover:text-red-500"
+           th:onclick="|openReportModal('reply', [[${reply.replyNo}]])|">신고</button>
+ </span>
```

### 신고 모달

기존 `delete-form` hidden form 아래에 추가.

```diff
+ <div id="modal-report" class="fixed inset-0 z-50 hidden items-center justify-center bg-black/40">
+   <div class="w-full max-w-sm rounded-2xl bg-white p-6 shadow-xl">
+     <h2 class="mb-4 text-lg font-bold">신고하기</h2>
+     <p class="mb-3 text-sm text-muted">신고 사유를 선택해주세요.</p>
+     <select id="report-reason"
+             class="mb-5 w-full rounded-lg border border-line px-3 py-2 text-sm outline-none focus:border-brand">
+       <option value="">-- 사유 선택 --</option>
+       <option value="A">광고</option>
+       <option value="B">욕설</option>
+       <option value="C">음란</option>
+       <option value="D">도배</option>
+     </select>
+     <div class="flex justify-end gap-2">
+       <button type="button" onclick="closeReportModal()"
+               class="inline-flex h-9 items-center rounded-lg border border-line px-4 text-sm text-muted hover:bg-sunk">취소</button>
+       <button type="button" onclick="submitReport()"
+               class="inline-flex h-9 items-center rounded-lg bg-red-500 px-4 text-sm font-semibold text-white hover:bg-red-600">신고</button>
+     </div>
+   </div>
+ </div>
```

### 신고 JS

기존 `<script>` 블록 하단에 추가.

```diff
+ let reportTargetType = null;
+ let reportTargetNo   = null;
+
+ function openReportModal(targetType, targetNo) {
+   reportTargetType = targetType;
+   reportTargetNo   = targetNo;
+   document.getElementById('report-reason').value = '';
+   const modal = document.getElementById('modal-report');
+   modal.classList.remove('hidden');
+   modal.classList.add('flex');
+ }
+
+ function closeReportModal() {
+   const modal = document.getElementById('modal-report');
+   modal.classList.add('hidden');
+   modal.classList.remove('flex');
+ }
+
+ function submitReport() {
+   const reason = document.getElementById('report-reason').value;
+   if (!reason) {
+     openInfoModal('알림', '신고 사유를 선택해주세요.');
+     return;
+   }
+   fetch('/report/insert', {
+     method: 'POST',
+     headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
+     body: 'targetType=' + reportTargetType
+         + '&targetNo='  + reportTargetNo
+         + '&reason='    + encodeURIComponent(reason)
+   })
+   .then(r => r.json())
+   .then(data => {
+     closeReportModal();
+     openInfoModal(data.status === 'success' ? '신고 완료' : '알림', data.message);
+   });
+ }
```

---

## 흐름 요약

```
[신고 버튼 클릭]
      ↓
openReportModal(targetType, targetNo)   -- 모달 열기, 전역 변수에 대상 저장
      ↓
[사유 선택 후 신고 클릭]
      ↓
submitReport()
  → fetch POST /report/insert
      ↓
ReportAjaxController.insertReport()
  → 세션 loginUser 체크
  → AdminService.insertReport()
      → checkDuplicateReport() > 0 ? return -1
      → mapper.insertReport()
      ↓
JSON 응답 { status, message }
      ↓
closeReportModal() + openInfoModal() 로 결과 알림
```
