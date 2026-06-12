package kh.ddeonabom.admin.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.admin.model.vo.AdminPost;
import kh.ddeonabom.member.model.vo.Member;

@Mapper
public interface AdminMapper {

    int selectMemberCount();

    int selectTravelCount();

    int selectScheduleCount();

    int selectQlistCount();

    int selectReplyCount();

    int selectReportCount();

	ArrayList<Member> selectMembers(String id);

	ArrayList<AdminNotice> selectNoticeList();

	int updateMemberStatus(HashMap<String, String> map);

	ArrayList<AdminPost> selectSchedulePosts();

	ArrayList<AdminPost> selectReviewPosts();

	ArrayList<AdminPost> selectQuestionPosts();

	int updateScheduleStatus(AdminPost post);

	int updateReviewStatus(AdminPost post);

	int updateQuestionStatus(AdminPost post);
}