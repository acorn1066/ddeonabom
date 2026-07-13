<div align="center">

# 떠나봄 (Ddeonabom)

**국내 여행 일정 계획 · 공유 웹 서비스**

KH 정보교육원 파이널 프로젝트 (6인 팀)

</div>

---

## 프로젝트 소개

**떠나봄**은 사용자가 날짜별로 여행 장소를 담아 일정을 짜고, 카카오맵 기반으로 최적 경로를 확인하며, 완성한 일정을 다른 사람과 공유·후기로 남길 수 있는 국내 여행 플래너 서비스입니다.

- **개발 기간** : KH 정보교육원 파이널 프로젝트
- **인원** : 6인 (팀장 1, 팀원 5)
- **배포** : AWS EC2(Ubuntu) + Nginx + systemd

---

## Screenshots

| 메인 | 관광지 탐색 |
|---|---|
| ![메인](./screenshots/01-main.png) | ![관광지 탐색](./screenshots/02-landmark.png) |

| 일정 플래너 | 일정 공유 |
|---|---|
| ![일정 플래너](./screenshots/03-schedule.png) | ![일정 공유](./screenshots/04-share.png) |

| 여행 후기 |
|---|
| ![여행 후기](./screenshots/05-review.png) |

---

## Collaborators

| 이름 | 역할 |
|---|---|
| 박진희 | 프로젝트 팀장 · 일정 플래너 담당 |
| 김지윤 | 회원 담당 |
| 김은호 | 질문게시판 · 일정 공유 게시판 담당 |
| 임동혁 | 여행 후기 게시판 담당 |
| 유승우 | 관리자 페이지 담당 |
| 김성한 | 관광지 탐색 담당 |

---

## Stacks

### Environment
![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)
![AWS EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)
![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)

### Backend
![Java](https://img.shields.io/badge/Java%2021-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-DC382D?style=for-the-badge)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)

### Frontend (Admin)
![React](https://img.shields.io/badge/React%2019-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![TailwindCSS](https://img.shields.io/badge/TailwindCSS-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white)
![Chart.js](https://img.shields.io/badge/Chart.js-FF6384?style=for-the-badge&logo=chartdotjs&logoColor=white)

### Infra & External API
![AWS S3](https://img.shields.io/badge/AWS%20S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)
![Kakao](https://img.shields.io/badge/Kakao%20Map%2FMobility-FFCD00?style=for-the-badge&logo=kakao&logoColor=black)
![TourAPI](https://img.shields.io/badge/한국관광공사%20TourAPI-1E88E5?style=for-the-badge)

### Tools
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![STS](https://img.shields.io/badge/Spring%20Tool%20Suite-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![StarUML](https://img.shields.io/badge/StarUML-1572B6?style=for-the-badge)

---

## 주요 기능

| 도메인 | 설명 |
|---|---|
| 회원 | 회원가입(이메일 인증), 로그인, 아이디/비밀번호 찾기, 마이페이지, 회원 탈퇴 |
| 일정 플래너 | 여행 일정 생성/편집, Kakao Map 기반 경로 및 장소 관리 |
| 일정 공유 | 완성한 일정을 게시글 형태로 공유, URL 우회 접속 차단 |
| 여행 후기 | 이미지 첨부 후기 작성/수정, 댓글 |
| 질문 게시판 | 여행 관련 Q&A |
| 관광지 탐색 | TourAPI 연동 데이터를 지도에서 탐색 |
| 신고 | 게시글/댓글 신고 |
| 관리자 페이지 | 회원·게시글 관리, 공지 작성, 신고 처리, TourAPI 동기화, 통계 대시보드 (React SPA, `/admin`) |

---

## 프로젝트 구조

```
ddeonabom/
├── src/main/java/kh/ddeonabom/
│   ├── main/         # 메인 페이지
│   ├── member/        # 회원가입 · 로그인 · 이메일 인증 · 마이페이지
│   ├── schedule/       # 여행 일정 플래너 (경로/장소 관리)
│   ├── share/         # 일정 공유 게시판
│   ├── review/        # 여행 후기 게시판 (이미지 첨부)
│   ├── qList/         # 질문 게시판
│   ├── reply/         # 댓글 (공용)
│   ├── report/        # 게시글 신고
│   ├── landmark/       # 관광지 탐색 (지도 연동)
│   ├── admin/         # 관리자 기능 (공지/신고/통계/TourAPI 동기화)
│   └── common/        # 공통 설정 · 인터셉터 · 페이징 · 예외 처리
├── src/main/resources/
│   ├── mappers/        # MyBatis XML 매퍼
│   ├── templates/       # Thymeleaf 뷰
│   └── application.properties
├── src/main/admin/       # React 관리자 페이지 (Vite 프로젝트, 별도 빌드)
└── deploy.bat          # 로컬 → EC2 배포 스크립트
```

각 도메인 패키지는 `controller / service / model(vo, mapper)` 계층으로 구성되어 있습니다.

