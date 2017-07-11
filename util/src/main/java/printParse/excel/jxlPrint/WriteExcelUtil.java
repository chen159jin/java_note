package printParse.excel.jxlPrint;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qzdatasoft.comm.dao.util.BeanUtil;

import jxl.HeaderFooter;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import stringUtil.StringUtils;
/**
 * <p>
 * Title: 导出Excel
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: 湖南强智科技发展有限公司
 * </p>
 * <p>
 * author: 陈文
 * </p>
 * <p>
 * Date: 2010-7-16
 * </p>
 * @version 1.0
 */
public class WriteExcelUtil
{
    private WritableWorkbook writableWorkbook;
    private WritableSheet writableSheet;
    private WritableFont writableFont;
    private WritableCellFormat writableCellFormat;
    private Label label;
    WritableCell wcell ;
    private File file;
    private String fileName = "";
    private String sheetName = "";
    private HttpServletResponse response;
    private String savePath = "";
    
    private void createDirtory(String strDirName) {
        File strDir = new File(strDirName);
        if (!strDir.isDirectory()) { //如果还没有建立目录当前用户的文件夹

            strDir.mkdir();
        }
    }
    
    public WriteExcelUtil(){
    	
    }
    
    public WriteExcelUtil(HttpServletResponse response,int row,int col,String name,String savepath) throws Exception {
        this.response = response;
        savePath = savepath;
		createDirtory(savePath);

        this.response.setContentType("application/vnd.ms-excel");

        fileName = fileName + StringUtils.generateID("");

        sheetName = name + fileName;

        file = new File(savePath,fileName + ".xls");
        writableWorkbook = Workbook.createWorkbook(file);
        writableSheet = writableWorkbook.createSheet(sheetName,0);
        
        
        writableFont = new jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED);
    	
        writableCellFormat = new WritableCellFormat(writableFont);
        
        writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE);
    	writableCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

    	
        label = new Label(row,col,name,writableCellFormat);
        writableSheet.addCell(label);
        writableFont=null;
        writableCellFormat=null;
        label=null;
    }
    
    public WriteExcelUtil(HttpServletResponse response,String name,String savepath,String fromFile) throws Exception {
        this.response = response;
        savePath = savepath;
		createDirtory(savePath);

        this.response.setContentType("application/vnd.ms-excel");

        fileName = fileName + StringUtils.generateID("");

        sheetName = name + fileName;

        file = new File(savePath,fileName + ".xls");
        copyFile(new File(fromFile), file);
        Workbook r=Workbook.getWorkbook(file);
        writableWorkbook = Workbook.createWorkbook(file,r);
        writableSheet = writableWorkbook.getSheet(0);
        
    }
    
    public void createSheet (String sheetName,int sheetIndex)
    {
	writableSheet = writableWorkbook.createSheet(sheetName+sheetIndex,sheetIndex);
    }
    
    public void copyCell(int startrow,int startcol,int endrow,int endcol,int torow,int tocol,int fromSheet,int toSheet) throws RowsExceededException, WriteException{
	WritableSheet ws=writableWorkbook.getSheet(fromSheet);
	int a=0;
	for (int row = startrow; row <= endrow; row++) {
	   
	    int b=0;
   		    for (int col = startcol; col <= endcol; col++) {
   		     WritableCell cell = ws.getWritableCell(col, row).copyTo(tocol+b, torow +a);
   		     writableSheet.addCell(cell);
   		 b++;
   		    }
   		 a++;
   	 }
	ws=null;

   }
    
    /**
     * 将startrow到endrow行, startcol到endcol列 复制到
     * torow行 tocol列 开始的表格中
     * @param startrow
     * @param startcol
     * @param endrow
     * @param endcol
     * @param torow
     * @param tocol
     * @throws RowsExceededException
     * @throws WriteException
     */
    public void copyCell(int startrow,int startcol,int endrow,int endcol,int torow,int tocol) throws RowsExceededException, WriteException{
    	 int a=0;
	for (int row = startrow; row <= endrow; row++) {
	   
	    int b=0;
    		    for (int col = startcol; col <= endcol; col++) {
    		     WritableCell cell = writableSheet.getWritableCell(col, row).copyTo(tocol+b, torow +a);
    		     writableSheet.addCell(cell);
    		 b++;
    		    }
    		 a++;
    	 }

    }
    
   public void WriteCell(int row,int col,String value) throws RowsExceededException, WriteException{
       label=new Label(col,row,value);
       writableSheet.addCell(label);
       label=null;

   }
   
   public void setCell(int row,int col,String value) throws RowsExceededException, WriteException{
       WritableCell wcell=writableSheet.getWritableCell(col, row);
       CellFormat cformat=wcell.getCellFormat();
       label=new Label(col,row,value,cformat);
       writableSheet.addCell(label);
       cformat=null;
       label=null;
       wcell=null;
      

   }
   
   public void deleteRows(int startrow,int endrow){
      for(int i=startrow;i<=endrow;i++){
	  writableSheet.removeRow(startrow);
      }
   }
   
   public void deleteSheet(int sheet){
       writableWorkbook.removeSheet(sheet);
   }
   /**
    * 分页
    * @param row
    */
   public void addRowPageBreak(int row){
       writableSheet.addRowPageBreak(row);
   }
    /**
     * 合并单元格
     * @param m  单元格的列号
     * @param n	单元格的行号
     * @param p 向下合并到列数
     * @param q 向下合并到行数
     * @throws Exception
     */
    public void mergeCells(int m,int n,int p,int q) throws Exception {
    	writableSheet.mergeCells(m, n, p, q);
    }
    
    public void startWrite(int row,int col,String[] name,boolean isHead,int alignType) throws Exception {
        if(isHead) {
            for (int i = 0; i < name.length; i++) {
            	writableFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
                writableCellFormat = new WritableCellFormat(writableFont);
                if(alignType == 0)
                    writableCellFormat.setAlignment(jxl.format.Alignment.LEFT);// 设置居左
                if(alignType == 1)
                    writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE);// 设置居中
                if(alignType == 2)
                    writableCellFormat.setAlignment(jxl.format.Alignment.RIGHT);// 设置居右
            	writableCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            	label = new Label(row + i, col, name[i], writableCellFormat);
                writableSheet.addCell(label);
                writableFont=null;
                writableCellFormat=null;
                label=null;
                //writableSheet.setColumnView(i, 20);
            }
        }else{
            for (int i = 0; i < name.length; i++) {
                writableCellFormat = new WritableCellFormat();
                if(alignType == 0)
                    writableCellFormat.setAlignment(jxl.format.Alignment.LEFT);
                if(alignType == 1)
                    writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                if(alignType == 2)
                    writableCellFormat.setAlignment(jxl.format.Alignment.RIGHT);
            	writableCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            	label = new Label(row + i, col, name[i], writableCellFormat);
                writableSheet.addCell(label);
                writableCellFormat=null;
                label=null;
            }
        }
    }
    /**
     * 在单元格中写入内容
     * @param row
     * @param col
     * @param obj  数组
     * @param alignType 设置居左    居中    居右
     * @param fontName	字体
     * @param fontSize	字号
     * @param fields  表头字段[学年学期:4:1:100:t1.xnxq01id, 院系名称:1:1:140:t1.dwmc, 调课原因:8:1:100:dmmc, 调课人次:2:1:100:t1.jsrs, 调课学时:3:1:80:t1.zxs, 总学时:6:1:80:t1.syzxs, 节次:3:1:80:t1.zxs, 调课率:5:1:80:t1.tkl]
     * @throws WriteException
     */
    public void writeCell(int row,int col,Object obj,int alignType,String fontName,int fontSize,String fields[]) throws WriteException{
	
	for (int i = 0; i <= fields.length-1; i++) {
	    String[] fieldf = fields[i].split(":");
	    String value = BeanUtil.getProperty(obj, fieldf[1],fieldf[2]).toString();
	    writableFont=new WritableFont(WritableFont.createFont(fontName),fontSize);
            writableCellFormat = new WritableCellFormat(writableFont);
            if(alignType == 0)
                writableCellFormat.setAlignment(jxl.format.Alignment.LEFT);
            if(alignType == 1)
                writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE);
            if(alignType == 2)
                writableCellFormat.setAlignment(jxl.format.Alignment.RIGHT);
        	writableCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        	label = new Label(row + i, col, value, writableCellFormat);
        	writableCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            writableSheet.addCell(label);
            writableFont=null;
            writableCellFormat=null;
            label=null;
	}
    }
  
    /**
     * 写入表头行
     * @param row		行
     * @param col		列
     * @param name 		显示内容
     * @param isHead	是否是表头
     * @param alignType	设置居左    居中    居右
     * @param fontName	字体
     * @param fontSize	字号
     * @throws Exception
     */
    public void startWriteComm(int row,int col,String[] name,boolean isHead,int alignType,String fontName,int fontSize) throws Exception {
        if(isHead) {
            for (int i = 0; i < name.length; i++) {
            	//添加带有字型Formatting的对象
        	writableFont=new WritableFont(WritableFont.createFont(fontName),fontSize,WritableFont.BOLD );
                writableCellFormat = new WritableCellFormat(writableFont);
                if(alignType == 0)
                    writableCellFormat.setAlignment(jxl.format.Alignment.LEFT);// 设置居左
                if(alignType == 1)
                    writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE);// 设置居中
                if(alignType == 2)
                    writableCellFormat.setAlignment(jxl.format.Alignment.RIGHT);// 设置居右
                //format.setAlignment(jxl.format.Alignment.CENTRE);//左右居中
            	writableCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);////上下居中
            	//LabelCell，NumberCell，DateCell分别表示字符、数值、日期类型的单元格
            	label = new Label(row + i, col, name[i], writableCellFormat);
                writableSheet.addCell(label);
                writableFont=null;
                writableCellFormat=null;
                label=null;
                //writableSheet.setColumnView(i, 20);
            }
        }else{
            writableSheet.setRowView(col, 800);//设置指定行的高度  
            for (int i = 0; i < name.length; i++) {
            	//添加带有字型Formatting的对象
        	writableFont=new WritableFont(WritableFont.createFont(fontName),fontSize);
                writableCellFormat = new WritableCellFormat(writableFont);
                writableCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
         //设置工作表指定列的宽度。这导致Excel调整整个列。如果已经指定的列视图相关联的信息,然后取而代之的是新的数据  
         //第一个参数是列，第二个参数是宽度  
                writableSheet.setColumnView(i, Integer.parseInt(name[i].split(":")[3])/4);
                
                writableCellFormat.setBackground(Colour.GRAY_25);
                if(alignType == 0)
                    writableCellFormat.setAlignment(jxl.format.Alignment.LEFT);
                if(alignType == 1)
                    writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                if(alignType == 2)
                    writableCellFormat.setAlignment(jxl.format.Alignment.RIGHT);
            	writableCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            	label = new Label(row + i, col, name[i].split(":")[0], writableCellFormat);
                writableSheet.addCell(label);
                writableFont=null;
                writableCellFormat=null;
                label=null;
            }
        }
    }
    
    /**
     * 添加图像
     * @param row
     * @param col
     * @param width
     * @param height
     * @param imgUrl
     * @throws Exception
     */
    public void writeImage(int row,int col,double width,double height,String imgUrl) throws Exception {
    	WritableImage ri = new WritableImage(row,col,width,height,new File(imgUrl));
    	writableSheet.addImage(ri);
    }
    /**
     * 设置页脚  只填一个值,其他两个为null 
     * @param left 
     * @param center
     * @param right
     */
    public void setBottomFooter(String left,String center,String right){
	HeaderFooter hf=new HeaderFooter();
	if(left!=null)
	hf.getLeft().append(left);
	if(center!=null)
	hf.getCentre().append(center);
	if(right!=null)
	hf.getRight().append(right);
	writableSheet.getSettings().setFooter(hf);
    }
    /**
     * 保存文件
     * @param fileName
     * @return
     */
    public String saveFile(String fileName)  {
	BufferedInputStream fis = null;
	BufferedOutputStream os = null;
    	try
	{
	    writableWorkbook.write();
	    //writableCellFormat.uninitialize();
	    writableWorkbook.close();
	    writableSheet=null;
	    writableWorkbook=null;
	    response.setContentType("application/vnd.ms-excel");
	    response.setHeader("Content-Disposition", "attachment;filename="
		    + URLEncoder.encode(fileName, "utf-8") + ".xls");
	    fis = new BufferedInputStream(
		    new FileInputStream(file));
	    os = new BufferedOutputStream(response.getOutputStream());
	    int byteRead;
	    byte[] buffer = new byte[4096];
	    int index=0;
	    while ((byteRead = fis.read(buffer, 0, 4096)) != -1)
	    {
		os.write(buffer);
		if(index!=0 && index%1000==0)
		    os.flush();
	    }
	    os.flush();


	} catch (Exception e)
	{
	    e.printStackTrace();
	} finally
	{
	    try
	    {
		if (fis != null)
		{
		    
		    fis.close();
		}
		os.close();
		file.delete();
		
	    }catch(Exception e)
	    {
		e.printStackTrace();
	    }
	}
    	return fileName + ".xls";
    }
    public String saveFile(HttpServletRequest request,String fileName)  {
    	BufferedInputStream fis = null;
    	BufferedOutputStream os = null;
        	try
    	{
    	    writableWorkbook.write();
    	    //writableCellFormat.uninitialize();
    	    writableWorkbook.close();
    	    writableSheet=null;
    	    writableWorkbook=null;
    	    
    	    String userAgent = request.getHeader("User-Agent");   
    	     byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8"); // name.getBytes("UTF-8")处理safari的乱码问题   
    	     fileName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码    
    	    response.setContentType("application/vnd.ms-excel");
    	    response.setHeader("Content-Disposition", "attachment;filename="
    		    + fileName + ".xls");
    	    fis = new BufferedInputStream(
    		    new FileInputStream(file));
    	    os = new BufferedOutputStream(response.getOutputStream());
    	    int byteRead;
    	    byte[] buffer = new byte[4096];
    	    int index=0;
    	    while ((byteRead = fis.read(buffer, 0, 4096)) != -1)
    	    {
    		os.write(buffer);
    		if(index!=0 && index%1000==0)
    		    os.flush();
    	    }
    	    os.flush();


    	} catch (Exception e)
    	{
    	    e.printStackTrace();
    	} finally
    	{
    	    try
    	    {
    		if (fis != null)
    		{
    		    
    		    fis.close();
    		}
    		os.close();
    		file.delete();
    		
    	    }catch(Exception e)
    	    {
    		e.printStackTrace();
    	    }
    	}
        	return fileName + ".xls";
        }
        
   
 public void mergeCells(int col, int row) throws Exception {
		int start = row, end = row;
		String val = writableSheet.getCell(col, row).getContents();
		//writableCellFormat = new WritableCellFormat();
		//writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		//writableCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

		for (int i = row + 1; i < writableSheet.getRows(); i++) {
			String cur = writableSheet.getCell(col, i).getContents();
			if (val.equals(cur)) {
				end = i;
				continue;
			}
			if (start != end && !"".equals(val)) {
				writableSheet.mergeCells(col, start, col, end);
				start = end = i;
				val = cur;
			}
		}
		if (start != end && !"".equals(val)) {
			writableSheet.mergeCells(col, start, col, end);
		}
	}
    
    
    public static void copyFile(File sourceFile,File targetFile)  
    throws IOException{ 
            FileInputStream input = new FileInputStream(sourceFile); 
            BufferedInputStream inBuff=new BufferedInputStream(input); 
            FileOutputStream output = new FileOutputStream(targetFile); 
            BufferedOutputStream outBuff=new BufferedOutputStream(output); 
            byte[] b = new byte[1024 * 5]; 
            int len; 
            while ((len =inBuff.read(b)) != -1) { 
                outBuff.write(b, 0, len); 
            } 
            outBuff.flush(); 
            inBuff.close(); 
            outBuff.close(); 
            output.close(); 
            input.close(); 
        } 
    
    public static void main(String[] args)
    {
	try
	{
	    copyFile(new File("E:\\myworkspace1\\Hnkjdx\\WebRoot\\template\\printXsjbxxhd.xls"), new File("d:\\aaaaaaa.xls"));
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
}
