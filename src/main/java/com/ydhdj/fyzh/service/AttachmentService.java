package com.ydhdj.fyzh.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ydhdj.fyzh.bean.AttachmentInfoBean;

public class AttachmentService {
	
	private SqlSessionFactory sqlSessionFactory;

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	//
	public boolean insertAttachment(AttachmentInfoBean aib){
		if(aib != null){
			SqlSession ss = sqlSessionFactory.openSession();
			try{
				ss.insert("itpdf_attachment.insert",aib);
			}finally{
				if(ss != null){ss.close();}
			}
			return true;
		}
		return false;
	}
}
