package printParse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory; */

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import io.FileIO;



/**
 * Excel 转换PDF服务
 * 
 * @author Administrator
 * 
 */
public class ExcelToPdfServlet extends HttpServlet {
  //  private static final Logger log = LoggerFactory.getLogger(ExcelToPdfServlet.class);

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExcelToPdfServlet() {
	super();
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    @SuppressWarnings("unchecked")
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	    IOException {
	// TODO Auto-generated method stub
	doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	OutputStream toClient = null;
	InputStream fis = null;
	String filepath = ""; 
	String source = "";
	try {
	    String isserver = request.getParameter("isserver");
	    if(request.getParameter("url")==null){
		//log.debug("url is null");
		return;
	    } 
	    String url = URLDecoder.decode(request.getParameter("url"),"UTF-8");
	    String title = request.getParameter("title")==null ? "" : URLDecoder.decode(request.getParameter("title"),"UTF-8");
	    
	    if("1".equals(isserver)){//远程服务器打印
		 source = System.getProperty("java.io.tmpdir")+File.separator + UUID.randomUUID()+".pdf";
		 FileIO.writeFile(this.getURLContent(url, "GBK"), source); 
	    }else{
		 source = FileIO.getRealFilePath(request.getSession().getServletContext(),"/")+ url;
	    }	   
	    
	    String filetitle =   UUID.randomUUID()+".pdf";
	    String filename = "toPDF" + File.separator + filetitle;
	    filepath = FileIO.getRealFilePath(request.getSession().getServletContext(), "/") + filename;
	     
	    excel2pdf(source, filepath);
	    
	    File file = new File(filepath);
	    String userAgent = request.getHeader("User-Agent");
	    if(title==null){
		title = filetitle;
	    }else{
		title += ".pdf";
	    }
	    
	    byte[] bytes = userAgent.contains("MSIE") ? title.getBytes() : title.getBytes("UTF-8"); 
	    title = new String(bytes, "ISO-8859-1"); 
	    response.reset();
	    response.addHeader("Content-Disposition", "attachment;filename=" + title);
	    response.addHeader("Content-Length", "" + file.length());
	    response.setContentType("application/octet-stream");

	    fis = new BufferedInputStream(new FileInputStream(filepath));
	    toClient = new BufferedOutputStream(response.getOutputStream());
	    byte[] buffer = new byte[1024];
	    int i = -1;
	    while ((i = fis.read(buffer)) != -1) {
		toClient.write(buffer, 0, i);
		toClient.flush();
	    }

	} catch (Exception e) {
	//    log.error(e.getMessage(),e);
	    if (toClient != null) {
		    try {
			toClient.close();
		    } catch (IOException e1) {
			e1.printStackTrace();
		    }
		}
		if (fis != null) {
		    try {
			fis.close();
		    } catch (IOException e1) {
			e1.printStackTrace();
		    }
		}
		File file = new File(filepath);
		if (file.exists()) {
		    file.delete();
		}
		if(source!=null){
		    File tmpFile = new File(source);
		    if (tmpFile.exists()) {
			    tmpFile.delete();
		    }
		}
	}
    } 
    
    public static String getURLContent(String url, String encoding) {
	StringBuffer content = new StringBuffer();
	InputStream in = null;
	InputStreamReader theHTML = null;
	try {
	    // 新建URL对象
	    URL u = new URL(url);
	    in = new BufferedInputStream(u.openStream());
	    theHTML = new InputStreamReader(in, encoding);
	    int c;
	    while ((c = theHTML.read()) != -1) {
		content.append((char) c);
	    } 
	} catch (Exception e) {
	    e.printStackTrace();
	}finally{
	    try {
		if (in != null) {
		    in.close();
		}
		if (theHTML != null) {
		    theHTML.close();
		}
	    } catch (Exception e2) {

	    }
	}
	
	return content.toString();
    }
    public static void excel2pdf(String source, String target) {
	ActiveXComponent app = null;
	try {
	    app = new ActiveXComponent("Excel.Application"); // 启动excel(Excel.Application)
	    File targetFile = new File(target);
	    if(targetFile.exists()){
		targetFile.delete();
	    }
	    app.setProperty("Visible", new Variant(false));
	    Dispatch workbooks = app.getProperty("Workbooks").toDispatch(); 
	    Dispatch workbook = Dispatch.invoke(workbooks, "Open", Dispatch.Method,
		    new Object[] { source, new Variant(false), new Variant(false) }, new int[3])
		    .toDispatch();
	    Dispatch.invoke(workbook, "SaveAs", Dispatch.Method, new Object[] { target,
		    new Variant(57), new Variant(false), new Variant(57), new Variant(57),
		    new Variant(false), new Variant(true), new Variant(57), new Variant(true),
		    new Variant(true), new Variant(true) }, new int[1]);
	    Variant f = new Variant(false);
	    Dispatch.call(workbook, "Close", f);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (app != null) {
		app.invoke("Quit", new Variant[] {});
	    }
	}
    }
    
    public static void main(String [] args){
//	String source = "D:\\work_hnkd\\njwwdb\\WebRoot\\toPDF\\3d283aea-7f3e-472d-9df8-4352d935dac3.xls";
//	String target = "D:\\work_hnkd\\njwwdb\\WebRoot\\toPDF\\ceec1459-7f2d-4d83-b58e-1b56a347260d.pdf";
	String source = "D:\\kb.xls";
	String target = "D:\\kb.pdf";
	System.out.println(target);
	excel2pdf(source, target);
	
	File targetFile = new File(target);
	System.out.println(targetFile.length());
	
	//System.out.println(getURLContent("http://www.baidu.com","UTF-8"));
    }
    
    

}