package io;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.upload.FormFile;

public class FileUpload {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String fileDir;
	private String fileName;
	private int fileMaxSize;
	private String[] fileType;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(int fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}

	public String[] getFileType() {
		return fileType;
	}

	public void setFileType(String[] fileType) {
		this.fileType = fileType;
	}

	public FileUpload(){
		//默认最大支持50M
		this.fileMaxSize = 50 * 1024 * 1024;
		
		//默认支持的文件格式
		this.fileType = new String[]{".jpg",".png",".jpeg",".gif",".doc",".docx",".txt",".zip"};
	}

	public void init(HttpServletRequest request,HttpServletResponse response,String fileDir){
		this.request = request;
		this.response = response;
		this.fileDir = fileDir;
	} 
	
	public void init2(HttpServletRequest request,String fileDir){
		this.request = request;
		this.fileDir = fileDir;
	} 
	
	/**
	 * 文件上传方法
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void upLoad() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		List fileTypeList = Arrays.asList(fileType);
		try{ 
        	//判断文件路径
        	if(fileDir==null || "".equals(fileDir.trim())){
        		throw new Exception("未指定文件存储目录！");
        	}
        	
            if(ServletFileUpload.isMultipartContent(request)) {  
                DiskFileItemFactory factory = new DiskFileItemFactory();  
                //设置内存缓冲区的大小
                factory.setSizeThreshold(1 * 1024 * 1024);
                //指定临时文件目录
                factory.setRepository(new File(fileDir+"\\temp"));   
                ServletFileUpload sfu = new ServletFileUpload(factory);  
                sfu.setHeaderEncoding("UTF-8");
                
                //设置每个文件最大为
                sfu.setFileSizeMax(fileMaxSize);
                
                //一共最多能上传的大小
                //sfu.setSizeMax(10 * 1024 * 1024);   
                 
                List<FileItem> fileItems = sfu.parseRequest(request);  
                if (!new File(fileDir).isDirectory()){  
                    new File(fileDir).mkdirs();   
                }  
                
                //便利Form中的表单列表
                int leng = fileItems.size();  
                for(int n=0;n<leng;n++) {  
                    FileItem item = fileItems.get(n); // 从集合中获得一个文件流  
                    
                    if(item==null || item.getName()==null || item.getName().length()==0){
                    	continue;
                    }
                    
                    //普通表单字段列表
                    if(item.isFormField()){
                    	continue;
                    }
                    
                    //获取上传文件的名称
                    String ifileName = item.getName();
                    ifileName = ifileName.substring(ifileName.lastIndexOf("\\") + 1);
                    
                    //获取文件后缀名,比较文件格式
                    String eName = ifileName.substring(ifileName.lastIndexOf("."));
                    eName = eName!=null?eName.toLowerCase():"";
                    
                    if(!fileTypeList.contains(eName)){
                    	throw new Exception("上传文件格式不支持！");
                   	}
                    
                    //上传后存储文件名称，如果未传人文件名称则默认
                    String fname = this.fileName + eName;
                    if(fname==null || "".equals(fname)){
                    	 fname=sdf.format(new Date())+"-"+n+ifileName;
                    }
                    
                    // 写入文件 
                    item.write(new File(fileDir,fname));   
                }  
            }  
        }catch(Exception e) {
        	//throw e;
        	e.printStackTrace();
        }  
	}
	
	public void upload3() throws Exception {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		List fileTypeList = Arrays.asList(fileType);
		try {
			List<FileItem> list = upload.parseRequest(request);
			int n=0;
			for(FileItem item : list){
				if(item.isFormField()){
					//获取上传文件的名称
                    String ifileName = item.getName();
                    ifileName = ifileName.substring(ifileName.lastIndexOf("\\") + 1);
                    
                    //获取文件后缀名,比较文件格式
                    String eName = ifileName.substring(ifileName.lastIndexOf("."));
                    eName = eName!=null?eName.toLowerCase():"";
                    
                    if(!fileTypeList.contains(eName)){
                    	throw new Exception("上传文件格式不支持！");
                   	}
                    
                    //上传后存储文件名称，如果未传人文件名称则默认
                    String fname = this.fileName + eName;
                    if(fname==null || "".equals(fname)){
                    	 fname=sdf.format(new Date())+"-"+n+ifileName;
                    }
                    
                    // 写入文件 
                    item.write(new File(fileDir,fname));
                    n=n+1;
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 文件下载
	 * 
	 * @param downfilename
	 * @param filename
	 * @throws IOException
	 */ 
	public void downloadFile(String downfilename, String filename) throws Exception {
		FileInputStream fin = null;
		OutputStream outs = null;
		
		try{
			File file = new File(fileDir, downfilename);
			long len = file.length();
			byte a[] = new byte[600];

			fin = new FileInputStream(file);
			outs = response.getOutputStream();

			response.setHeader("Content-Disposition", "attachment; filename="+ filename);
			//response.setContentType(filetype);
			response.setContentType("application/x-msdownload");
			response.setHeader("Content_Lenght", String.valueOf(len));

			int read = 0;
			while ((read = fin.read(a)) != -1) {
				outs.write(a, 0, read);
			}
			outs.flush();
			outs.close();
			fin.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(fin!=null){
				fin.close();
			}
			if(outs!=null){
				outs.close();
			}
		}
	}
	
	/**
     * FORMACTION下的文件上传
     * 
     * @param file
     * @param request
     * @param path
     * @return
     */
    public void doUploadFile_formfile(FormFile file) throws Exception{
    	List fileTypeList = Arrays.asList(fileType);
		if(file.getFileSize() <= 0){
		    return;
		} 
		if(fileDir==null || "".equals(fileDir.trim())){
    		throw new Exception("未指定文件存储目录！");
    	}
		InputStream stream = null;
		OutputStream bos = null;
		String filepath="";
		
		try{
			stream = file.getInputStream();
			
			File tempFile=new File(fileDir);
		    if(!tempFile.exists()){
		    	tempFile.mkdirs();
		    }
		    String extension=(file.getFileName()).substring((file.getFileName()).lastIndexOf("."), (file.getFileName()).length());
		    if(!fileTypeList.contains(extension.toLowerCase())){
            	throw new Exception("上传文件格式不支持！");
           	}
		    
		    filepath = fileDir + File.separator + fileName;
		    bos = new FileOutputStream(filepath);
		    int bytesRead = 0;
		    byte[] buffer = new byte[8192];
		    while ((bytesRead = stream.read(buffer, 0, 8192))!=-1){
		    	bos.write(buffer, 0, bytesRead);
		    }
		    bos.close();
		    stream.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
			if(stream!=null){
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
    }
    
    /**
     * @param args
     * folderFullPath 目标文件夹
     */
    //删除文件夹下的所有文件
    public void deleteAllFile(String folderFullPath)
    {
	boolean ret = false;
	File file = new File(folderFullPath);
	if (file.exists())
	{
	    if (file.isDirectory())
	    {
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++)
		{
		    String filePath = fileList[i].getPath();
		    deleteAllFile(filePath);
		}
	    }
	    if (file.isFile())
	    {
		file.delete();
	    }
	}else{
		file.mkdirs();
	}
    }
    
    /**
     * 解压文件夹
     * 
     * @param zipFileName
     * @param request
     * @param extPlace
     * @return 文件相对路径
     * @throws FileNotFoundException
     * @throws IOException
     * zipFileName 目标文件
     * extPlace 解压地址
     */
    public void extZipFileList(String zipFileName, String extPlace)
    {
	try
	{
		ZipInputStream in = new ZipInputStream(new FileInputStream(
		    zipFileName));

	    ZipEntry entry = null;

	    while ((entry = in.getNextEntry()) != null)
	    {

		String entryName = entry.getName();
		
		if (entry.isDirectory())
		{
		    File file = new File(entryName);
		    file.mkdirs();
		    System.out.println("创建文件夹: " + entryName);
		} else
		{
            try
            {
			if(entryName.indexOf("/")>=0)//带文件夹
			{
				String[] entryNamesz=entryName.split("/");
				entryName=entryNamesz[entryNamesz.length-1];
			}
            }
            catch (Exception e) {
				
			}
			
		    FileOutputStream os = new FileOutputStream(extPlace
			    + "\\" + entryName);
		    System.out.println("创建文件夹: " + entryName);
		    //	   Transfer   bytes   from   the   ZIP   file   to   the   output   file 
		    byte[] buf = new byte[1024];

		    int len;
		    while ((len = in.read(buf)) > 0)
		    {
			os.write(buf, 0, len);
		    }
		    os.close();
		    in.closeEntry();

		}
	    }

	} catch (IOException e)
	{
		String aaaString="";
		e.printStackTrace();
	}
	System.out.println("解压文件成功 ");
    } 
    
    public void extZipFileListDG(String zipFileName, String extPlace)
    {
	try
	{
		ZipInputStream in = new ZipInputStream(new FileInputStream(
		    zipFileName));

	    ZipEntry entry = null;

	    while ((entry = in.getNextEntry()) != null)
	    {

		String entryName = entry.getName();
		
		if (entry.isDirectory())
		{
		    File file = new File(entryName);
		    file.mkdirs();
		    System.out.println("创建文件夹: " + entryName);
		} else
		{
            try
            {
			if(entryName.indexOf("/")>=0)//带文件夹
			{
				String[] entryNamesz=entryName.split("/");
				entryName=entryNamesz[entryNamesz.length-1];
			}
            }
            catch (Exception e) {
				
			}
			
		    FileOutputStream os = new FileOutputStream(extPlace
			    + File.separator + File.separator + entryName);
		    System.out.println("创建文件夹: " + entryName);
		    //	   Transfer   bytes   from   the   ZIP   file   to   the   output   file 
		    byte[] buf = new byte[1024];

		    int len;
		    while ((len = in.read(buf)) > 0)
		    {
			os.write(buf, 0, len);
		    }
		    os.close();
		    in.closeEntry();

		}
	    }

	} catch (IOException e)
	{
		String aaaString="";
		e.printStackTrace();
	}
	System.out.println("解压文件成功 ");
    }
    
}
