package printParse.word.templatePrint;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspose.words.Body;
import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.NodeType;
import com.aspose.words.RelativeHorizontalPosition;
import com.aspose.words.RelativeVerticalPosition;
import com.aspose.words.Row;
import com.aspose.words.Run;
import com.aspose.words.SaveFormat;
import com.aspose.words.Section;
import com.aspose.words.Table;
import com.aspose.words.Underline;
import com.aspose.words.WrapType;

public class WordPrint {
	/**
	 * 打印段落文字
	 * 
	 * @param request
	 * @param response
	 * @param list
	 */
	private void zdzmPrint(HttpServletRequest request,HttpServletResponse response, List list) {
		String MBURL = request.getSession().getServletContext()
				.getRealPath("template//xszdzm.doc");
		try {
			Document doc = new Document(MBURL);
			Section section = doc.getSections().get(0);
			Body body = section.getBody();
			Run run = new Run(doc, "姓名:");
			run.getFont().setSize(16);
			run.getFont().setName("宋体");
			run.getFont().setUnderline(Underline.SINGLE);// 下划线
			body.getParagraphs().get(4).getRuns().add(run);// 添加在第4段上

			run = new Run(doc, ",性别：");
			run.getFont().setSize(16);
			run.getFont().setName("宋体");
			body.getParagraphs().get(5).getRuns().add(run);// 添加在第5段上

			String filename = "北京城市学院学生在读证明申请表";
			byte[] by = filename.getBytes("gbk");
			filename = new String(by, "ISO-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename + ".doc");
			response.addHeader("Content-Length", "");
			response.setContentType("application/octet-stream");
			OutputStream out = response.getOutputStream();
			doc.save(out, SaveFormat.DOC);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 打印表格
	 * @param request
	 * @param response
	 * @param list
	 */
	private void qgzxPrint(HttpServletRequest request,HttpServletResponse response, List list) {
		String MBURL = request.getSession().getServletContext()
				.getRealPath("template//qgzxsq.doc");
		try {
			Document doc = new Document(MBURL);

			String filename = "勤工俭学申请表";
			
			Section section = doc.getSections().get(0);
			Body body = section.getBody();
			Run run = new Run(doc, "");
			
			Table t = (Table) doc.getChild(NodeType.TABLE, 0, true);
			Row rows = t.getRows().get(0);//第0行 
			Cell cell = rows.getCells().get(1);
			run = new Run(doc, "xxx");
			run.getFont().setSize(12);
			run.getFont().setName("宋体");
			cell.getParagraphs().get(0).getRuns().add(run);	
			
			cell = rows.getCells().get(3);
			run = new Run(doc, "ABC的");
			run.getFont().setSize(12);
			run.getFont().setName("宋体");
			cell.getParagraphs().get(0).getRuns().add(run);	
			 
			String imgPath = "xxx.jpg";
			DocumentBuilder builder = new DocumentBuilder(doc);
			
			try{
				builder.insertImage(imgPath, RelativeHorizontalPosition.MARGIN,315, RelativeVerticalPosition.MARGIN, 47, 100, 140, WrapType.SQUARE);
			}catch (FileNotFoundException e) {
				//	 this.writeJsMessage(response, "alert('找不到照片');");
			}
			rows = t.getRows().get(1); //第1行 
			cell = rows.getCells().get(1);
			run = new Run(doc, "班级1");
			run.getFont().setSize(12);
			run.getFont().setName("宋体");
			cell.getParagraphs().get(0).getRuns().add(run);
			
			cell = rows.getCells().get(3);
			run = new Run(doc, "1");
			run.getFont().setSize(12);
			run.getFont().setName("宋体");
			cell.getParagraphs().get(0).getRuns().add(run);
			
			rows = t.getRows().get(2); //第2行 
			cell = rows.getCells().get(1);
			run = new Run(doc, "汉");
			run.getFont().setSize(12);
			run.getFont().setName("宋体");
			cell.getParagraphs().get(0).getRuns().add(run);
			
			cell = rows.getCells().get(3);
			run = new Run(doc, "2016");
			run.getFont().setSize(12);
			run.getFont().setName("宋体");
			cell.getParagraphs().get(0).getRuns().add(run);
			
			byte[] by = filename.getBytes("gbk");
			filename = new String(by, "ISO-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename + ".doc");
			response.addHeader("Content-Length", "");
			response.setContentType("application/octet-stream");
			OutputStream out = response.getOutputStream();
			doc.save(out, SaveFormat.DOC);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
