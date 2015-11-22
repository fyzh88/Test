package com.ydhdj.fyzh.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.ydhdj.fyzh.bean.BookInfoBean;
import com.ydhdj.fyzh.pdf.PdfParser;
import com.ydhdj.fyzh.utils.CommonUtils;
import com.ydhdj.fyzh.utils.DefaultConfiguration;
import com.ydhdj.fyzh.utils.FileTypeProbe;

public class BookService {
    public static  String COVER_PATH; /** 封面图片路径 */    
    public static  String DIRECTORY_PATH;/** PDF目录文件路径*/
    public static  String UPLOAD_PDF_PATH;/** 上传PDF文件暂存路径*/
    
    static {
        try {
            DefaultConfiguration config = new DefaultConfiguration("server-config.properties").load();
            COVER_PATH = config.get("cover_path", "/home/fyzh/itpdf/itpdf_cover");//封面图片
            DIRECTORY_PATH = config.get("directory_path", "/home/fyzh/itpdf/itpdf_directory");//PDF目录文件路径
            UPLOAD_PDF_PATH=config.get("upload_pdf_path","/home/fyzh/itpdf/itpdf_upload");//PDF上传保存路径
            
        } catch (Exception ex) {
            //LOG.error("Upload file path read failure, check if property file named 'server-config.properties' exists.");
        }
    }
	private SqlSessionFactory sqlSessionFactory;

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	//保存上传上来的PDF文件到服务器目录中
	//文件秒传的问题暂时没有考虑
	public File savePDF(MultipartFile file){
		if(file != null){
			try {
				final String name = file.getOriginalFilename();
				InputStream is = file.getInputStream();		
				File uploadFileDir = new File(UPLOAD_PDF_PATH);
				uploadFileDir.mkdirs();
				//上传文件存档
				StringBuilder builder = new StringBuilder(UUID.randomUUID().toString());
				builder.append("_").append(name);
				String fn = builder.toString();
				//?文件名称的长度需要限定在256以内
				File uploadFile = new File(uploadFileDir,fn);
				FileOutputStream os = new FileOutputStream(uploadFile);
				IOUtils.copy(is, os);
				os.close();
				return uploadFile;
				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return null;
	}
	//提取文件中的有用信息
	public BookInfoBean extractBookInfo(File file,final String name){
		if(file != null){
			try{
				BookInfoBean bib = null;
				String fileType = FileTypeProbe.getFileType(file);
				if(fileType.compareToIgnoreCase("pdf")==0){
					long len = file.length();
					bib  = new BookInfoBean();
					//被存盘文件名称
					bib.setSave_name(file.getName());
					//文件大小
					bib.setFile_len((int)len);
					//文件的原始名称
					bib.setName(name);
					//作者
					bib.setAuthor("xxx");
					//分类
					bib.setCategory("C++");
					//出版社
					bib.setPublisher("xxxxx");
					//分享地址
					bib.setShared_addr("");
					//分享提取码
					bib.setShare_code("");
					//MD5
					String strMd = CommonUtils.encryptWithMD5(file);
					bib.setMd(strMd);
					//文件页数
					PdfParser pp  = new PdfParser(file);
					bib.setPages(pp.getPageNum());
					pp.getMetaInfo();
					pp.getBookmark();
					//生成前n页PDF缩略图
					pp.savePdfCover(file,COVER_PATH,strMd);
				}else{
					//不是pdf文件
				}
				return bib;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	//判定相同文件是否存在
	public BookInfoBean getSameFile(final MultipartFile file){
		InputStream is=null;
		try {
			is = file.getInputStream();
			//判定相同文件是否存在
			String md = CommonUtils.encryptWithMD5(is);
			List<BookInfoBean> res = getByMd(md);
			if(!res.isEmpty()){
				return res.get(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	//依据文件的消息摘要获取
	private List<BookInfoBean> getByMd(final String md){
		if(md != null){
			SqlSession ss = sqlSessionFactory.openSession();
			try{
				return ss.selectList("itpdf_main.getByMd",md);
			}finally{
				if(ss!= null){ss.close();}
			}
		}
		return Collections.emptyList();
	}
	//PDF目录信息存盘
	private String saveBookmarks(final List<String> bm,final String name){
		if(bm != null){
			String str_bm = JSON.toJSONString(bm);
			StringBuilder builder = new StringBuilder(UUID.randomUUID().toString());
			builder.append("_").append(name).append("_bookmark");
			File dir = new File(DIRECTORY_PATH);
			dir.mkdirs();
			File file = new File(dir,builder.toString());			
			try {
				FileUtils.writeStringToFile(file, str_bm);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return builder.toString();//存储目录文件的名称
		}
		return "";
	}
	
	//获取所有书籍的分类信息
	public List<Map<String,String>> getAllCategory(Integer limitCnt){
		SqlSession ss = sqlSessionFactory.openSession();
		try{
			return ss.selectList("itpdf_main.getAllCategory",limitCnt);
		}finally{
			if(ss != null){ss.close();}
		}
		
	}
	//获取某分类中PDF书籍信息
	public List<BookInfoBean> getByCategroy(final String category,int limitCnt){
		if(!StringUtils.isEmpty(category)){
			SqlSession ss = sqlSessionFactory.openSession();
			try{
				HashMap<String,Object> parameter = new HashMap<String,Object>();
				parameter.put("category",category);
				parameter.put("limitCnt", limitCnt);
				return ss.selectList("itpdf_main.getByCategory",parameter);
			}finally{
				if(ss != null){ss.close();}
			}
		}
		return Collections.emptyList();
	}
	//插入PDF文件信息记录
	public boolean insertPdfInfo(BookInfoBean bib){
		SqlSession ss = sqlSessionFactory.openSession();
		try{
			if(bib != null){
				bib.setId(UUID.randomUUID().toString());
				ss.insert("itpdf_main.insert", bib);
				return true;
			}else{
				return false;
			}
		}finally{
			if(ss != null){ss.close();}
		}
	}
	
}
