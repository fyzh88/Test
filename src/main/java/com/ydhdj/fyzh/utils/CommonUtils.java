package com.ydhdj.fyzh.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.mysql.jdbc.StringUtils;

/*常用的工具类*/
public class CommonUtils {
	/*密码加密成MD5*/
	public static String encryptWithMD5(final String pwd){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(pwd.getBytes());
			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "123456";
	} 
	public static String encryptWithMD5(File file){
		FileInputStream is=null;;
		try {
			is = new FileInputStream(file);
			return encryptWithMD5(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "123456";
	}
	public static String encryptWithMD5(InputStream fis){
		if(fis != null){
			MessageDigest md;
			try {
				md = MessageDigest.getInstance("SHA-1");
				byte[] buffer = new byte[2048];
		        int length = -1;
		          while ((length = fis.read(buffer)) != -1) {
		            md.update(buffer, 0, length);
		        }
		        byte[] b = md.digest();
		        return toHex(b);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return "123456";
	}
	/*发送HTML格式的邮件*/
	public static void sendHtmlEmail(final String emailContent,final String destination){
		if(!StringUtils.isEmptyOrWhitespaceOnly(destination)){
			try{
				JavaMailSenderImpl ms = new JavaMailSenderImpl();
				ms.setHost("smtp.163.com");
				MimeMessage msg = ms.createMimeMessage();
				MimeMessageHelper msgHelper = new MimeMessageHelper(msg,"gbk");
				msgHelper.setTo(destination);
				msgHelper.setFrom("fyzh84@163.com");
				msgHelper.setSubject("账户密码重置邮件");
				msgHelper.setText(emailContent, true);
				ms.setUsername("fyzh84@163.com");
				ms.setPassword("68344701");
			
				Properties javaMailProperties = new Properties();
				javaMailProperties.put("mail.smtp.auth", "true");
				javaMailProperties.put("mail.smtp.timeout", 25000);
				ms.setJavaMailProperties(javaMailProperties);
				
				ms.send(msg);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	private static String toHex(byte buffer[]) {  
        StringBuffer sb = new StringBuffer(buffer.length * 2);  
        for (int i = 0; i < buffer.length; i++) {  
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));  
            sb.append(Character.forDigit(buffer[i] & 15, 16));  
        }    
        return sb.toString();  
    }
	
	/**
	 * 确认字符串是否为email格式
	 */
	public static boolean isEmail(String strEmail) {
	    String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
	    Pattern p = Pattern.compile(strPattern);
	    Matcher m = p.matcher(strEmail);
	    return m.matches();
	}
    /**
     * 判断手机格式是否正确
     */
	public static boolean isPhoneNum(String mobiles) {
		Pattern p = Pattern
		.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);		
		return m.matches();
	}
	/**
	 * 是否有效的QQ号
	 */
	public static boolean isQQ(String strQQ){
		Pattern p = Pattern.compile("\\d{5,11}");
		Matcher matcher = p.matcher(strQQ);
		return matcher.matches();
	}
	/**
	 * 格式化输出文件大小
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		DecimalFormat formater = new DecimalFormat("####.00");
		if (size < 1024) {
			return size + "bytes";
		} else if (size < 1024 * 1024) {
			float kbsize = size / 1024f;
			return formater.format(kbsize) + "KB";
		} else if (size < 1024 * 1024 * 1024) {
			float mbsize = size / 1024f / 1024f;
			return formater.format(mbsize) + "MB";
		} else if (size < 1024 * 1024 * 1024 * 1024) {
			float gbsize = size / 1024f / 1024f / 1024f;
			return formater.format(gbsize) + "GB";
		} else {
			return "size: error";
		}
	}
}
