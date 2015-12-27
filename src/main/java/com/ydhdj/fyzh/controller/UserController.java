package com.ydhdj.fyzh.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ydhdj.fyzh.bean.AdviceBean;
import com.ydhdj.fyzh.common.CommonConst;
import com.ydhdj.fyzh.service.UserService;
import com.ydhdj.fyzh.utils.SpringContextUtils;

@Controller("/user")
public class UserController {
	@RequestMapping("/login")
	public ModelAndView login(){
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("show_login");
		return mv;
	}
	@RequestMapping("/register")
	public ModelAndView register(){
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("show_register");
		return mv;
	}
	//查询意见反馈
	@RequestMapping("/show_advice")
	public ModelAndView showAdvice(){
		ModelAndView mv = new ModelAndView();
		UserService us = (UserService)SpringContextUtils.getBean("user_service");
		List<AdviceBean> advices = us.getAdvices(0,50);
		mv.getModel().put(CommonConst.ADVICES, advices);
		mv.setViewName("advice");
		return mv;
	}
	//发表意见
	@RequestMapping("/send_advice")
	public ModelAndView sendAdvice(final String name,final String content){
		ModelAndView mv = new ModelAndView();
		if(!StringUtils.isEmpty(content)){
			AdviceBean ab = new AdviceBean();
			ab.setId(UUID.randomUUID().toString());
			ab.setAuthor(name);
			String strContent = content;
			if(content.length()>256){
				strContent = content.substring(0, 254);
			}
			ab.setContent(strContent);
			//
			UserService us = (UserService)SpringContextUtils.getBean("user_service");
			us.insertAdvice(ab);
			//
			mv.setViewName("redirect:/show_advice");
		}else{
			mv.setViewName("redirect:/show_advice");
		}
		return mv;
	}
	
}
