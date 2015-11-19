package com.ydhdj.fyzh.bean;

import java.io.Serializable;
import java.util.Date;

public class UserInfoBean implements Serializable{

	public UserInfoBean(){}
	private static final long serialVersionUID = 6670377636220903399L;
	
	private String id;
	private String name;//用户名称
	private Date date;//记录生成日期
	private String email;//电子邮件地址
	private String phone;//电话号码
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
