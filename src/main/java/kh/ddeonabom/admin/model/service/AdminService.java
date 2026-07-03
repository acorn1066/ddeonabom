package kh.ddeonabom.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public int updateMemberStatus(HashMap<String, String> map) {
		int result = mapper.updateMemberStatus(map);
		
		if(result > 0 && "B".equals(map.get("val"))) {
			mapper.banMemberSchedule(map);
			mapper.banMemberReview(map);
			mapper.banMemberQlist(map);
			mapper.banMemberReply(map);
		}
		
		return result;
		
		
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

	public int getReportCount(String targetType, String status, String keyword) {
		   HashMap<String, Object> map = new HashMap<>();
		    map.put("targetType", targetType);
		    map.put("status", status);
		    map.put("keyword", keyword);
		    return mapper.getReportCount(map);
		}

	public ArrayList<AdminReport> selectReportList(String targetType, String status, String keyword, PageInfo pi) {
		 HashMap<String, Object> map = new HashMap<>();
		    map.put("targetType", targetType);
		    map.put("status", status);
		    map.put("keyword", keyword);
		    map.put("pi", pi);
		    return mapper.selectReportList(map);
		}

	public int updateReportStatus(AdminReport report) {
		return mapper.updateReportStatus(report);
	}

	public int processReport(AdminReport report) {
	    report.setReportStatus("Y");
	    mapper.updateStatusByTarget(report);
	    mapper.updateTargetStatus(report);
	    
	    int writerMemberNo = mapper.selectWriterByTarget(report);
//	    System.out.println( writerMemberNo);
	    
	 // 같은 게시글이 올라와도 하나의 게시글로 신고처리 갯수를 세기
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("memberNo", writerMemberNo);
	    
	    int reportCount = mapper.memberReportCount(map);
//	    System.out.println(reportCount);
	    

	    // 3건 이상이면 자동 정지하도록
	    if (reportCount >= 3) {
//	    	 System.out.println("회원정지됨");
	        mapper.blockMember(writerMemberNo);
	        
	        // id 조회 후 기존 ban 쿼리 재활용
	        String writerId = mapper.selectIdByMemberNo(writerMemberNo);
	        HashMap<String, String> banMap = new HashMap<>();
	        banMap.put("id", writerId);
	        
	        mapper.banMemberReview(banMap);
	        mapper.banMemberQlist(banMap);
	        mapper.banMemberReply(banMap);
	        mapper.banMemberSchedule(banMap);
	    }

	    return 1;
	}

	// 신고 등록: 중복 -1, 대상 없음 -2, 본인 글/댓글 -3, 관리자 글/댓글 -4, 성공 시 등록 결과(1)
	public int insertReport(AdminReport report) {
	    if (mapper.checkDuplicateReport(report) > 0) return -1;

	    // 신고 대상 작성자 조회 -> 본인 글/댓글이거나 관리자 글/댓글이면 신고 거부
	    Map<String, Object> owner = mapper.getReportTargetOwner(report);
	    if (owner == null || owner.get("MEMBERNO") == null) return -2;   // 대상이 존재하지 않음

	    int ownerNo = ((Number) owner.get("MEMBERNO")).intValue();
	    if (ownerNo == report.getMemberNo()) return -3;                  // 본인 글/댓글
	    if ("Y".equals(owner.get("ISADMIN"))) return -4;                 // 관리자 글/댓글

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

	public int updateStatusByTarget(AdminReport report) {
	    return mapper.updateStatusByTarget(report);
	}

	public int updateTargetStatus(AdminReport report) {
		 return mapper.updateTargetStatus(report);
		
	}

	public int getTodayMemberCount() {
		return mapper.getTodayMemberCount();
	}

	public int checkMemberStatusSchedule(AdminPost post) {
		return mapper.checkMemberStatusSchedule(post) ;
	}

	public int checkMemberStatusReview(AdminPost post) {
		return mapper.checkMemberStatusReview(post);
	}

	public int checkMemberStatusQuestion(AdminPost post) {
		return mapper.checkMemberStatusQuestion(post);
	}



	
}
	