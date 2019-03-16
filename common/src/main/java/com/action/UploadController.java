package com.action;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

@Controller
public class UploadController implements ServletContextAware {

	private ServletContext application;
	
	//url不要和文件夹同名，否则异常
	@RequestMapping("/upload2")
	public String upload2(@RequestParam(required=false) MultipartFile[] files,String title) throws Exception{
		
		String path = application.getRealPath("upload")+File.separator;
		System.out.println(path+" \t title:"+title);
		
		for (MultipartFile file : files) {
			//判断文件非空，上传
			if(!file.isEmpty()){
				String type = file.getContentType();
				long size = file.getSize();
				System.out.println("文件类型："+type+"\t文件大小："+size);
				//随机字符串
				String uuid = UUID.randomUUID().toString();
				System.out.println(" uuid = "+uuid);
				
				//文件名称
				String fname = file.getOriginalFilename();
				System.out.println("  name = "+fname);
				
				//目标文件
				File destFile = new File(path,uuid+fname);
				
				//上传：将上传文件流写入目标文件
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
			}
		}
		
		return "success";
	}

	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response,String realName) throws Exception{
		//定义ContentType 为流
		String contentType = "application/octet-stream";
		/*response.setContentType("text/html;charset=UTF-8");  
        request.setCharacterEncoding("UTF-8");  */
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
 
        //默认的文件在 /upload 下.
        String ctxPath = application.getRealPath("upload")+File.separator;  
        System.out.println(" ctxPath = "+ctxPath);
        
        String downLoadPath = ctxPath + realName; //要下载文件的绝对地址 
  
        long fileLength = new File(downLoadPath).length();  
  
        //设置ContentType
        response.setContentType(contentType);  
        response.setHeader("Content-disposition", "attachment; filename="  
                + new String(realName.getBytes("UTF-8"), "ISO-8859-1"));  
        response.setHeader("Content-Length", String.valueOf(fileLength));  
  
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
        bos = new BufferedOutputStream(response.getOutputStream());  
        byte[] buff = new byte[2048];  
        
        int bytesRead=bis.read(buff);//读取
        
        while (bytesRead != -1) {  
            bos.write(buff, 0, bytesRead);//写入  
            bytesRead=bis.read(buff);
        } 
        
        bis.close();  //关闭流
        bos.flush();
        bos.close();  
	}
	
	public void setServletContext(ServletContext servletContext) {
		
		this.application = servletContext;
	}
	
}
