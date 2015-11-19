package com.ydhdj.fyzh.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ydhdj.fyzh.common.CommonConst;
import com.ydhdj.fyzh.utils.SpringContextUtils;
/**
 * SITEMESH装饰器所需数据
 * @author FYZH
 *
 */
public class CommonService {
	
	public void init(HttpServletRequest request){
		BookService bs = (BookService)SpringContextUtils.getBean("main_service");
	    List<Map<String,String>> categorys = bs.getAllCategory(-1);
		//获取所有PDF书籍分类
		request.setAttribute(CommonConst.ALL_CATEGORY, categorys);
	}
}
