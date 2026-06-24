-- ============================================================
-- 일정 공유 게시판 더미 데이터
-- SCHEDULE_MAIN 20건 + SCHEDULE_SUB + REPLY
-- 실행 후 COMMIT; 필수
-- ============================================================


-- ============================================================
-- 1. SCHEDULE_MAIN + SCHEDULE_SUB 더미 (20건)
-- 패턴: SMAIN INSERT → SSUB INSERT에서 SEQ_SMAIN.CURRVAL 사용
-- ============================================================

-- [1] 제주 감성 4박 5일
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '제주 감성 4박 5일', 'Y', SYSDATE-12, DATE '2026-07-10', DATE '2026-07-14', 'Y', 4);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-10', 1, SEQ_SMAIN.CURRVAL, 125422);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-11', 1, SEQ_SMAIN.CURRVAL, 125415);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-12', 1, SEQ_SMAIN.CURRVAL, 125405);

-- [2] 부산 2박 3일 해변 투어
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '부산 2박 3일 해변 투어', 'Y', SYSDATE-11, DATE '2026-07-18', DATE '2026-07-20', 'Y', 5);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-18', 1, SEQ_SMAIN.CURRVAL, 126481);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-19', 1, SEQ_SMAIN.CURRVAL, 126482);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-19', 2, SEQ_SMAIN.CURRVAL, 126483);

-- [3] 강릉 바다 당일치기
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '강릉 바다 당일치기', 'Y', SYSDATE-10, DATE '2026-07-05', DATE '2026-07-05', 'Y', 2);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-05', 1, SEQ_SMAIN.CURRVAL, 127376);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-05', 2, SEQ_SMAIN.CURRVAL, 127377);

-- [4] 서울 궁궐 & 북촌 하루 코스
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '서울 궁궐 & 북촌 하루 코스', 'Y', SYSDATE-9, DATE '2026-06-28', DATE '2026-06-28', 'Y', 4);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-06-28', 1, SEQ_SMAIN.CURRVAL, 125407);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-06-28', 2, SEQ_SMAIN.CURRVAL, 125410);

-- [5] 제주 올레길 완주 도전기
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '제주 올레길 완주 도전기', 'Y', SYSDATE-9, DATE '2026-08-01', DATE '2026-08-05', 'Y', 5);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-01', 1, SEQ_SMAIN.CURRVAL, 125408);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-02', 1, SEQ_SMAIN.CURRVAL, 125409);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-03', 1, SEQ_SMAIN.CURRVAL, 125422);

-- [6] 전주 한옥마을 맛집 탐방
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '전주 한옥마을 맛집 탐방', 'Y', SYSDATE-8, DATE '2026-07-12', DATE '2026-07-13', 'Y', 2);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-12', 1, SEQ_SMAIN.CURRVAL, 4008795);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-13', 1, SEQ_SMAIN.CURRVAL, 4008161);

-- [7] 경주 역사 문화 탐방
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '경주 역사 문화 탐방', 'Y', SYSDATE-8, DATE '2026-07-20', DATE '2026-07-22', 'Y', 4);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-20', 1, SEQ_SMAIN.CURRVAL, 126479);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-21', 1, SEQ_SMAIN.CURRVAL, 125452);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-22', 1, SEQ_SMAIN.CURRVAL, 125460);

-- [8] 속초 & 설악산 2박 3일
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '속초 & 설악산 2박 3일', 'Y', SYSDATE-7, DATE '2026-08-08', DATE '2026-08-10', 'Y', 5);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-08', 1, SEQ_SMAIN.CURRVAL, 2756168);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-09', 1, SEQ_SMAIN.CURRVAL, 3076525);

-- [9] 여수 밤바다 힐링 여행
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '여수 밤바다 힐링 여행', 'Y', SYSDATE-7, DATE '2026-07-25', DATE '2026-07-27', 'Y', 2);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-25', 1, SEQ_SMAIN.CURRVAL, 511801);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-26', 1, SEQ_SMAIN.CURRVAL, 2789102);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-27', 1, SEQ_SMAIN.CURRVAL, 125424);

-- [10] 인천 강화도 역사 기행
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '인천 강화도 역사 기행', 'Y', SYSDATE-6, DATE '2026-07-04', DATE '2026-07-05', 'Y', 4);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-04', 1, SEQ_SMAIN.CURRVAL, 125266);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-05', 1, SEQ_SMAIN.CURRVAL, 125407);

-- [11] 담양 죽녹원 & 소쇄원 당일
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '담양 죽녹원 & 소쇄원 당일', 'Y', SYSDATE-6, DATE '2026-07-13', DATE '2026-07-13', 'Y', 5);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-13', 1, SEQ_SMAIN.CURRVAL, 4008795);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-13', 2, SEQ_SMAIN.CURRVAL, 4008161);

-- [12] 통영 섬 투어 3박 4일
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '통영 섬 투어 3박 4일', 'Y', SYSDATE-5, DATE '2026-08-14', DATE '2026-08-17', 'Y', 2);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-14', 1, SEQ_SMAIN.CURRVAL, 126481);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-15', 1, SEQ_SMAIN.CURRVAL, 126482);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-16', 1, SEQ_SMAIN.CURRVAL, 126483);

-- [13] 춘천 호반 & 남이섬 코스
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '춘천 호반 & 남이섬 코스', 'Y', SYSDATE-5, DATE '2026-07-11', DATE '2026-07-12', 'Y', 4);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-11', 1, SEQ_SMAIN.CURRVAL, 127376);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-12', 1, SEQ_SMAIN.CURRVAL, 127377);

-- [14] 포항 호미곶 일출 & 과메기 탐방
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '포항 호미곶 일출 & 과메기 탐방', 'Y', SYSDATE-4, DATE '2026-08-01', DATE '2026-08-02', 'Y', 5);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-01', 1, SEQ_SMAIN.CURRVAL, 125452);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-02', 1, SEQ_SMAIN.CURRVAL, 126479);

-- [15] 서울 한강 & 홍대 감성 투어
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '서울 한강 & 홍대 감성 투어', 'Y', SYSDATE-4, DATE '2026-06-29', DATE '2026-06-29', 'Y', 2);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-06-29', 1, SEQ_SMAIN.CURRVAL, 125410);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-06-29', 2, SEQ_SMAIN.CURRVAL, 125266);

-- [16] 제주 서귀포 폭포 & 중문 해수욕장
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '제주 서귀포 폭포 & 중문 해수욕장', 'Y', SYSDATE-3, DATE '2026-08-10', DATE '2026-08-13', 'Y', 4);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-10', 1, SEQ_SMAIN.CURRVAL, 125405);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-11', 1, SEQ_SMAIN.CURRVAL, 125415);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-12', 1, SEQ_SMAIN.CURRVAL, 125422);

-- [17] 순천 갈대밭 & 낙안읍성
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '순천 갈대밭 & 낙안읍성', 'Y', SYSDATE-3, DATE '2026-07-26', DATE '2026-07-27', 'Y', 5);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-26', 1, SEQ_SMAIN.CURRVAL, 2789102);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-07-27', 1, SEQ_SMAIN.CURRVAL, 511801);

-- [18] 대전 & 공주 백제 문화권 탐방
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '대전 & 공주 백제 문화권 탐방', 'Y', SYSDATE-2, DATE '2026-08-03', DATE '2026-08-04', 'Y', 2);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-03', 1, SEQ_SMAIN.CURRVAL, 2929187);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-04', 1, SEQ_SMAIN.CURRVAL, 125407);

-- [19] 강원 계곡 & 펜션 피서 3박
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '강원 계곡 & 펜션 피서 3박', 'Y', SYSDATE-2, DATE '2026-08-20', DATE '2026-08-23', 'Y', 4);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-20', 1, SEQ_SMAIN.CURRVAL, 3076525);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-21', 1, SEQ_SMAIN.CURRVAL, 2756168);

-- [20] 부산 광안리 & 감천문화마을
INSERT INTO schedule_main (schedule_no, schedule_title, schedule_status, create_date, schedule_startdate, schedule_enddate, schedule_visibility, member_no)
VALUES (SEQ_SMAIN.NEXTVAL, '부산 광안리 & 감천문화마을', 'Y', SYSDATE-1, DATE '2026-08-28', DATE '2026-08-30', 'Y', 5);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-28', 1, SEQ_SMAIN.CURRVAL, 125460);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-29', 1, SEQ_SMAIN.CURRVAL, 125408);
INSERT INTO schedule_sub (schedule_sub_no, schedule_sub_date, schedule_sub_seq, schedule_no, content_id)
VALUES (SEQ_SSUB.NEXTVAL, DATE '2026-08-30', 1, SEQ_SMAIN.CURRVAL, 125409);


-- ============================================================
-- 2. REPLY 더미 데이터
-- post_board = 'S', 기존 7개(6,9,10,12,13,14,16) +
-- 위에서 추가한 20개 모두 대상
-- 새로 추가한 20개의 schedule_no는 시퀀스에 따라 자동 할당되므로
-- ※ 아래 INSERT는 실제 DB에서 신규 schedule_no 확인 후 번호 교체 필요
--   또는 서브쿼리로 최근 N개를 가져와 처리
-- 여기서는 편의상 기존 7개 댓글만 직접 삽입하고,
-- 신규 20개는 공통 서브쿼리 패턴으로 삽입
-- ============================================================

-- ── 기존 schedule_no = 6 (테스트) ──────────────────────────
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '오 코스 진짜 잘 짜셨네요! 저도 비슷하게 가봤는데 딱 맞는 동선이에요ㅎㅎ', SYSDATE-5, 'Y', 'S', 6, 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 일정 가져가도 될까요?? 다음 달 여행에 참고하고 싶어요!', SYSDATE-4, 'Y', 'S', 6, 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '와 진짜 꼼꼼하게 짜셨다... 존경스럽습니다 ㅠㅠ', SYSDATE-3, 'Y', 'S', 6, 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '동선이 좀 왔다갔다 하는 것 같긴 한데 나름 이유가 있겠죠 뭐', SYSDATE-2, 'Y', 'S', 6, 5);

-- ── 기존 schedule_no = 9 (제목 없는 일정) ────────────────────
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '숨겨진 명소까지 다 넣으셨네요!! 정보력 대박이에요', SYSDATE-6, 'Y', 'S', 9, 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 코스로 저도 다녀왔는데 진짜 최고였어요! 완벽한 일정 추천드려요', SYSDATE-5, 'Y', 'S', 9, 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '날짜별로 동선 정리가 진짜 깔끔하네요. 따라가고 싶다', SYSDATE-3, 'Y', 'S', 9, 5);

-- ── 기존 schedule_no = 10 (제목 없는 일정) ───────────────────
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '여행 계획 세우는 데 진짜 참고 많이 됐어요. 좋은 일정 공유 감사드려요!', SYSDATE-4, 'Y', 'S', 10, 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 시기에 거기 사람 엄청 많을 텐데 대기 시간 감안하면 좀 빡센 것 같아요', SYSDATE-3, 'Y', 'S', 10, 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '맛집이랑 관광지 비율이 딱 적당해요! 알차게 짠 티가 나요ㅎㅎ', SYSDATE-2, 'Y', 'S', 10, 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이런 일정 혼자 다 짜셨어요? 진짜 대단하세요', SYSDATE-1, 'Y', 'S', 10, 5);

-- ── 기존 schedule_no = 12 (제목 없는 일정) ───────────────────
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '동선이 너무 효율적이에요~ 어떻게 이렇게 잘 짜셨어요?', SYSDATE-5, 'Y', 'S', 12, 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '완전 힐링될 것 같은 코스네요~ 다음 여행 때 꼭 참고할게요!', SYSDATE-4, 'Y', 'S', 12, 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '가족 여행 코스로도 딱 좋겠어요! 아이들이랑 가기 정말 좋을 것 같아요', SYSDATE-2, 'Y', 'S', 12, 2);

-- ── 기존 schedule_no = 13 (oooo) ─────────────────────────────
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 일정 진짜 보석이에요ㅠㅠ 찜해두고 나중에 꼭 따라가볼게요', SYSDATE-3, 'Y', 'S', 13, 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '하루에 이 장소를 다 돌기엔 솔직히 좀 빡세지 않나요??', SYSDATE-2, 'Y', 'S', 13, 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '코스 구성이 탁월해요. 저도 이렇게 짜고 싶은데 항상 실패해서ㅋㅋ', SYSDATE-1, 'Y', 'S', 13, 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '처음 여행 계획 짜는데 이런 일정 보니까 많은 도움이 됐어요!', SYSDATE, 'Y', 'S', 13, 4);

-- ── 기존 schedule_no = 14 (제목 없는 일정) ───────────────────
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '공들여 짠 게 느껴지는 일정이에요. 진짜 부럽다', SYSDATE-2, 'Y', 'S', 14, 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '좋은 일정 공유해주셔서 감사해요!! 덕분에 여행 준비가 수월해졌어요', SYSDATE-1, 'Y', 'S', 14, 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '주차 공간이 없는 곳인데 대중교통 정보도 같이 넣어줬으면 더 좋았을 것 같아요', SYSDATE, 'Y', 'S', 14, 4);

-- ── 기존 schedule_no = 16 (테스트용) ─────────────────────────
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '와 일정이 너무 알찬데요! 빠진 게 없어 보여요ㅎㅎ', SYSDATE-1, 'Y', 'S', 16, 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 코스로 여행 계획 잡았어요!! 감사합니다ㅠㅠ', SYSDATE, 'Y', 'S', 16, 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이렇게 잘 짠 일정을 무료로 공유해주시다니 감사해요!', SYSDATE, 'Y', 'S', 16, 2);


-- ============================================================
-- 신규 추가 20개 일정 댓글
-- 아래 서브쿼리는 schedule_main에서 신규 삽입된 일정들을 조회해
-- post_no로 사용합니다.
-- 실행 전 반드시 schedule_no 실제 값 확인 후 교체하세요.
-- (예: SELECT schedule_no, schedule_title FROM schedule_main ORDER BY schedule_no DESC FETCH FIRST 20 ROWS ONLY;)
-- 아래는 schedule_title 기준 서브쿼리 방식으로 작성 (제목이 고유하다는 가정)
-- ============================================================

-- [1] 제주 감성 4박 5일
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '제주 4박 5일 코스 진짜 완벽해요! 저도 이 일정으로 가고 싶어요ㅠㅠ', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 감성 4박 5일'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '숙소 위치까지 고려한 동선이 정말 효율적이네요. 짱이에요!', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 감성 4박 5일'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '제주는 렌트카 없이 이동하기 힘든데 그 부분 대비는 하셨나요?', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 감성 4박 5일'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이런 일정 공유해주셔서 정말 감사합니다! 찜 눌렀어요ㅎㅎ', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 감성 4박 5일'), 4);

-- [2] 부산 2박 3일 해변 투어
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '부산 2박 3일이면 딱 적당한 것 같아요! 저도 비슷하게 다녀왔는데 진짜 좋았어요', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '부산 2박 3일 해변 투어'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '해변 코스가 너무 알차네요!! 해산물 맛집도 넣어주시면 더 완벽할 것 같아요', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '부산 2박 3일 해변 투어'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '여름 성수기엔 숙소 예약 미리미리 해두세요! 금방 차거든요', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '부산 2박 3일 해변 투어'), 5);

-- [3] 강릉 바다 당일치기
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '강릉 당일치기 진짜 알차게 짜셨어요! 커피거리도 들르셨으면 좋겠어요ㅎㅎ', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '강릉 바다 당일치기'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '당일치기면 새벽 출발해야 하는데 체력 관리 잘 하세요!', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '강릉 바다 당일치기'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '강릉 바다 진짜 너무 좋죠ㅠㅠ 이 코스 따라가고 싶어요!', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '강릉 바다 당일치기'), 2);

-- [4] 서울 궁궐 & 북촌 하루 코스
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '궁궐이랑 북촌 묶은 코스 진짜 센스 있어요! 외국 친구 데려가기도 딱 좋겠네요', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '서울 궁궐 & 북촌 하루 코스'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '하루에 다 보려면 빡빡하지 않나요? 중간에 쉬는 시간도 필요할 것 같아요', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '서울 궁궐 & 북촌 하루 코스'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '서울 살면서도 이런 코스로 다닌 적 없었는데 이번에 따라가봐야겠어요ㅋㅋ', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '서울 궁궐 & 북촌 하루 코스'), 4);

-- [5] 제주 올레길 완주 도전기
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '올레길 완주 도전이요?! 대단하세요 진짜!! 체력이 어마어마하시겠어요', SYSDATE-4, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 올레길 완주 도전기'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '올레길 코스별 난이도 정보도 같이 올려주시면 너무 좋을 것 같아요!', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 올레길 완주 도전기'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 일정 보고 저도 올레길 도전해보고 싶어졌어요! 자세한 후기도 부탁드려요ㅎ', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 올레길 완주 도전기'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '여름에 올레길은 너무 더울 수 있어요ㅠ 선크림이랑 모자 필수예요!', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 올레길 완주 도전기'), 2);

-- [6] 전주 한옥마을 맛집 탐방
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '전주 한옥마을 맛집 코스 너무 알차다!! 비빔밥은 당연히 들어가 있겠죠?ㅋㅋ', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '전주 한옥마을 맛집 탐방'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '한복 입고 한옥마을 걷는 거 강추예요! 이 일정에 꼭 넣어보세요', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '전주 한옥마을 맛집 탐방'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '맛집 선정이 완전 찰떡이에요. 다 가보고 싶은 곳들만 골랐네요!', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '전주 한옥마을 맛집 탐방'), 2);

-- [7] 경주 역사 문화 탐방
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '경주는 역사 덕후에게 진짜 천국이죠ㅠㅠ 일정 알차게 짜셨네요!', SYSDATE-5, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '경주 역사 문화 탐방'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '야경 코스도 넣으면 더 완벽할 것 같아요! 첨성대 야경이 진짜 예뻐요', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '경주 역사 문화 탐방'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 일정으로 수학여행 재현 해봐야겠다ㅋㅋ 어릴 때 못 즐긴 경주를 제대로!', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '경주 역사 문화 탐방'), 4);

-- [8] 속초 & 설악산 2박 3일
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '설악산 포함 일정이 진짜 부럽다... 저도 체력 키워서 꼭 도전해볼게요!', SYSDATE-4, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '속초 & 설악산 2박 3일'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '속초 닭강정이랑 아바이순대는 필수예요ㅎㅎ 일정에 포함하셨으면 좋겠어요', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '속초 & 설악산 2박 3일'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '산행 난이도 어느 정도로 예상하고 계세요? 초보자도 가능한 코스인지 궁금해요', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '속초 & 설악산 2박 3일'), 4);

-- [9] 여수 밤바다 힐링 여행
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '여수 밤바다 노래 나오는 그 여수!! 진짜 가고 싶어지는 일정이에요ㅠㅠ', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '여수 밤바다 힐링 여행'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '낭만포차 꼭 가보세요!! 여수 가면 절대 빠지면 안 되는 곳이에요', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '여수 밤바다 힐링 여행'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '힐링 여행 코스 구성이 너무 완벽해요. 빡빡하지 않고 여유 있어 보여서 좋아요', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '여수 밤바다 힐링 여행'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 일정 가져가도 될까요?! 커플 여행으로 딱일 것 같아요', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '여수 밤바다 힐링 여행'), 4);

-- [10] 인천 강화도 역사 기행
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '강화도 역사 기행 너무 좋은 주제예요! 가깝고도 몰랐던 역사가 많더라고요', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '인천 강화도 역사 기행'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '강화 순무로 만든 김치도 꼭 사오세요ㅎㅎ 특산품 챙기는 재미도 있어요', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '인천 강화도 역사 기행'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '당일치기로도 충분한가요? 볼게 많아서 1박도 고려해봄직 할 것 같아요', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '인천 강화도 역사 기행'), 4);

-- [11] 담양 죽녹원 & 소쇄원 당일
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '죽녹원 사계절 다 예쁜데 여름 대나무숲은 진짜 시원하고 최고예요!', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '담양 죽녹원 & 소쇄원 당일'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '떡갈비도 꼭 드세요!! 담양 왔으면 떡갈비는 필수입니다ㅋㅋ', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '담양 죽녹원 & 소쇄원 당일'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '당일치기 코스가 딱 적당한 것 같아요. 너무 빡빡하지 않고 여유 있어 보여요', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '담양 죽녹원 & 소쇄원 당일'), 4);

-- [12] 통영 섬 투어 3박 4일
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '통영 3박 4일이면 섬 투어 제대로 할 수 있겠네요!! 너무 부러워요', SYSDATE-4, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '통영 섬 투어 3박 4일'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '굴국밥이랑 꿀빵은 꼭 드세요!! 통영 여행의 핵심이에요ㅎㅎ', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '통영 섬 투어 3박 4일'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '한려수도 케이블카 타셨나요? 통영 왔으면 필수인데!! 일정에 있으면 좋겠어요', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '통영 섬 투어 3박 4일'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '섬 투어 예약은 미리 해두세요! 성수기엔 배편 구하기 힘들 수 있어요', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '통영 섬 투어 3박 4일'), 2);

-- [13] 춘천 호반 & 남이섬 코스
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '남이섬은 사계절 다 예쁜데 여름 초록초록할 때 진짜 최고예요!', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '춘천 호반 & 남이섬 코스'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '닭갈비 집 예약 미리 하세요!! 줄 엄청 서요 성수기엔', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '춘천 호반 & 남이섬 코스'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 코스 따라가려고 연차 신청했어요!! 좋은 일정 공유해주셔서 감사해요', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '춘천 호반 & 남이섬 코스'), 2);

-- [14] 포항 호미곶 일출 & 과메기 탐방
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '호미곶 일출 진짜 장관이죠ㅠㅠ 새벽에 일어나기 힘들지만 그 값어치는 충분해요', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '포항 호미곶 일출 & 과메기 탐방'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '과메기 처음 먹어봤는데 생각보다 맛있더라고요! 기대해도 좋아요ㅎㅎ', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '포항 호미곶 일출 & 과메기 탐방'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '일출 보러 전날 포항 숙소에서 자야 할 것 같은데 숙소 정보도 공유해주시면 좋겠어요!', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '포항 호미곶 일출 & 과메기 탐방'), 4);

-- [15] 서울 한강 & 홍대 감성 투어
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '서울 살면서 이렇게 제대로 즐긴 적이 없었는데 이 일정 보고 자극받았어요!', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '서울 한강 & 홍대 감성 투어'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '한강 피크닉 코스가 너무 감성적이에요ㅠㅠ 친구들이랑 꼭 가봐야겠다!', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '서울 한강 & 홍대 감성 투어'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '홍대 주말에 사람 엄청 많으니 평일 가시는 거 추천해요!', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '서울 한강 & 홍대 감성 투어'), 2);

-- [16] 제주 서귀포 폭포 & 중문 해수욕장
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '정방폭포 진짜 웅장하죠ㅠㅠ 서귀포 코스 너무 잘 짜셨어요!!', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 서귀포 폭포 & 중문 해수욕장'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '중문 해수욕장 파도 세기 미리 체크하고 가세요! 여름엔 파도가 좀 강하거든요', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 서귀포 폭포 & 중문 해수욕장'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 일정 진짜 보석이에요. 찜하고 나중에 꼭 따라갈게요ㅠㅠ', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '제주 서귀포 폭포 & 중문 해수욕장'), 4);

-- [17] 순천 갈대밭 & 낙안읍성
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '순천만 갈대밭 일몰 진짜 환상적이에요ㅠㅠ 꼭 일몰 시간 맞춰 가세요!', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '순천 갈대밭 & 낙안읍성'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '낙안읍성 초가집 체험도 있던데 이 일정에 넣으면 더 풍성할 것 같아요!', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '순천 갈대밭 & 낙안읍성'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '알차고 좋은 일정이에요. 순천에 이렇게 볼 게 많은지 몰랐네요!', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '순천 갈대밭 & 낙안읍성'), 5);

-- [18] 대전 & 공주 백제 문화권 탐방
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '백제 문화권 여행 진짜 좋은 주제예요! 역사 공부도 되고 여행도 되고ㅎㅎ', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '대전 & 공주 백제 문화권 탐방'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '공주 무령왕릉 꼭 가보세요! 생각보다 규모가 웅장해서 놀랐어요', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '대전 & 공주 백제 문화권 탐방'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '대전 성심당 꼭 들르세요!! 공주 가는 길에 들르기도 좋아요ㅎㅎ', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '대전 & 공주 백제 문화권 탐방'), 4);

-- [19] 강원 계곡 & 펜션 피서 3박
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '여름 계곡 피서 진짜 최고의 선택이에요!! 올 여름 저도 이렇게 해야겠다ㅠㅠ', SYSDATE-3, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '강원 계곡 & 펜션 피서 3박'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '펜션 예약 얼마나 미리 하셨어요? 여름에는 진짜 빨리 마감되더라고요', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '강원 계곡 & 펜션 피서 3박'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '계곡에서 고기 구워먹는 거 상상만 해도 행복하네요ㅎㅎ 좋은 일정 감사해요!', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '강원 계곡 & 펜션 피서 3박'), 5);

-- [20] 부산 광안리 & 감천문화마을
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '감천문화마을 진짜 예쁘죠ㅠㅠ 사진 스팟이 너무 많아서 시간이 부족할 거예요!', SYSDATE-2, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '부산 광안리 & 감천문화마을'), 4);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '광안리 불꽃축제 기간에 가시면 대박인데 그 기간 체크해보세요!', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '부산 광안리 & 감천문화마을'), 5);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '이 두 코스 조합이 진짜 신의 한 수예요. 동선도 완벽하고 내용도 알차네요!', SYSDATE-1, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '부산 광안리 & 감천문화마을'), 2);
INSERT INTO reply (reply_no, content, create_date, status, post_board, post_no, member_no)
VALUES (SEQ_RNO.NEXTVAL, '감천마을 언덕길 생각보다 경사 급해요ㅠ 운동화 필수예요!', SYSDATE, 'Y', 'S', (SELECT schedule_no FROM schedule_main WHERE schedule_title = '부산 광안리 & 감천문화마을'), 4);


COMMIT;
