package kh.ddeonabom.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import kh.ddeonabom.admin.model.mapper.AdminMapper;
import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.admin.model.vo.AdminPost;
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

	public ArrayList<Member> selectMembers(String id) {
		return mapper.selectMembers(id);
	}
	

	public ArrayList<AdminNotice> selectNoticeList() {
		return mapper.selectNoticeList();
	}

	public int updateMemberStatus(HashMap<String, String> map) {
		return mapper.updateMemberStatus(map);
	}

	public ArrayList<AdminPost> selectSchedulePosts() {
		return mapper.selectSchedulePosts();
	}

	public ArrayList<AdminPost> selectReviewPosts() {
		return mapper.selectReviewPosts();
	}

	public ArrayList<AdminPost> selectQuestionPosts() {
		return mapper.selectQuestionPosts();
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
	
}