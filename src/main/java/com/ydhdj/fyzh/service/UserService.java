package com.ydhdj.fyzh.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.ydhdj.fyzh.bean.AdviceBean;

public class UserService {
	private SqlSessionFactory sqlSessionFactory;
	private static final Logger log = LoggerFactory.getLogger(AdviceBean.class);
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	//请求建议信息
	public List<AdviceBean> getAdvices(int start,int limitCnt){
		SqlSession ss = sqlSessionFactory.openSession();
		try{
			HashMap<String,Object> parameter = new HashMap<String,Object>();
			parameter.put("limitCnt", limitCnt);
			parameter.put("start", start);
			return ss.selectList("itpdf_user.getAdvices",parameter);
		}finally{
			if(ss != null){ss.close();}
		}
	}
	//插入建议数据
	 public void insertAdvice(AdviceBean ab){
		 if(ab != null){
			SqlSession ss = sqlSessionFactory.openSession();
			try{
				String id = ab.getId();
				if(StringUtils.isEmpty(id)){return;}
				AdviceBean selAb = getById(id);
				boolean isExist = (selAb != null);
				if(!isExist){
					ss.insert("itpdf_user.insertAdvice",ab);
				}else{
					log.debug("itpdf_user.insert One Advice that does already exist!");
				}
			}finally{
				if(ss != null){ss.close();}
			}
		 }
	 }
	 //按照ID获取建议
	 public AdviceBean getById(final String id){
		 if(StringUtils.isEmpty(id)){return null;}
		 SqlSession ss = sqlSessionFactory.openSession();
		 try{
			return ss.selectOne("itpdf_user.getById",id);
		 }finally{
			if(ss != null){ss.close();}
		 }		 
	 }
	
}
