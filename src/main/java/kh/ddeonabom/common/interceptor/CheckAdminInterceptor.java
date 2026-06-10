package kh.ddeonabom.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.ddeonabom.member.model.vo.Member;

public class CheckAdminInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Member loginUser = (Member)request.getSession().getAttribute("loginUser");
//		System.out.println(loginUser);
		if(loginUser == null || loginUser.getIsAdmin().equals("N")) {
//			System.out.println(request.getHeader("fetch"));
			
			boolean isFetch = "true".equals(request.getHeader("fetch"));
			if(isFetch) {
				response.setContentType("application/json; charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 status
			} else {
				response.getWriter().write("<script>alert('접근 권한이 없습니다');location.href='/home';</script>");
				response.setContentType("text/html; charset=UTF-8");
			}
			
			return false;
		}
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
		
	}

}
