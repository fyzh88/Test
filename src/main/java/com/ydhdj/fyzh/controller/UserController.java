package com.ydhdj.fyzh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/user")
public class UserController {
	@RequestMapping("login")
	public ModelAndView login(){
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("show_login");
		return mv;
	}
	@RequestMapping("register")
	public ModelAndView register(){
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("show_register");
		return mv;
	}
}
