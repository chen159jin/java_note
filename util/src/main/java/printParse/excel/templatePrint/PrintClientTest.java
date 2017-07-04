package printParse.excel.templatePrint;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrintClientTest {/*
	public ActionForward printCjfxbdtb(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			// 定义输出对象
			PrintWriter out = response.getWriter();
			// out.print("<script
			// language='javaScript'>window.parent.Form1.action='';</script>");
			// 模板名称
			String BBMC = "成绩分析表";
			// 获取模板URL
			String MBURL = "http://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath()
					+ "/template/cjfxddtb.xls";
			// 启动导出Excel文件
			ImportExcel.importExcelStart(MBURL, out);
			// 设置表头
			ImportExcel.printCellText(1, 1, "xxxx学校"
					+ "课程成绩分析表", out);

			int startrow = 7;
			int rol1 = 1;
			int row = 0;
			for (int i = 0; i < 5; i = i + 2) {
				rol1 = 1;
				ImportExcel.printCellText(startrow, rol1++, "", out);
				ImportExcel.printCellText(startrow, rol1++, "", out);

				startrow++;
				row++;
			}
			startrow = 7;
			row = startrow + row - 1;
			int m = 0;
			ImportExcel.printCellText(row + 2, 1, "1", out);// 成绩百分比

			ImportExcel.printCellText(row + 5 + m++, 2,"2", out);// 应考人数

			ImportExcel.printCellText(row + 5 + m, 2, 3+ "", out);// 缺考人数
			ImportExcel.printCellText(row + 5 + m++, 3, 4 + "%", out);// 缺考百分比

			ImportExcel.printCellText(row + 5 + m, 2, 5+ "", out);// 100-90分
			ImportExcel.printCellText(row + 5 + m++, 3, 6 + "%", out);// 100-90分百分比

			ImportExcel.printCellText(row + 5 + m, 2, 7+ "", out);// 89-80分
			ImportExcel.printCellText(row + 5 + m++, 3, 8 + "%", out);// 89-80分百分比

			ImportExcel.printCellText(row + 5 + m, 2, 9+ "", out);// 79-70分
			ImportExcel.printCellText(row + 5 + m++, 3, 10 + "%", out);// 79-70分百分比

			ImportExcel.printCellText(row + 5 + m, 2, 11+ "", out);// 69-60分
			ImportExcel.printCellText(row + 5 + m++, 3, 12 + "%", out);// 69-60分百分比

			ImportExcel.printCellText(row + 5 + m, 2, 13+ "", out);// 59分及其以下
			ImportExcel.printCellText(row + 5 + m++, 3, 14 + "%", out);// 59分及其以下百分比
			ImportExcel.printCellText(row + 5 + m, 2, 15+ "", out);// 平均分
			ImportExcel.delRows(6, 6, out);
			ImportExcel.importExcelEnd(BBMC, out);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
*/}
