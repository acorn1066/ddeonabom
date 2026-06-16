package kh.ddeonabom.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import kh.ddeonabom.admin.model.mapper.AdminMapper;
import kh.ddeonabom.admin.model.vo.AdminNotice;
import kh.ddeonabom.admin.model.vo.AdminPost;
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

    public ArrayList<Member> selectMembers(
            String id,
            PageInfo pi) {

        HashMap<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("startRow",
                (pi.getCurrentPage() - 1) * pi.getBoardLimit());

        map.put("listLimit",
                pi.getBoardLimit());

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

	public int getPostCount(String category) {

	    switch(category) {

	        case "schedule":
	            return mapper.selectScheduleCountList();

	        case "review":
	            return mapper.selectTravelCountList();

	        case "question":
	            return mapper.selectQlistCountList();

	        default:
	            return 0;
	    }
	}

	public ArrayList<AdminPost> selectPostList(String category, PageInfo pi) {
		
	    HashMap<String, Object> map = new HashMap<>();

	    map.put("startRow",(pi.getCurrentPage() - 1) * pi.getBoardLimit());

	    map.put("listLimit", pi.getBoardLimit());

	    switch(category) {

	        case "schedule":
	            return mapper.selectSchedulePosts(map);

	        case "review":
	            return mapper.selectReviewPosts(map);

	        case "question":
	            return mapper.selectQuestionPosts(map);

	        default:
	            return new ArrayList<>();
	    }
	}

	public int selectMemberCountList(String id) {
		return mapper.selectMemberCountList(id);
	}
	
}