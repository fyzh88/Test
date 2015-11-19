package com.ydhdj.fyzh.bean;

import java.io.Serializable;
import java.util.Date;

public class AttachmentInfoBean implements Serializable {

	public AttachmentInfoBean(){}
	private static final long serialVersionUID = 7052535170864497463L;
	
	private String id;
	private String ownerId;/*附件的拥有者*/
	private Date date;/*记录生成时间*/
	private String fileName;/*附件文件名称 */
	private String fileType;/*附件类型 cover，dir*/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
}
