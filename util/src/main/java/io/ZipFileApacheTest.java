package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipOutputStream;


public class ZipFileApacheTest {/*
	public ActionForward daoPrintproc(String sql, String stxt,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String destDirectory = request.getSession().getServletContext()
				.getRealPath("uploadfile\\wz");
		File file = new File(request.getSession().getServletContext()
				.getRealPath("uploadfile\\xszp.rar"));
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		FileOutputStream fous = null;
		try {
			fous = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
		}
		ZipOutputStream zipOut = new ZipOutputStream(fous);
		try {
			// 调用业务方法进行图片批量下载文件
			ZipFileApache zpf = new ZipFileApache();
			File fileds = new File(destDirectory);
			zpf.zipFilepath(fileds, zipOut);
			zipOut.close();
			String filepath = file.getPath();
			zpf.downloadFile(filepath, response);
			FileIO.deleteFile(file.getPath());
			FileIO.deleteAllFile(request.getSession()
					.getServletContext().getRealPath("uploadfile\\wz"));
			FileIO.deleteFile(request.getSession().getServletContext()
					.getRealPath("uploadfile\\xszp.rar"));
			FileIO.deleteFile(request.getSession().getServletContext()
					.getRealPath("uploadfile\\wz"));
			// this.daoPrintprocs(mapping, form, request, response);
		} catch (IOException e) {
		}
	}
*/}
