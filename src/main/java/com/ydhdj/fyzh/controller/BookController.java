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
		//选中第一个分类，并显示该分类中的内容
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("upload");
		return mv;
	}
	//上传文件功能目前的打算是只为自己方便构建数据库数据而引入
	@RequestMapping("upload_pdf")
	public ModelAndView uploadPdf(@RequestParam(value="file", required=false)MultipartFile file,
			HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.getModel().put(CommonConst.UPLOAD_NOT_PDF_FILE,true);
		BookService m_bs = (BookService)SpringContextUtils.getBean("main_service");
		BookInfoBean same_bib= m_bs.getSameFile(file);
		File uploadFfn=null;
		boolean notExist = (same_bib == null);
		if(notExist){
			//上传文件不存在，将文件保存在本地，为后续的解析PDF文件做准备
			//如果是真实的部署环境，我们这里应该如何处理呢？
			//其实我们在第一阶段是不准备开放用户上传PDF文件功能的，那么我们需要的仅仅是这个PDF文件对应的数据库记录而已
			//如果牵扯到文件的上传，那么恐怕还有数据流量方面的限制，这一点也是我们考虑的范围
			uploadFfn = m_bs.savePDF(file);
			if(uploadFfn != null){
				String name = file.getOriginalFilename();
				BookInfoBean bib = m_bs.extractBookInfo(uploadFfn,name);
				if(bib != null){
					mv.getModel().put(CommonConst.UPLOAD_RETURN_PARAM, bib);
				}else{
				}
				mv.getModel().put(CommonConst.UPLOAD_NOT_PDF_FILE,(bib==null));
			}else{
				//存盘失败
			}
		}else{
			//如果在数据库中已经存在了这本PDF文件的数据，那么我们可以简单的进行忽略
		}
		mv.getModel().put(CommonConst.UPLOAD_ALREADY_EXIST, !notExist);
		mv.setViewName("upload");
		return mv;
	}
	
	@RequestMapping("/share_pdf")
	public ModelAndView sharePdf(@RequestBody BookInfoBean bib){
		ModelAndView mv = new ModelAndView();
		BookService m_bs = (BookService)SpringContextUtils.getBean("main_service");
		//为了安全起见，还是在此处处理ID等敏感信息吧
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
		as.insertAttachment(aib);//封面图片（需要对图片的大小做限定，节约带宽）
		//
		AttachmentInfoBean aib_dir = new AttachmentInfoBean();
		aib_dir.setId(UUID.randomUUID().toString());
		aib_dir.setOwnerId(id);
		aib_dir.setFileName("");
		aib_dir.setFileType("dir");
		as.insertAttachment(aib_dir);//目录结构文件（文本性质）
		
		
		mv.setViewName("upload");
		return mv;
	}
}
