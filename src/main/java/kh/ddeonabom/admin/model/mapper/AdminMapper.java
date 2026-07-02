package kh.ddeonabom.admin.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.admin.model.vo.AdminPost;
import kh.ddeonabom.admin.model.vo.AdminReport;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.member.model.vo.Member;

@Mapper
public interface AdminMapper {

    int selectMemberCount();

    int selectTravelCount();

    int selectScheduleCount();

    int selectQlistCount();

    int selectReplyCount();

    int selectReportCount();

    ArrayList<Member> selectMembers(HashMap<String, Object> map);

	ArrayList<AdminNotice> selectNoticeList();

	int updateMemberStatus(HashMap<String, String> map);

	ArrayList<AdminPost> selectSchedulePosts(HashMap<String, Object> map);

	ArrayList<AdminPost> selectReviewPosts(HashMap<String, Object> map);

	ArrayList<AdminPost> selectQuestionPosts(HashMap<String, Object> map);

	int updateScheduleStatus(AdminPost post);

	int updateReviewStatus(AdminPost post);

	int updateQuestionStatus(AdminPost post);

	int selectScheduleCountList(HashMap<String, Object> map);

	int selectTravelCountList(HashMap<String, Object> map);

	int selectQlistCountList(HashMap<String, Object> map);

	int getNoticeCount(HashMap<String, Object> map);
	
	

	ArrayList<AdminNotice> selectNoticeList(HashMap<String, Object> map);

	int updateNoticeStatus(AdminNotice notice);

	int insertNotice(AdminNotice notice);

	AdminNotice selectNotice(int noticeNo);

	int updateNotice(AdminNotice notice);

	ArrayList<AdminNotice> selectTopNotice();

	int getReportCount(HashMap<String, Object> map);

	ArrayList<AdminReport> selectReportList(HashMap<String, Object> map);

	int updateReportStatus(AdminReport report);

	int updateTargetStatus(AdminReport report);

	int selectMemberCountList(HashMap<String, Object> map);

	int insertReport(AdminReport report);

	int checkDuplicateReport(AdminReport report);

	ArrayList<HashMap<String, Object>> selectScheduleActivity();

	ArrayList<HashMap<String, Object>> selectQlistActivity();

	ArrayList<HashMap<String, Object>> selectTravelActivity();

	int updateStatusByTarget(AdminReport report);

	int getTodayMemberCount();

//	관리자의 의해 정지당한 회원의 게시글과 댓글 모두 삭제
	int banMemberSchedule(HashMap<String, String> map);

	int banMemberReview(HashMap<String, String> map);

	int banMemberQlist(HashMap<String, String> map);

	int banMemberReply(HashMap<String, String> map);
	
	// 차단된 계정 관리자가 실수로 복구하려할때 막는 거

	int checkMemberStatusSchedule(AdminPost post);

	int checkMemberStatusReview(AdminPost post);

	int checkMemberStatusQuestion(AdminPost post);
	
	
}