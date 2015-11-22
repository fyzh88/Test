package com.ydhdj.fyzh.pdf;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.util.StringUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.SimpleBookmark;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;


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
	@Deprecated
	public void savePdfCover_(final File pdfFile,final String coverPath,final String md){
		if(pdfFile == null || StringUtils.isEmpty(coverPath)){return ;}
		if(StringUtils.isEmpty(md)){return;}
		//
		RandomAccessFile raf = null;
		try{
			 raf = new RandomAccessFile(pdfFile, "r");  
		     FileChannel channel = raf.getChannel();  
		     ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());  
		     
		     PDFFile pdffile = new PDFFile(buf);  
		     System.out.println("页数： " + pdffile.getNumPages());  
		     int pageNum = pdffile.getNumPages();
		     int cnt = MAX_PDF_PAGES_TO_PIC;
		     if(pageNum < MAX_PDF_PAGES_TO_PIC){cnt = pageNum;}
		     for (int i = 1; i <= cnt; i++) {  
		            // draw the first page to an image  
		            PDFPage page = pdffile.getPage(i);  
		            // get the width and height for the doc at the default zoom  
		            Rectangle rect = new Rectangle(0, 0, (int) page.getBBox()
		                    .getWidth(), (int) page.getBBox().getHeight());  
		            // generate the image  
		            Image img = page.getImage(rect.width, rect.height, // width &  
		                    // height  
		                    rect, // clip rect  
		                    null, // null for the ImageObserver  
		                    true, // fill background with white  
		                    true // block until drawing is done  
		                    );  
		            BufferedImage tag = new BufferedImage(rect.width, rect.height,BufferedImage.TYPE_INT_RGB);  
		              
		            Graphics2D g=tag.createGraphics();  
		            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		            g.drawImage(img, 0, 0, rect.width, rect.height, null);  
		            //
		           saveToFile(tag,coverPath,md,i);
		     }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(raf != null){raf.close();}
			}catch(Exception e){e.printStackTrace();	}
		}
	}
	//图片存盘
	@Deprecated
	private void saveToFile(BufferedImage image,final String coverPath,final String md,int index){
		if(image == null || StringUtils.isEmpty(coverPath)){return ;}
		if(StringUtils.isEmpty(md)){return;}
		if(index < 0){return ;}
		ImageWriter writer = (ImageWriter)ImageIO.getImageWritersByFormatName("png").next();
		File dir = new File(coverPath);
		dir.mkdirs();
		//构造封面图片的文件名称,PDF文件的MD5指纹作为文件名称
		StringBuilder builder = new StringBuilder(md);
		builder.append("_").append(index).append(".png");
		File file = new File(dir,builder.toString());
		//
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(file);
			ImageOutputStream ios = ImageIO.createImageOutputStream(output);
			writer.setOutput(ios);
			writer.write(image);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
			if(output != null){output.close();}
			}catch(Exception e){e.printStackTrace();}
		}		
	}
}
