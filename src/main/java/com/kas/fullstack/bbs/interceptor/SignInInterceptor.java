package com.kas.fullstack.bbs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class SignInInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// write.bbs 요청이 들어오면 컨트롤러의 wirte.bbs 요청을 처리하기 전에 동작할 코드
		if(!request.getMethod().equalsIgnoreCase("GET")) {
			return true;
		}else {
			HttpSession session = request.getSession();
			if(session.getAttribute("id")==null) {
				response.sendRedirect("login.bbs"); //get요청임ㄴ
				return false;
			}
			return true;
		}
		
	}

}
