package kh.ddeonabom.member.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kh.ddeonabom.member.model.mappers.MemberMapper;
import kh.ddeonabom.member.model.vo.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService { //  시큐리티 전용 로그인 인터페이스 구현 추가!
	
	private final MemberMapper mapper;
	private final BCryptPasswordEncoder passwordEncoder; //  암호화 부품 주입 (SecurityConfig에 등록했던 빈)

	// [시큐리티 필수 구현] 로그인 버튼을 누르면 시큐리티가 이 메서드를 자동으로 가동합니다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 폼에서 입력한 username(아이디)을 VO 객체에 세팅해서 기존 login 쿼리 재활용
		Member m = new Member();
		m.setId(username); //
		
		// DB에서 해당 아이디를 가진 유저를 꺼내
		Member loginUser = mapper.login(m);
		
		// 유저가 없다면 에러를 던져 로그인을 차단
		if (loginUser == null) {
			throw new UsernameNotFoundException("존재하지 않는 회원입니다: " + username);
		}
		
		// 꺼내온 유저 정보(아이디, 암호화된 비밀번호)를 시큐리티 규격 객체에 담아서 리턴합니다.
		return User.builder()
				.username(loginUser.getId()) 
				.password(loginUser.getPwd())
				.roles("USER") // 임시 권한 부여
				.build();
	}
	
	public int insertMember(Member m) {
		
		
		return mapper.insertMember(m);
	}

	
	public Member login(Member m) {
		return mapper.login(m);
	}

	public boolean existsByEmail(String email) {
		 return mapper.existsByEmail(email) > 0;
	}


	public int updateMemberWithPassword(Member m) {
		return mapper.updateMemberWithPassword(m);
	}

	public int updateMemberWithoutPassword(Member m) {
		return mapper.updateMemberWithoutPassword(m);
	}


	public Member selectOneMember(String id) {
		return mapper.selectOneMember(id);
	}

	public String findIdByEmail(String email) {
		return mapper.findIdByEmail(email);
	}

	public int updatePasswordOnly(Member m) {
		
		return mapper.updatePasswordOnly(m);
	}

	public int withdrawMember(String id) {
		return mapper.withdrawMember(id);
	}

	public boolean existsBynickname(String nickname) {
		return mapper.existsBynickname(nickname) > 0;
	}
}