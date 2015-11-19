package com.ydhdj.fyzh.bean;

import java.io.Serializable;
import java.util.Date;

public class BookInfoBean implements Serializable{
	public BookInfoBean(){}
	private static final long serialVersionUID = -8051795905555715248L;
	private String id;//
	private String user_id;//文件所属者ID
	private Date date;//数据生成日期
	private String name;//书籍名称
	private String author;//作者
	private String md;//文件的消息摘要
	private String save_name;//文件被存盘的名称
	private String publisher;//出版社
	private int pages;//页数
	private int file_len;//文件大小
	private String category;//书籍分类
	private String shared_addr;//分享地址
	private String share_code;//资源提取码
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMd() {
		return md;
	}
	public void setMd(String md) {
		this.md = md;
	}
	public String getSave_name() {
		return save_name;
	}
	public void setSave_name(String save_name) {
		this.save_name = save_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getFile_len() {
		return file_len;
	}
	public void setFile_len(int file_len) {
		this.file_len = file_len;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public String getShared_addr() {
		return shared_addr;
	}
	public void setShared_addr(String shared_addr) {
		this.shared_addr = shared_addr;
	}
	public String getShare_code() {
		return share_code;
	}
	public void setShare_code(String share_code) {
		this.share_code = share_code;
	}
	
}
