package com.tokioschool.web.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler
	public ModelAndView exceptionHandler(HttpServletRequest request, Exception exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", exception.getMessage());
		mav.addObject("exception", exception);
		mav.addObject("url" , request.getRequestURL());
		mav.setViewName("error");
		return mav;
	}
}
