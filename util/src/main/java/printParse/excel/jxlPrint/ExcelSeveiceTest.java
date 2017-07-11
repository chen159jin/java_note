package printParse.excel.jxlPrint;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ExcelSeveiceTest {
	/**
	 * jxl 打印表格
	 * @param request
	 * @param response
	 * @param fieldTitle   标题行
	 * String[] fieldTitle = {"学年学期:4:1:100:t1.xnxq01id,院系名称:1:1:140:t1.dwmc,调课原因:8:1:100:dmmc,调课人次:2:1:100:t1.jsrs,调课学时:3:1:80:t1.zxs,总学时:6:1:80:t1.syzxs,节次:3:1:80:t1.zxs,调课率:5:1:80:t1.tkl,"};
	 * @param excelTitle	打印标题
	 * @param strFoot		打印落款
	 * @param rowSet		sql查询list数组集合
	 */
	private void printManyTitleExl(HttpServletRequest request, HttpServletResponse response, String[] fieldTitle,
			String excelTitle, String strFoot, List rowSet) {
		String path = request.getRealPath("/ExcelPath");
		try {
			//表字段信息
			String[] fieldInfo = fieldTitle[fieldTitle.length-1].split(",");
			
			String[] titles1 = new String[1];
			titles1[0] = excelTitle;
			WriteExcelUtil weu = new WriteExcelUtil(response, 2, 0, "", path);
			weu.setBottomFooter(null, null, "&10" + strFoot + "&10第&P页 / 共&N页");
			// 格式
			String titleFontName = "宋体";
			int titleFontSize = 18;
			String filedFontName = "宋体";
			int filedFontSize =10;
			String cellFontName = "宋体";
			int cellFontSize = 10;
			weu.mergeCells(0, 0, fieldInfo.length - 1, 1);//从0列合并到fieldInfo.length - 1列,从0行合并的1行
			weu.startWriteComm(0, 0, titles1, true, 1, titleFontName, titleFontSize);
			weu.startWriteComm(0, 2, fieldInfo, false, 1, filedFontName, filedFontSize);
			int index = 2 + fieldTitle.length;
			int count = 0;
			for (Iterator it = rowSet.iterator(); it.hasNext();) {
				weu.writeCell(0, index, it.next(), 0, cellFontName, cellFontSize, fieldInfo);
				index++;
			}
			for (int i = 0; i < rowSet.size(); i++) {
				Object[] obj=(Object[]) rowSet.get(i);
				String xh = obj[9].toString();
				if("1".equals(xh)){
					count++;
				}else{
					weu.mergeCells(0, 3+i-count,0, 3+i);//单元格的列号,单元格的行号,向下合并到列数,向下合并到行数
					weu.mergeCells(1, 3+i-count,1, 3+i);//单元格的列号,单元格的行号,向下合并到列数,向下合并到行数
					count=0;
				}
			}
			
			weu.saveFile(request, excelTitle);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
