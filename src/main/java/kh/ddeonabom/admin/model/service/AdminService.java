package kh.ddeonabom.admin.model.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import kh.ddeonabom.admin.model.mapper.AdminMapper;
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

	public ArrayList<Member> selectMemberList() {
		return mapper.selectMemberList();
	}
	
	public int updateMemberStatus(int memberNo, String status) {
	    return mapper.updateMemberStatus(memberNo, status);
	}

	
}