package kh.ddeonabom.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import kh.ddeonabom.admin.model.mapper.AdminMapper;
import kh.ddeonabom.admin.model.vo.AdminNotice;
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

	
}