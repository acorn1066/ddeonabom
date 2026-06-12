package kh.ddeonabom.admin.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kh.ddeonabom.admin.model.vo.AdminNotice;
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
}