package com.ydhdj.fyzh.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@RequestMapping("/show_main")
	public ModelAndView showMain(){
		ModelAndView mv = new ModelAndView();
		//选中第一个分类，并显示该分类中的内容
		BookService m_bs = (BookService)SpringContextUtils.getBean("main_service");
		List<Map<String,String>> category = m_bs.getAllCategory(1);
		if(category != null && !category.isEmpty()){
			String str_category = category.get(0).get("category");
			//按照类型取出所有的书籍数据
			List<BookInfoBean> books = m_bs.getByCategroy(str_category, 20);
			mv.getModel().put(CommonConst.MAIN_BOOKS_OF_CATEGORY_PERPAGE, books);
			//每行4本，显示5行
		}else{
			//此分类中没有任何的PDF文件
			log.error("There is not any book in this categaory");
		}
		mv.setViewName("show_category_detail");
		return mv;
	}
	//显示一个PDF文件的详细内容
	@RequestMapping("/show_pdf")
	public ModelAndView showPdfOf(final String bookId){
		ModelAndView mv = new ModelAndView();
		if(StringUtils.isEmpty(bookId)){
			mv.setViewName("show_not_exist");
		}else{
			BookService m_bs = (BookService)SpringContextUtils.getBean("main_service");
			BookInfoBean bi = m_bs.getById(bookId);
			mv.getModel().put(CommonConst.PDF_DETAIL_BOOK_INFO, bi);
			mv.getModel().put(CommonConst.PDF_TEXT_INFO, m_bs.getPdfText(bookId));
			mv.setViewName("show_pdf_detail");
		}
		return mv;
	}
	//显示PDF文件的第一页图片
	@RequestMapping("/getPdfImageOf")
	public void getPdfImageOf(HttpServletRequest request,HttpServletResponse response,final String bookId, int pageIndex){
		if(StringUtils.isEmpty(bookId)){return;}
		if(pageIndex >= 5){pageIndex = 4 ;}//我们仅仅生成了前5页的图片文件
		if(pageIndex < 0){pageIndex = 0;}
		BookService m_bs = (BookService)SpringContextUtils.getBean("main_service");
		File imageFile = m_bs.getPdfImageOf(bookId,pageIndex);
		if(imageFile == null){//所请求的PDF页图片不存在，我们应该采用默认替代
			imageFile = m_bs.getDummyPng();
		}
		if(imageFile != null){
			OutputStream os  = null;
			try{
				os = response.getOutputStream();
				response.reset();
				response.setHeader("Content-Disposition", "attachment; filename=xxxxxxx.png");
				response.setContentType("application/octet-stream; charset=utf-8");
			    os.write(FileUtils.readFileToByteArray(imageFile));
			    os.flush();
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}finally{
				try {
				if(os != null){
					os.close();
				}} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			log.error("ERROR,when get the png image!");
		}
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
					//读取使用PdfToText工具读取出来的文本信息，为得输入作者和出版信息的时候方便
					String strText = m_bs.readPdfToTextFile(bib.getMd());
					mv.getModel().put(CommonConst.UPLOAD_PDFTOTEXT_STR,strText);
				}else{
				}
				mv.getModel().put(CommonConst.UPLOAD_NOT_PDF_FILE,(bib==null));
			}else{
				//存盘失败
				log.error("Save Pdf file Failed!");
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
		//封面和前n页的图片文件的文件名称是使用PDF文件的md5值进行指定的，尾部有序号指示第x页
		aib.setFileName(bib.getMd());
		as.insertAttachment(aib);//封面图片（需要对图片的大小做限定，节约带宽）
		//
		AttachmentInfoBean aib_dir = new AttachmentInfoBean();
		aib_dir.setId(UUID.randomUUID().toString());
		aib_dir.setOwnerId(id);
		aib_dir.setFileName(bib.getMd());
		aib_dir.setFileType("dir");
		as.insertAttachment(aib_dir);//目录结构文件（文本性质）
		
		mv.setViewName("upload");
		return mv;
	}
}
