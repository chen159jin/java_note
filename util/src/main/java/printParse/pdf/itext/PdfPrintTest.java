package printParse.pdf.itext;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.qzdatasoft.comm.dao.util.BeanUtil;

import stringUtil.StringUtils;



public class PdfPrintTest {
	/**
	 * itext 打印表格 
	  * @param request
	 * @param response
	 * @param fieldTitle   标题行
	 * String[] fieldTitle = {"学年学期:4:1:100:t1.xnxq01id,院系名称:1:1:140:t1.dwmc,调课原因:8:1:100:dmmc,调课人次:2:1:100:t1.jsrs,调课学时:3:1:80:t1.zxs,总学时:6:1:80:t1.syzxs,节次:3:1:80:t1.zxs,调课率:5:1:80:t1.tkl,"};
	 * @param excelTitle	打印标题
	 * @param strFoot		打印落款
	 * @param rowSet		sql查询list数组集合
	 */
	public void PrintPDF(HttpServletRequest request, HttpServletResponse response, String[] fieldTitle,
			String excelTitle, String strFoot, List rowSet){

		// 得到列的列数
		int rows = rowSet.size()+3;
		// ---开始写标题
		OutputStream toClient = null;
		
		InputStream fis = null;
		String _filepath = "";
		String[] s_array_fieldtitle2 = null;
		s_array_fieldtitle2 = fieldTitle[fieldTitle.length - 1].split(",");
		try {
			//创建一个 itextpdf.text.Document对象的实例：
			Document document = new Document(PageSize.A4, 50, 50, 100, 50);
			//为该Document创建一个Writer实例：
			Rectangle pageRect = document.getPageSize();
			_filepath = System.getProperty("java.io.tmpdir") + File.separator + StringUtils.generateID("") + ".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(_filepath));// "C:\\"+title2.toString()+".pdf"
			// 创建汉字字体
			BaseFont bfSong = BaseFont.createFont(request.getRealPath("/template/font/") + File.separator
					+ "simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			Font fontSong = new Font(bfSong, 10, Font.BOLD);

			// 为页增加页头信息
			HeaderFooter header = new HeaderFooter(new Phrase(" 名称" + "--" + excelTitle + "打印日期："
					+ StringUtils.formatDate6(new Date()), fontSong), false);
			header.setBorder(2);
			header.setAlignment(Element.ALIGN_RIGHT);
			document.setHeader(header);

			// 为页增加页脚信息
			HeaderFooter footer3 = new HeaderFooter(new Phrase("第 ", fontSong), new Phrase(" 页", fontSong));
			footer3.setAlignment(Element.ALIGN_CENTER);
			footer3.setBorder(1);
			document.setFooter(footer3);

			// 打开文档
			document.open();

			// 构造表格  建立一个列的表格
			Table table = new Table(rows);
			// table.setDefaultVerticalAlignment(Element.ALIGN_MIDDLE);
			// 1.3版本
			table.setAlignment(Element.ALIGN_MIDDLE); // 2.1版本替换使用

			table.setBorder(Rectangle.NO_BORDER);
			table.setWidth((float) 10);
			table.setWidth(100);

			// 表头信息
			Cell cellmain = new Cell(new Phrase(excelTitle, new Font(bfSong, 10, Font.BOLD, new Color(0, 0, 255))));
			cellmain.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellmain.setColspan(rows);//合并rows列
			cellmain.setBorder(Rectangle.NO_BORDER);//设置边框
			cellmain.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));//设置边框颜色
			table.addCell(cellmain);
//---------------------------------
			
	        // 其中1为居中对齐，2为右对齐，3为左对齐
	        table.setAlignment(2);
	        // table.setPadding(0);
	        // table.setSpacing(0);
	  
	        // 读取图片(参数为gif、jpg、png格式的图片都可以)，设置图片大小
	        Image image = Image.getInstance("D:/test/1.jpg");
	        // Image img = Image.getInstance(new URL("http://xxx.com/logo.jpg)");
	        // 设置图片的绝对大小，宽和高
	        image.scaleAbsolute(50f, 50f);
	        // 设置图片居中显示
	        image.setAlignment(Image.MIDDLE);
	        // 创建单元格,并且将单元格内容设置为图片
	        Cell cell = new Cell(image);
	        // 设置单元格边框为0
	        cell.setBorder(0);
 //------
			
			
			
			// 分表头信息
			// 开始写表头，第一行
			if(fieldTitle.length > 1 && fieldTitle.length < 4) {//暂时只支持最多3行多表头
				for (int i = 0; i < fieldTitle.length; i++) {
					String[] s = fieldTitle[i].split(",");
					//合并内容行相同的单元格
					if(s.length > 1) {//字段大于1列时，合并才又意义
						int xt_index = 0; //记录相同字段第一个起始位子
						Map<Integer, Integer> mapCol  = new HashMap<Integer, Integer>();
						Map<Integer, Integer> mapRow  = new HashMap<Integer, Integer>();
						for (int j = 0; j < s.length; j++) {
							//横向合并条件判断
							if(j < s.length-1 && s[xt_index].split(":")[0].equals(s[j+1].split(":")[0])) {
								mapCol.put(xt_index, j+1-xt_index);
							} else {
								xt_index = j+1;
							}
							//纵向合并条件判断,只简单判断所有行标题都相同的情况
							if(fieldTitle.length == 2) {
								String[] str1 = fieldTitle[0].split(",");
								String[] str2 = fieldTitle[1].split(",");
								if(str1[j].split(":")[0].equals(str2[j].split(":")[0])) {
									mapRow.put(j, 2);
								}
							} else if (fieldTitle.length == 3) {
								String[] str1 = fieldTitle[0].split(",");
								String[] str2 = fieldTitle[1].split(",");
								String[] str3 = fieldTitle[2].split(",");
								if(str1[j].split(":")[0].equals(str2[j].split(":")[0]) && 
										str1[j].split(":")[0].equals(str3[j].split(":")[0])) {
									mapRow.put(j, 3);
								}
							}
						}
						for (int k = 0; k < s.length; k++) {
							Cell cellleft = new Cell(new Phrase(s[k].substring(0, s[k].indexOf(":")), 
									new Font(bfSong, 10, Font.ITALIC, new Color(0, 0, 255))));
							cellleft.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellleft.setVerticalAlignment(Element.ALIGN_MIDDLE);
							if(mapCol.get(k) != null && mapCol.keySet().contains(k)) {
								cellleft.setColspan(mapCol.get(k)+1);
								k += mapCol.get(k);
							} else {
								cellleft.setColspan(1);
							}
							if(mapRow.get(k) != null && mapRow.keySet().contains(k)) {
								cellleft.setRowspan(mapRow.get(k));
							}
							if(i > 0 && mapRow.get(k) != null && mapRow.keySet().contains(k)) {
								cellleft = null;
								continue;
							}
							table.addCell(cellleft);
						}
					}
				}
			} else {
				for (int i = 0; i < s_array_fieldtitle2.length; i++) {
					Cell cellleft = new Cell(new Phrase(
						"页面上的显示文本",//	s_array_fieldtitle2[i].substring(0,	s_array_fieldtitle2[i].indexOf(":")), 
							new Font(bfSong, 10, Font.ITALIC, new Color(0, 0, 255)))  );
					cellleft.setColspan(1);
					cellleft.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellleft.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellleft);
				}
			}

			// 写入行值
			String[] fields2 = s_array_fieldtitle2;
			Object oo = null;
			int count=0;
			int indexxh=0;
			String xy ="";
			Map<String, Integer> wz =new HashMap<String,Integer>();
			for (Iterator it = rowSet.iterator(); it.hasNext();) {
				oo = it.next();
				Object[] objs=(Object[]) oo;
				String xh = objs[9].toString();
				if("1".equals(xh)){
					count++;
				}else{
					wz.put(objs[1].toString(), count+1);
					count=0;
				}
			}
			for (Iterator it = rowSet.iterator(); it.hasNext();) {
				oo = it.next();
				Object[] objs=(Object[]) oo;
				for (int i = 1; i <= fields2.length; i++) {
					String[] fieldf = fields2[i - 1].split(":");
					Object object = BeanUtil.getProperty(oo, fieldf[1], fieldf[2]).toString();
					// System.out.println(object);
					Cell cellleft = new Cell(new Phrase(object.toString(), fontSong));
			
					if("学年学期".equals(fieldf[0])||"院系名称".equals(fieldf[0])){
						if(!xy.equals(objs[1].toString())){
							cellleft.setRowspan(wz.get(objs[1].toString()));//合并n行
							table.addCell(cellleft);//将合并的cell传入table中
							//cellleft.setColspan(2);//合并2列
							//table.addCell(cellleft);
						}
						if("院系名称".equals(fieldf[0]))
						xy=objs[1].toString();
					}else
					table.addCell(cellleft);
					//table.addCell(new Phrase(object.toString(), fontSong));
				}
			}
			
			// 将表格添加到文本中
			document.add(table);
			// 关闭文本，释放资源
			document.close();
			//pdf完成------------------------------
			//下载文件-------------------
			File file = new File(_filepath);
			String filename = excelTitle + ".pdf";
			String userAgent = request.getHeader("User-Agent");
			byte[] bytes = userAgent.contains("Windows") ? filename.getBytes() : filename.getBytes("UTF-8"); // name.getBytes("UTF-8")处理safari的乱码问题
			filename = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");

			fis = new BufferedInputStream(new FileInputStream(_filepath));
			toClient = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = fis.read(buffer)) != -1) {
				toClient.write(buffer, 0, i);
				toClient.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
			File file = new File(_filepath);
			if (file.exists()) {
				file.delete();
			}
		}
	
	}
}
