package com.ydhdj.fyzh.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ydhdj.fyzh.bean.AttachmentInfoBean;
import com.ydhdj.fyzh.bean.BookInfoBean;
import com.ydhdj.fyzh.common.CommonConst;
import com.ydhdj.fyzh.service.AttachmentService;
import com.ydhdj.fyzh.service.BookService;
import com.ydhdj.fyzh.utils.SpringContextUtils;

@Controller("/")
public class BookController {
	
	@RequestMapping("/show_main")
	public ModelAndView showMain(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("upload");
		return mv;
	}
	@RequestMapping("upload_pdf")
	public ModelAndView uploadPdf(@RequestParam(value="file", required=false)MultipartFile file,
			HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		BookService m_bs = (BookService)SpringContextUtils.getBean("main_service");
		BookInfoBean same_bib= m_bs.getSameFile(file);
		File uploadFfn=null;
		boolean notExist = (same_bib == null);
		if(notExist){
			uploadFfn = m_bs.savePDF(file);
		}else{
			File dir = new File(BookService.UPLOAD_PDF_PATH);
			uploadFfn = new File(dir,same_bib.getSave_name());
		}
		mv.getModel().put(CommonConst.UPLOAD_ALREADY_EXIST, !notExist);
		//
		if(uploadFfn != null){
			String name = file.getOriginalFilename();
			BookInfoBean bib = m_bs.extractBookInfo(uploadFfn,name);
			bib.setAuthor("xxx");
			bib.setCategory("C++");
			bib.setPublisher("xxxxx");
			bib.setShared_addr("");
			bib.setShare_code("");
			mv.getModel().put(CommonConst.UPLOAD_RETURN_PARAM, bib);
		}
		mv.setViewName("upload");
		return mv;
	}
	
	@RequestMapping("/share_pdf")
	public ModelAndView sharePdf(@RequestBody BookInfoBean bib){
		ModelAndView mv = new ModelAndView();
		BookService m_bs = (BookService)SpringContextUtils.getBean("main_service");
		String id = UUID.randomUUID().toString();
		bib.setId(id);		
		bib.setUser_id("fec8a497-48ac-11e5-8b13-f56105f7c907");//fyzh		
		m_bs.insertPdfInfo(bib);
		//
		AttachmentService as = (AttachmentService)SpringContextUtils.getBean("attach_service");
		AttachmentInfoBean aib = new AttachmentInfoBean();
		aib.setId(UUID.randomUUID().toString());
		aib.setOwnerId(id);
		aib.setFileType("cover");
		aib.setFileName("");
		as.insertAttachment(aib);//封面图片
		//
		AttachmentInfoBean aib_dir = new AttachmentInfoBean();
		aib.setId(UUID.randomUUID().toString());
		aib.setOwnerId(id);
		aib.setFileName("");
		aib.setFileType("dir");
		as.insertAttachment(aib_dir);//目录结构文件
		
		
		mv.setViewName("upload");
		return mv;
	}
}
