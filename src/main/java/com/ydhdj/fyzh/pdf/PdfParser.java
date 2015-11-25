package com.ydhdj.fyzh.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;


public class PdfParser {
	private PdfReader m_rd = null;
	private static final int MAX_PDF_PAGES_TO_PIC = 5; //提取PDF文件中前多少页为图片
	public PdfParser(final File file){
		if(m_rd == null){
			try {
				InputStream is = new FileInputStream(file);
				m_rd = new PdfReader(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//获取页码
	public int getPageNum(){
		if(m_rd != null){
			return m_rd.getNumberOfPages();
		}
		return 0;
	}
	//获取元数据
	public void getMetaInfo(){
		if(m_rd != null){
			 HashMap<String,String> map = m_rd.getInfo(); 
			   Set<String> set = map.keySet(); 
			   java.util.Iterator<String> iterator = set.iterator(); 
			   while(iterator.hasNext()){ 
				   String key = (String) iterator.next(); 
				   System.out.println(key + ":" + map.get(key)); 
		     }
		}
	}
		
	//获取目录摘要
	public List<String> getBookmark() {
		if(m_rd!=null){
			List<String> arr = new ArrayList<String>();
			List<HashMap<String,Object>> bm = SimpleBookmark.getBookmark(m_rd);
			if(bm != null){
				for ( Iterator<HashMap<String,Object>> i = bm.iterator () ; i.hasNext () ; ) {
				       showBookmark (( HashMap<String,Object> ) i.next (),arr) ;
				}
				return arr;
			}
		}
		return Collections.emptyList();
	}
	
	private void showBookmark ( HashMap<String,Object> bookmark ,List<String> arr) {
	     arr.add(bookmark.get("Title").toString());
	     List<HashMap<String,Object>> kids = ( List<HashMap<String,Object>> ) bookmark.get ( "Kids" ) ;
	     if ( kids == null ){
	       return;
	     }
	     for ( Iterator<HashMap<String,Object>> i = kids.iterator () ; i.hasNext () ; ) {
	       showBookmark (( HashMap<String,Object> ) i.next (),arr) ;
	     }
	 }
	
	//采用PDFRender 库实现存在中文无法被支持的问题,所以我们不得不寻找其他途径解决这个问题
	//我们想到的另一个解决办法是采用 linux的pdftk工具和ImageMagick工具配合完成PDF前n页的
	//图片转换任务
	//提取PDF文件页为图片
	//我们采用PDF文件的MD5值作为，封面图片的检索依据
	public void savePdfCover(File pdfFile,final String coverPath,final String md){
		if(pdfFile == null || StringUtils.isEmpty(coverPath)|| StringUtils.isEmpty(md)){return ;}
		File dir = new File(coverPath);
		dir.mkdirs();
		String fileName= pdfFile.getName();
		String pdfDir = pdfFile.getParent();
		    List<String> strList = new ArrayList();  		  
		    StringBuilder builder = new StringBuilder();
		    //change the directory
		    builder.append("cd ").append(pdfDir).append(";");
		    //get the first MAX_PDF_PAGES_TO_PIC page to a new PDF file
		    builder.append("pdftk A=\'").append(fileName).append("\' cat A1-").append(this.MAX_PDF_PAGES_TO_PIC);
		    String limitPageFileName = UUID.randomUUID().toString()+"_tmp.pdf";
		    builder.append(" output ").append(limitPageFileName).append(";");
		    //convert the new PDF to image PAGE by PAGE
		    builder.append("convert ").append(limitPageFileName).append(" ").append(md).append(".png;");
		    //extract TEXT from new PDF
		    builder.append("pdftotext -f 1 -l ").append(this.MAX_PDF_PAGES_TO_PIC).append(" \'");
		    builder.append(limitPageFileName).append("\' ").append(md).append(".txt");
		    //delete the template file
		    builder.append("rm ").append(limitPageFileName).append(";");
		    //move  all the PNG file to COVER PATH
		    builder.append("mv ").append(md).append("*.* ").append(coverPath).append(";");
		    //task OVER!
		    System.out.println(builder.toString());
		    String shStr = builder.toString();
		    try{
		        Process process;  
		        process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shStr},null,null);  
		        InputStreamReader ir = new InputStreamReader(process  
		                .getInputStream());  
		        LineNumberReader input = new LineNumberReader(ir);  
		        String line;  
		        process.waitFor();  
		        while ((line = input.readLine()) != null){  
		            strList.add(line);  
		        }  
		        System.out.print(strList.toString());
		    }catch(Exception e){e.printStackTrace();}
	}
}
