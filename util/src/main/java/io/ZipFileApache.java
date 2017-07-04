package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import org.apache.tools.zip.ZipEntry;  
import org.apache.tools.zip.ZipOutputStream; 

import javax.servlet.http.HttpServletResponse;
/**
 * 处理 中文压缩乱码问题的
 * 与 ZipFile.java 相同 但由于其名称和Apache的类名冲突
 * @author jin
 *
 */ 
public class ZipFileApache
{
    private HttpServletResponse response;
    private File file;
    /**  
     * 多个文件压缩处理  
     *   
     * @param files  
     *            所要打包的文件列表  
     * @param outputStream  
     */  
    public void zipFile(List files, ZipOutputStream outputStream) {   
        int size = files.size();   
        for (int i = 0; i < size; i++) {   
            File file = (File) files.get(i);   
            zipFilepath(file, outputStream);   
        }   
    }   
  
    /**  
     * 根据输入的文件与流对文件进行打包  
     */  
    public void zipFilepath(File inputFile, ZipOutputStream ouputStream) {   
        try {   
        	ouputStream.setEncoding("gbk");
            if (inputFile.exists()) {   
                if (inputFile.isFile()) {   
                    FileInputStream in = new FileInputStream(inputFile);   
                    BufferedInputStream bins = new BufferedInputStream(in, 512);   
                    ZipEntry entry = new ZipEntry(inputFile.getName());   
                    ouputStream.putNextEntry(entry);   
                    // 向压缩文件中输出数据   
                    int nNumber;   
                    byte[] buffer = new byte[512];   
                    while ((nNumber = bins.read(buffer)) != -1) {   
                        ouputStream.write(buffer, 0, nNumber);   
                        // 关闭创建的流对象   
                    }   
                    bins.close();   
                    in.close();   
                } else {   
                    try {   
                        File[] files = inputFile.listFiles();   
                        for (int i = 0; i < files.length; i++) {   
                            zipFilepath(files[i], ouputStream);   
                        }   
                    } catch (Exception e) {   
                        e.printStackTrace();   
                    }   
                }   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }  
    
    /**
     * 下载文件
     * @param filePath --文件完整路径
     * @param response --HttpServletResponse对象
     */
    public  void downloadFile(String filePath,
	    javax.servlet.http.HttpServletResponse response)
    {

	String fileName = ""; //文件名，输出到用户的下载对话框
	//    从文件完整路径中提取文件名，并进行编码转换，防止不能正确显示中文名
	try
	{
	    if (filePath.lastIndexOf("/") > 0)
	    {
		fileName = new String(filePath.substring(
			filePath.lastIndexOf("/") + 1, filePath.length())
			.getBytes("GB2312"), "ISO8859_1");
	    } else if (filePath.lastIndexOf("\\") > 0)
	    {
		fileName = new String(filePath.substring(
			filePath.lastIndexOf("\\") + 1, filePath.length())
			.getBytes("GB2312"), "ISO8859_1");
	    }

	} catch (Exception e)
	{
	}
	//    打开指定文件的流信息
	FileInputStream fs = null;
	try
	{
	    fs = new FileInputStream(new File(filePath));
	} catch (FileNotFoundException e)
	{
	    e.printStackTrace();
	    return;
	}
	//    设置响应头和保存文件名 
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=\""
		+ fileName + "\"");
	//    写出流信息
	int b = 0;
	try
	{
	    PrintWriter out = response.getWriter();
	    while ((b = fs.read()) != -1)
	    {
		out.write(b);
	    }
	    fs.close();
	    out.close();
	    System.out.println("文件下载完毕.");
	} catch (Exception e)
	{
	    System.out.println("下载文件失败!");
	}
    }

}
