package kh.ddeonabom.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import kh.ddeonabom.admin.model.mapper.AdminMapper;
import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.admin.model.vo.AdminPost;
import kh.ddeonabom.admin.model.vo.AdminReport;
import kh.ddeonabom.common.paging.PageInfo;
import kh.ddeonabom.member.model.vo.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper mapper;

    public int selectMemberCount() {
        return mapper.selectMemberCount();
    }

    public int selectTravelCount() {
        return mapper.selectTravelCount();
    }

    public int selectScheduleCount() {
        return mapper.selectScheduleCount();
    }

    public int selectQlistCount() {
        return mapper.selectQlistCount();
    }

    public int selectReplyCount() {
        return mapper.selectReplyCount();
    }

    public int selectReportCount() {
        return mapper.selectReportCount();
    }

    public ArrayList<Member> selectMembers(HashMap<String, Object> map) {
        return mapper.selectMembers(map);
    }
	

	public ArrayList<AdminNotice> selectNoticeList() {
		return mapper.selectNoticeList();
	}

	public int updateMemberStatus(HashMap<String, String> map) {
		return mapper.updateMemberStatus(map);
	}

	public int updatePostStatus(AdminPost post) {
		 switch (post.getBoardType()) {
	        case "공유":
	            return mapper.updateScheduleStatus(post);
	        case "후기":
	            return mapper.updateReviewStatus(post);
	        case "질문":
	            return mapper.updateQuestionStatus(post);
	        default:
	            return 0;
	    }
	}

	public int getPostCount(String category, HashMap<String, Object> map) {
	    switch(category) {
	        case "schedule": return mapper.selectScheduleCountList(map);
	        case "review":   return mapper.selectTravelCountList(map);
	        case "question": return mapper.selectQlistCountList(map);
	        default: return 0;
	    }
	}

	public ArrayList<AdminPost> selectPostList(String category, HashMap<String, Object> map) {
	    switch(category) {
	        case "schedule": return mapper.selectSchedulePosts(map);
	        case "review":   return mapper.selectReviewPosts(map);
	        case "question": return mapper.selectQuestionPosts(map);
	        default: return new ArrayList<>();
	    }
	}

	public int selectMemberCountList(HashMap<String, Object> map) {
	    return mapper.selectMemberCountList(map);
	}

	public int getNoticeCount(HashMap<String, Object> map) {
		return mapper.getNoticeCount(map);
	}

	public ArrayList<AdminNotice> selectNoticeList(HashMap<String, Object> map) {
	    return mapper.selectNoticeList(map);
	}

	public int updateNoticeStatus(AdminNotice notice) {
		return mapper.updateNoticeStatus(notice);
	}

	public int insertNotice(AdminNotice notice) {
		return mapper.insertNotice(notice);
	}

	public AdminNotice selectNotice(int noticeNo) {
		return mapper.selectNotice(noticeNo);
	}

	public int updateNotice(AdminNotice notice) {
		return mapper.updateNotice(notice);
	}

	public ArrayList<AdminNotice> selectTopNotice() {
		return mapper.selectTopNotice();
	}

	public int getReportCount(String targetType, String status) {
		   HashMap<String, Object> map = new HashMap<>();
		    map.put("targetType", targetType);
		    map.put("status", status);
		    return mapper.getReportCount(map);
		}

	public ArrayList<AdminReport> selectReportList(String targetType, String status, PageInfo pi) {
		 HashMap<String, Object> map = new HashMap<>();
		    map.put("targetType", targetType);
		    map.put("status", status);
		    map.put("pi", pi);
		    return mapper.selectReportList(map);
		}

	public int updateReportStatus(AdminReport report) {
		return mapper.updateReportStatus(report);
	}

	public int processReport(AdminReport report) {
		report.setReportStatus("Y");
	    mapper.updateReportStatus(report);
	    return mapper.updateTargetStatus(report);

	}

	// 중복 신고 시 -1 반환
	public int insertReport(AdminReport report) {
		if (mapper.checkDuplicateReport(report) > 0) return -1;
		return mapper.insertReport(report);
	}

	public ArrayList<HashMap<String, Object>> selectScheduleActivity() {
	    return mapper.selectScheduleActivity();
	}

	public ArrayList<HashMap<String, Object>> selectQlistActivity() {
	    return mapper.selectQlistActivity();
	}

	public ArrayList<HashMap<String, Object>> selectTravelActivity() {
	    return mapper.selectTravelActivity();
	}


	
}
	