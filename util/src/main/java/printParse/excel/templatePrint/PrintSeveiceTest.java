package printParse.excel.templatePrint;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PrintSeveiceTest {/*
	public static void main(String[] args) {
		//String webRoot = getServletContext().getRealPath("/")+ "temple"+ File.separatorChar + "xscjfxb.xls";
		String filePath = ""+ File.separatorChar + "src"+ File.separatorChar + "main"+ File.separatorChar + 
				"resources"+ File.separatorChar + "temple"
				+ File.separatorChar + "xscjfxb.xls";
		System.out.println(filePath);
		ExcelServicePrinter print = ExcelServicePrinter.openTemplate(filePath);
		//response.reset();// response.reset() 来清除首部的空白行。
		//response.setContentType("application/x-msdownload");
		//String filename = new String(title.getBytes("GBK"),"iso8859-1");
		//response.addHeader("Content-Disposition", "attachment;filename="+filename+".xls");// 表示下载文件的 header头	
		//output = response.getOutputStream();	
		String title="成绩分析报告";
		print.printCellText(2, 1, " 打印第2行" );// 打印第2行
		print.printCellText(3, 1, "打印第3行" );// 打印第3行
		int startrow = 7;
		int rol1 = 1;
		int row = 0;
		for (int i = 0; i <5; i = i + 2) {
			rol1 = 1;
			print.copyRange(6, 6, startrow );
			print.printCellText(startrow, rol1++,"学生学号"+i );
			print.printCellText(startrow, rol1++,
					"学生姓名"+i);
			
			startrow++;
			row++;
		}
		startrow = 7;
		row = startrow + row - 1;
		int m = 0;
		print.printCellText(row + 2, 1, 1 );// 成绩百分比
		 
		print.printCellText(row + 5 + m++, 2, 2 );// 应考人数

		print.printCellText(row + 5 + m++, 2, 3 );// 缺考人数

		print.printCellText(row + 5 + m++, 2, 4 );// 100-90分

		print.printCellText(row + 5 + m++, 2, 5 );// 89-80分

		print.printCellText(row + 5 + m++, 2, 6 );// 79-70分

		print.printCellText(row + 5 + m++, 2, 7 );// 69-60分

		print.printCellText(row + 5 + m++, 2, 8 );// 59分及其以下
		print.printCellText(row + 5 + m++, 2, 9 );// 平均分
		print.delRows(6, 6 );
		//	print.importExcelEnd(BBMC, out);
			//------------------------------------------- 
		//print.write();	
	}
	public ActionForward printCjfxbdtb(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		OutputStream output = null;   
		String filePath = this.servlet.getServletContext().getRealPath("template")
				+ File.separatorChar + "xscjfxb.xls";
		ExcelServicePrinter print = ExcelServicePrinter.openTemplate(filePath);
		String title="成绩分析报告";
		response.reset();// response.reset() 来清除首部的空白行。
		response.setContentType("application/x-msdownload");
		String filename = new String(title.getBytes("GBK"),"iso8859-1");
		response.addHeader("Content-Disposition", "attachment;filename="+filename+".xls");// 表示下载文件的 header头	
		output = response.getOutputStream();
		//------------------------
		print.printCellText(2, 1, " 打印第2行" );// 打印第2行
		print.printCellText(3, 1, "打印第3行" );// 打印第3行
		int startrow = 7;
		int rol1 = 1;
		int row = 0;
		int zps=1; 
		for (int i = 0; i <5; i = i + 2) {
			rol1 = 1;
			print.copyRange(6, 6, startrow );
			print.printCellText(startrow, rol1++,"学生学号"+i );
			print.printCellText(startrow, rol1++,
					"学生姓名"+i);
			
			startrow++;
			row++;
		}
		print.printPageBreak(row);//强制分页
		String path="http://" +request.getServerName() + ":" + request.getServerPort()+request.getContextPath() + "";
		print.insertPic(path, zps, 80, 75, startrow+1, 5);
		zps++;
		startrow = 7;
		row = startrow + row - 1;
		int m = 0;
		print.printCellText(row + 2, 1, 1 );// 成绩百分比
		 
		print.printCellText(row + 5 + m++, 2, 2 );// 应考人数

		print.printCellText(row + 5 + m++, 2, 3 );// 缺考人数

		print.printCellText(row + 5 + m++, 2, 4 );// 100-90分

		print.printCellText(row + 5 + m++, 2, 5 );// 89-80分

		print.printCellText(row + 5 + m++, 2, 6 );// 79-70分

		print.printCellText(row + 5 + m++, 2, 7 );// 69-60分

		print.printCellText(row + 5 + m++, 2, 8 );// 59分及其以下
		print.printCellText(row + 5 + m++, 2, 9 );// 平均分
		print.delRows(6, 6 );
		print.write(output);
		return null;
	}
	
*/}
