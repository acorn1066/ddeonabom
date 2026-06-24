package kh.ddeonabom.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NavbarInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (modelAndView != null) {
            String viewName = modelAndView.getViewName();
            if (viewName != null && viewName.startsWith("redirect:")) return;
            modelAndView.addObject("currentURI", request.getRequestURI());
        }
    }
}