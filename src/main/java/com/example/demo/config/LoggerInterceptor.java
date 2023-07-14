package com.example.demo.config;

import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggerInterceptor implements HandlerInterceptor{

	Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		log.info("Request is complete");
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
		log.info("Handler execution is complete");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		log.info("Before Handler execution");
		return true;
	}
}
