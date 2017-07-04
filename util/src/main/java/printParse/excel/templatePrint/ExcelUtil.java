package printParse.excel.templatePrint;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @author 李君
 * 
 */
public class ExcelUtil {

	public static void writeTitleCell(WritableSheet sheet, String value, int col, int row) throws WriteException {
		WritableCellFormat titleFomat = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"), 18,
				WritableFont.BOLD, false));
		titleFomat.setAlignment(Alignment.CENTRE);
		titleFomat.setVerticalAlignment(VerticalAlignment.CENTRE);
		sheet.addCell(new Label(col, row, value, titleFomat));
	}
	
	public static void writeHeaderCell(WritableSheet sheet, String value, int col, int row) throws WriteException {
		WritableCellFormat HeaderFomat = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.BOLD, false));
		HeaderFomat.setBorder(Border.ALL, BorderLineStyle.THIN);
		HeaderFomat.setAlignment(Alignment.CENTRE);
		HeaderFomat.setVerticalAlignment(VerticalAlignment.CENTRE);
		HeaderFomat.setWrap(true);
		sheet.addCell(new Label(col, row, value, HeaderFomat));
	}
	
	public static void writeBodyCellBorder(WritableSheet sheet, String value, int col, int row, Border border)
			throws WriteException {
		writeBodyCellBorder(sheet, value, col, row, border, BorderLineStyle.THIN, Alignment.CENTRE, Colour.BLACK);
	}
	
	public static void writeBodyCellBorder(WritableSheet sheet, String value, int col, int row, Border border, BorderLineStyle style, Alignment alignment, Colour color)
			throws WriteException {
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD, false);
		bodyFont.setColour(color);
		WritableCellFormat bodyformat = new WritableCellFormat(bodyFont);
		bodyformat.setBorder(border, style);
		bodyformat.setAlignment(alignment);
		bodyformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		bodyformat.setWrap(true);
		sheet.addCell(new Label(col, row, value, bodyformat));
	}
	
	public static void writeBodyCellNotBorder(WritableSheet sheet, String value, int col, int row)
			throws WriteException {
		writeBodyCellNotBorder(sheet, value, col, row, Alignment.LEFT);
	}

	public static void writeBodyCellNotBorder(WritableSheet sheet, String value, int col, int row, Alignment alignment)
			throws WriteException {
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD, false);
		WritableCellFormat bodyformat = new WritableCellFormat(bodyFont);
		bodyformat.setAlignment(alignment);
		bodyformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		bodyformat.setWrap(true);
		sheet.addCell(new Label(col, row, value, bodyformat));
	}

	public static void writeBodyCellNotBorder(WritableSheet sheet, int value, int col, int row) throws WriteException {
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD, false);
		WritableCellFormat bodyformat = new WritableCellFormat(bodyFont);
		bodyformat.setAlignment(Alignment.LEFT);
		bodyformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		sheet.addCell(new jxl.write.Number(col, row, value, bodyformat));
	}

	public static void writeBodyCell(WritableSheet sheet, String value, int col, int row) throws WriteException {
		writeBodyCell(sheet, value, col, row, Alignment.LEFT);
	}

	public static void writeBodyCell(WritableSheet sheet, double value, int col, int row) throws WriteException {
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD, false);
		WritableCellFormat bodyformat = new WritableCellFormat(bodyFont);
		bodyformat.setBorder(Border.ALL, BorderLineStyle.THIN);
		bodyformat.setAlignment(Alignment.RIGHT);
		bodyformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		sheet.addCell(new jxl.write.Number(col, row, value, bodyformat));
	}

	public static void writeBodyCell(WritableSheet sheet, Date value, int col, int row) throws WriteException {
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD, false);
		WritableCellFormat bodyformat = new WritableCellFormat(bodyFont);
		bodyformat.setBorder(Border.ALL, BorderLineStyle.THIN);
		bodyformat.setAlignment(Alignment.LEFT);
		bodyformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		bodyformat.setWrap(true);
		sheet.addCell(new DateTime(col, row, value, bodyformat));
	}

	public static void writeBodyCell(WritableSheet sheet, String value, int col, int row, Alignment alignment)
			throws WriteException {
		writeBodyCell(sheet, value, col, row, alignment, Colour.BLACK);
	}

	public static void writeBodyCell(WritableSheet sheet, String value, int col, int row, Alignment alignment, Colour color)
			throws WriteException {
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD, false);
		bodyFont.setColour(color);
		WritableCellFormat bodyformat = new WritableCellFormat(bodyFont);
		bodyformat.setBorder(Border.ALL, BorderLineStyle.THIN);
		bodyformat.setAlignment(alignment);
		bodyformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		bodyformat.setWrap(true);
		sheet.addCell(new Label(col, row, value, bodyformat));
	}

	public static void writeBodyCell(WritableSheet sheet, int value, int col, int row, Alignment alignment)
			throws WriteException {
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD, false);
		WritableCellFormat bodyformat = new WritableCellFormat(bodyFont);
		bodyformat.setBorder(Border.ALL, BorderLineStyle.THIN);
		bodyformat.setAlignment(alignment);
		bodyformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		sheet.addCell(new jxl.write.Number(col, row, value, bodyformat));
	}

	public static void writeBodyCellNotFormat(WritableSheet sheet, String value, int col, int row)
	throws WriteException {
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD, false);
		WritableCellFormat bodyformat = new WritableCellFormat(bodyFont);
		bodyformat.setAlignment(Alignment.LEFT);
		bodyformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		sheet.addCell(new Label(col, row, value));
}
	public static void writeBodyCell(WritableSheet sheet, double value, int col, int row, Alignment alignment)
			throws WriteException {
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD, false);
		WritableCellFormat bodyformat = new WritableCellFormat(bodyFont);
		bodyformat.setBorder(Border.ALL, BorderLineStyle.THIN);
		bodyformat.setAlignment(alignment);
		bodyformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		sheet.addCell(new jxl.write.Number(col, row, value, bodyformat));
	}

	public static void writeBodyCell(WritableSheet sheet, Date value, int col, int row, Alignment alignment)
			throws WriteException {
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD, false);
		WritableCellFormat bodyformat = new WritableCellFormat(bodyFont);
		bodyformat.setBorder(Border.ALL, BorderLineStyle.THIN);
		bodyformat.setAlignment(alignment);
		bodyformat.setVerticalAlignment(VerticalAlignment.CENTRE);
		bodyformat.setWrap(true);
		sheet.addCell(new DateTime(col, row, value, bodyformat));
	}

	/**
	 * 删除行
	 */
	public static void delRow(WritableSheet sheet, int startRow, int endRow) {
		int num = endRow - startRow;
		if (num == 0) {
			sheet.removeRow(startRow);
		} else {
			int count = 0;
			while (count <= num) {
				sheet.removeRow(startRow);
				count++;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String path = "C:\\cjlr_import.xls";
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream("C:\\cjlr_import_temp.xls");
		WritableWorkbook workbook = Workbook.createWorkbook(fos, Workbook.getWorkbook(file));
		WritableSheet sheet = workbook.getSheet(0);
		delRow(sheet, 48, sheet.getRows() - 1);
		workbook.write();
		workbook.close();
		fos.close();
	}
}
