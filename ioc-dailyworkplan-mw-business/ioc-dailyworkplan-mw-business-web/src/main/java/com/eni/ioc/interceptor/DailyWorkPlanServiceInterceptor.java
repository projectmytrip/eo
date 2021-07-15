package com.eni.ioc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class DailyWorkPlanServiceInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(DailyWorkPlanServiceInterceptor.class);
	private static final String JWT_UID_WSS = "ADMAPMIOC-ST";
	private static final int codeKO = 501;
	private static final int codeForbidden = 403;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (!request.getMethod().equals("OPTIONS")) {
			try {
				response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				return true;

			} catch (Exception e) {
				logger.error(e.getMessage());
				response.sendError(codeKO, e.getMessage());
				return false;
			}
		} else {
			return true;
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
	}
}
