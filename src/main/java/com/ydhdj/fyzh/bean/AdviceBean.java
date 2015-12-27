package com.ydhdj.fyzh.bean;

import java.io.Serializable;
import java.util.Date;

public class AdviceBean implements Serializable{
	private static final long serialVersionUID = -5029461363567731964L;
	private String id;//ID
	private Date date;//建议创建的时间
	private String content;//建议内容
	private String author;//建议者
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date dt) {
		this.date = dt;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
}
