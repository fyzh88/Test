package com.ydhdj.fyzh.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;

public class PdfParser {
	private PdfReader m_rd = null;
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
	private void getMetaInfo(){
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
	
}
