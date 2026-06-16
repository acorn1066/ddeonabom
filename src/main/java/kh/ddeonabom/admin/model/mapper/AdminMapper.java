package kh.ddeonabom.admin.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.admin.model.vo.AdminPost;
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

	int selectScheduleCountList();

	int selectTravelCountList();

	int selectQlistCountList();

	int selectMemberCountList(String id);
	
	
}