
package printParse.excel.templatePrint;

import java.io.PrintWriter;

public class ImportExcel
{

    public ImportExcel()
    {
    }   

    public static void importExcelStart(String MBURL, PrintWriter writer)
    {
        writer.write("<script language='vbscript'>");
        writer.write("Function GetColName(iCol) :");
        writer.write("Dim cCol, cOldAsc, itmpCol, iOldAsc, i :");
        writer.write("cCol = \"\" :");
        writer.write("cOldAsc = \"\" :");
        writer.write("iOldAsc = 0 :");
        writer.write("itmpCol = 0 :");
        writer.write("for i = 1 To iCol :");
        writer.write("itmpCol = itmpCol + 1 :");
        writer.write("If itmpCol = 27 Then :");
        writer.write("iOldAsc = iOldAsc + 1 :");
        writer.write("cOldAsc = Chr(iOldAsc + 64) :");
        writer.write("itmpCol = 1 :");
        writer.write("End If :");
        writer.write("If i = iCol Then :");
        writer.write("cCol = cOldAsc & Chr(itmpCol + 64) :");
        writer.write("end if :");
        writer.write("next :");
        writer.write("GetColName = cCol :");
        writer.write("End Function :");
        writer.write("</script>");
        writer.write("<SCRIPT ID=clientEventHandlersJS LANGUAGE=javascript>\n");
        writer.write("function ImportExcel()\n");
        writer.write("{\n");
        openExcel(MBURL, writer);
    }

    private static void openExcel(String MBURL, PrintWriter writer)
    {
        writer.write("try{");
        writer.write("     var objdoc = new ActiveXObject(\"Excel.Application\");");
        writer.write("}");
        writer.write("catch(e)");
        writer.write("{");
        writer.write("     alert('\u53D1\u751F\u9519\u8BEF\uFF1A\\n\\n\u8BF7\u786E\u8BA4\u60A8\u7684\u7535\u8111\u662F\u5426\u5B89\u88C5\u4E86Excel,\u5E76\u4E14\u60A8\u7684\u6D4F\u89C8\u5668\u662F\u5426\u5141\u8BB8\u8FD0\u884CExcel!\\n\u5177\u4F53\u64CD\u4F5C\u8BF7\u67E5\u9605\u5E2E\u52A9.');");
        writer.write("     window.close();");
        writer.write("     return;");
        writer.write("}");
        writer.write("try{");
        writer.write("    objdoc.Workbooks.Open(\"" + MBURL + "\");");
        writer.write("}");
        writer.write("catch(e)");
        writer.write("{");
        writer.write("    alert('\u53D1\u751F\u9519\u8BEF\uFF1A\u4E0D\u80FD\u6253\u5F00\u6A21\u677F\u6587\u4EF6\uFF0C\u53EF\u80FD\u662F\u7531\u4E8E\uFF29\uFF25\u5B89\u5168\u8BBE\u7F6E\u963B\u6B62\uFF0C\u8BF7\u8C03\u6574\uFF29\uFF25\u5B89\u5168\u8BBE\u7F6E\uFF0C\u7136\u540E\u91CD\u8BD5\u6253\u5370!');");
        writer.write("    window.close();");
        writer.write("    return;");
        writer.write("}");
        writer.write("objdoc.CommandBars(\"Web\").Visible =false;");
        writer.write("objdoc.CommandBars(\"Visual Basic\").Visible=false;");
        writer.write("objdoc.CommandBars(\"Standard\").Visible=true;");
        writer.write("objdoc.CommandBars(\"Formatting\").Visible=true;");
        writer.write("objdoc.Visible=true;");
        writer.write("var objdocSheets = objdoc.ActiveWorkbook;");
    }

    public static void printCellText(int row, int col, String inputString, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Cells(" + row + "," + col + ").Value = \"" + inputString.replaceAll("\"", "\\\"").replaceAll("\n", "\"+\"\\n\"+\"").replaceAll("\r", "\"+\"\\r\"+\"") + "\";" + "\n");
    }
    /*
     * 设置row行  高度hight
     * 作者：bucce
     */
    public static void printSetCellHight(int row, int hight, String inputString, PrintWriter writer)
    {
    	writer.write("objdocSheets.Sheets(\"Sheet1\").Rows(\"" + row + ":" + row+1 + "\").RowHeight="+getHight(hight)+";");
    } 
    /*
     * 设置row行  高度hight
     * 作者：libiao 20130220
     */
    public static void setRowHight(int row, int hight, PrintWriter writer)
    {
    	writer.write("objdocSheets.Sheets(\"Sheet1\").Rows(\"" + row + ":" + row+1 + "\").RowHeight="+hight+";");
    } 
    public static void printPageBreak(int startRow, int endRow, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Rows(\"" + startRow + ":" + endRow + "\").Select();");
        writer.write("try{");
        writer.write("objdoc.ActiveWindow.SelectedSheets.HPageBreaks.Add(objdoc.ActiveWindow.ActiveCell);");
        writer.write("}");
        writer.write("catch(e){");
        writer.write("}");
    }

    public static void copyRange(int ScrStartrow, int ScrEndrow, int Startrow, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Rows(\"" + ScrStartrow + ":" + ScrEndrow + "\").Copy();");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Rows(\"" + Startrow + ":" + Startrow + "\").Insert();");
    }

    public static void delRows(int startRow, int endRow, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Rows(\"" + startRow + ":" + endRow + "\").Delete();");
    }

    public static void delColumns(int startCol, int endCol, PrintWriter writer)
    {
    	String excelColumns[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ"};
        writer.write("objdocSheets.Sheets(\"Sheet1\").Columns(\"" + excelColumns[startCol-1] + ":" + excelColumns[endCol-1] + "\").Delete();");
    }
    
    public static void setPrintTitleRows(int StartRow, int EndRow, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").PageSetup.PrintTitleRows = \"$" + StartRow + ":$" + EndRow + "\"; ");
    }

    public static void setRangeFont(String StartCell, String EndCell, String FontName, int FontSize, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Range(\"" + StartCell + ":" + EndCell + "\").Font.name =  \"" + FontName + "\" ; ");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Range(\"" + StartCell + ":" + EndCell + "\").Font.size =  " + FontSize + " ; ");
    }
    public static void setRangeFont(String StartCell, String EndCell, String FontName, int FontSize,String weight, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Range(\"" + StartCell + ":" + EndCell + "\").Font.name =  \"" + FontName + "\" ; ");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Range(\"" + StartCell + ":" + EndCell + "\").Font.size =  " + FontSize + " ; ");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Range(\"" + StartCell + ":" + EndCell + "\").Font.bold = true;");
    }

    public static void printChart(int ChartType, String SrcRange, int DataChartType, int ChartIndex, int ChartCellRow, int ChartCellCol, int ChartWidth, int ChartHeight, 
            PrintWriter writer)
    {
        writer.write("objdocSheets.Charts.Add();");
        writer.write("objdocSheets.ActiveChart.ChartType = " + ChartType + ";");
        writer.write("objdocSheets.ActiveChart.SetSourceData(objdocSheets.Sheets(\"Sheet1\").Range(\"" + SrcRange + "\")," + DataChartType + ");");
        writer.write("objdocSheets.ActiveChart.Location(2,\"Sheet1\");");
        writer.write("objdocSheets.ActiveChart.ApplyDataLabels(2,false);");
        writer.write("objdocSheets.ActiveChart.HasLegend = false;");
        writer.write("objdocSheets.ActiveChart.HasTitle = false;");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"\u56FE\u8868 " + ChartIndex + "\").Left = objdocSheets.Sheets(\"Sheet1\").Cells(\"" + ChartCellRow + "\"," + ChartCellCol + ").Left;");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"\u56FE\u8868 " + ChartIndex + "\").Top = objdocSheets.Sheets(\"Sheet1\").Cells(\"" + ChartCellRow + "\"," + ChartCellCol + ").Top;");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"\u56FE\u8868 " + ChartIndex + "\").Width = " + ChartWidth + ";");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"\u56FE\u8868 " + ChartIndex + "\").Height = " + ChartHeight + ";");
    }

    public static void copyColumns(int StartCol, int EndCol, int TargetCol, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Columns(GetColName(" + StartCol + ") + \":\" + GetColName(" + EndCol + ")).Copy();");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Columns(GetColName(" + TargetCol + ") + \":\" + GetColName(" + TargetCol + ")).Insert();");
    }

    public static void mergeCells(int StartCol, int StartRow, int EndCol, int EndRow, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Range(GetColName(" + StartCol + ") + \"" + StartRow + ":\" + GetColName(" + EndCol + ") + \"" + EndRow + "\").MergeCells = true;");
    }

    public static void clearContents(int Row, int Col, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Cells(" + Row + "," + Col + ").ClearContents();");
    }

    public static void setCellAlign(int StartCol, int StartRow, int EndCol, int EndRow, int Align, int VAlign, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Range(GetColName(" + StartCol + ") + \"" + StartRow + ":\" + GetColName(" + EndCol + ") + \"" + EndRow + "\").HorizontalAlignment = " + Align + ";");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Range(GetColName(" + StartCol + ") + \"" + StartRow + ":\" + GetColName(" + EndCol + ") + \"" + EndRow + "\").VerticalAlignment = " + VAlign + ";");
    }

    public static void insertPic(String PicAddress, int PicXh, int PicHeight, int PicWidth, int PicAlignRow, int PicAlignCol, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Pictures.Insert(\"" + PicAddress + "\").Select();");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"Picture " + PicXh + "\").LockAspectRatio =0;");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"Picture " + PicXh + "\").Height =" + PicHeight + ";");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"Picture " + PicXh + "\").Width =" + PicWidth + ";");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"Picture " + PicXh + "\").Left = objdocSheets.Sheets(\"Sheet1\").Cells(" + PicAlignRow + "," + PicAlignCol + ").Left;");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"Picture " + PicXh + "\").Top = objdocSheets.Sheets(\"Sheet1\").Cells(" + PicAlignRow + "," + PicAlignCol + ").Top;");
    }
    
    public static void insertPic(String PicAddress, int PicXh, int PicHeight, int PicWidth, int PicAlignRow, int PicAlignCol, float offsetLeft, float offsetTop, PrintWriter writer)
    {
        writer.write("objdocSheets.Sheets(\"Sheet1\").Pictures.Insert(\"" + PicAddress + "\").Select();");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"Picture " + PicXh + "\").LockAspectRatio =0;");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"Picture " + PicXh + "\").Height =" + PicHeight + ";");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"Picture " + PicXh + "\").Width =" + PicWidth + ";");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"Picture " + PicXh + "\").Left = " + offsetLeft + " + objdocSheets.Sheets(\"Sheet1\").Cells(" + PicAlignRow + "," + PicAlignCol + ").Left;");
        writer.write("objdocSheets.Sheets(\"Sheet1\").Shapes(\"Picture " + PicXh + "\").Top = " + offsetTop + " + objdocSheets.Sheets(\"Sheet1\").Cells(" + PicAlignRow + "," + PicAlignCol + ").Top;");
    }

    public static void importExcelEnd(String BBMC, PrintWriter writer)
    {
    	//judgeHb(writer);
    	
        writer.write("objdocSheets.Sheets(\"Sheet1\").Range(\"A1\").Select();");
        writer.write("objdocSheets=null;");
        writer.write("objdoc=null;");
        writer.write("}\n");
        writer.write("ImportExcel();\n");
        
        writer.write("alert('" + BBMC.replaceAll("'", "\\'") + "\u62A5\u8868\u5DF2\u6210\u529F\u5BFC\u5165\u5230Excel\u4E2D\uFF01');" + "\n");
        writer.write("parent.indexSx();\n");
        writer.write("</SCRIPT>");
    }
    
    public static void importExcelEnd(String BBMC, PrintWriter writer,String refreshFlag)
    {
    	//judgeHb(writer);
    	
        writer.write("objdocSheets.Sheets(\"Sheet1\").Range(\"A1\").Select();");
        writer.write("objdocSheets=null;");
        writer.write("objdoc=null;");
        writer.write("}\n");
        writer.write("ImportExcel();\n");
        
        writer.write("alert('" + BBMC.replaceAll("'", "\\'") + "\u62A5\u8868\u5DF2\u6210\u529F\u5BFC\u5165\u5230Excel\u4E2D\uFF01');" + "\n");
        //writer.write("parent.indexSx();\n");
        if("1".equals(refreshFlag)){
        	writer.write("parent.colseProgress();\n");
        }else{
        	writer.write("parent.indexSx();\n");
        }
        
        writer.write("</SCRIPT>");
    }
    
    
    public static int getHight(int hight){
    	if(hight < 10){ 
    		hight = 10;
    	}else{
    		hight = hight+40;
    	}
    	if(hight >200){
    		hight = 120;
    	}
		return hight;
    }
    
    /**
     * 
     * @param days 一周的天数
     * @param rowNum 课表第一行的位置
     * @param writer
     */
    public static void judgeHb(int days,int rowNum,int js,PrintWriter writer){
    	writer.write(
    			"	for(i = 2;i<"+(2+days)+";i++){" +
    			" for(x="+rowNum+";x<"+(rowNum+js-1)+";x++){"+
    			"	var fCell = objdocSheets.Sheets(\"Sheet1\").Cells(x,i).Value;" +
    			" var hbFlag = false; "+
    			"  for(m=x+1;m<"+(rowNum+js)+";m++){"+
    			" var nCell = objdocSheets.Sheets(\"Sheet1\").Cells(m,i).Value;" +
    			" 	if(fCell ==nCell&&fCell!=undefined){" +
    			" 	objdoc.DisplayAlerts = false; hbFlag = true;" +
    			
    			" }else{ break;} " +
    			"if(hbFlag){objdocSheets.Sheets(\"Sheet1\").Range(GetColName(i) + x+\":\" + GetColName(i) + (m)).MergeCells = true;}"+
    			"}"+
    			"}"+
    			"}");
 
    	
    } 
    
    
    public static void allKbHb(PrintWriter writer,int row,int js){
    	
    	String script = " for(i=0;i<7;i++){" +
    			"for(n=(i*"+js+")+2;n<(i*"+js+")+"+(js+1)+";n++){" +
    			"	var fCell = objdocSheets.Sheets(\"Sheet1\").Cells("+row+",n).Value; " +
    			"for(m=n+1;m<(i*"+js+")+"+(js+2)+";m++){" +
    			" var nCell = objdocSheets.Sheets(\"Sheet1\").Cells("+row+",m).Value;" +
    			" 	if(fCell ==nCell&&fCell!=undefined&&fCell!=''){" +
    			" 	objdoc.DisplayAlerts = false; hbFlag = true;" +
    			
    			" }else{ break;} " +
    			"if(hbFlag){objdocSheets.Sheets(\"Sheet1\").Range(GetColName(n) + "+row+"+\":\" + GetColName(m) + "+row+").MergeCells = true;}"+
    			"" +
    			"}" +
    			"}" +
    			"}";
    	
    	writer.write(script);
    	
    }

}



/*
	DECOMPILATION REPORT

	Decompiled from: E:\workspace1\HNKJDX\WebRoot\WEB-INF\lib\QzKjZdBpKb.jar
	Total time: 218 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/