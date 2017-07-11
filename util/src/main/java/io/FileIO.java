/**
 * 说明：文件IO操作工具类
 * 编写者：谢平
 * 日期：Nov 20, 2007
 * 湖南强智科技版权所有。
 */
package io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import org.apache.struts.upload.FormFile;



//import com.qzdatasoft.comm.generic.Sequence;
import com.qzdatasoft.comm.dao.util.Sequence;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import stringUtil.StringUtils;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * <p>
 * Title:文件IO操作工具类
 * <p>
 * Description:文件IO操作工具类，请使用这个工具类完成文件操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: 湖南强智科技发展有限公司
 * </p>
 * 
 */
public class FileIO {
	/**
	 * readFile(String filePath) 通过文件路径以字节方式读取文件内容
	 * 
	 * @param filePath
	 *            String
	 * @return String
	 */
	public static String readFile(String filePath) {
		String info = "";
		try {
			File f = new File(filePath);
			if (f.exists()) {
				FileInputStream bw = new FileInputStream(f);
		int len = bw.available();//可获得的长度 对于网络传输可能会因为网络阻塞而不正确
				byte[] str = new byte[len];
				if (bw.read(str) == -1) {
					info = "";
				} else {
					info = new String(str);
				}
				bw.close();
				bw = null;
			}
			f = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * readFile(String filePath, String charset) 通过文件路径以字节方式按照指定的编码读取文件内容
	 * 
	 * @param filePath
	 *            String
	 * @param charset
	 *            String
	 * @return String
	 */
	public static String readFile(String filePath, String charset) {
		String info = "";
		try {
			File f = new File(filePath);
			if (f.exists()) {
				FileInputStream bw = new FileInputStream(f);
				int len = bw.available();
				byte[] str = new byte[len];
				if (bw.read(str) == -1) {
					info = "";
				} else {
					info = new String(str, charset);
				}
				bw.close();
				bw = null;
			}
			f = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * writeFile(String msg, String filePath) 以字节的方式写文件 推荐使用
	 * 
	 * @param msg
	 *            String
	 * @param filePath
	 *            String
	 */
	public static void writeFile(String msg, String filePath) {
		FileWriter fw = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			fw = new FileWriter(filePath);
			fw.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 以流的方式写文件
	 * 
	 * @param msg
	 * @param filePath
	 */
	public static void writeFile2(String msg, String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream wf = new FileOutputStream(filePath);
			wf.write(msg.getBytes("utf-8"));
			wf.close();
			file = null;
			wf = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * makeDir(String directoryName) 无条件建立一个目录
	 * 
	 * @param directoryName
	 *            String
	 * @return boolean
	 */
	public static boolean makeDir(String directoryName) {
		boolean flag = false;
		try {
			File aFile = new File(directoryName);
			aFile.mkdir();
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * cutFile(String sourceDirectoryName,String sourceFileName, String
	 * DestDirectoryName,String DestFileName) 把本地文件cut到另一个目录
	 * 
	 * @param sourceDirectoryName
	 *            String
	 * @param sourceFileName
	 *            String
	 * @param DestDirectoryName
	 *            String
	 * @param DestFileName
	 *            String
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean cutFile(String sourceDirectoryName,
			String sourceFileName, String DestDirectoryName, String DestFileName)
			throws java.io.IOException {
		boolean flag = false;
		try {
			File aFile = new File(sourceDirectoryName + sourceFileName);
			aFile.renameTo(new File(DestDirectoryName + DestFileName));
			aFile.delete();
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * cutFiles(String sourceDirectoryName, String DestDirectoryName,Vector
	 * fileNames) 把本地多个文件cut到另一个目录
	 * 
	 * @param sourceDirectoryName
	 *            String
	 * @param destDirectoryName
	 *            String
	 * @param fileNames
	 *            Vector
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean cutFiles(String sourceDirectoryName,
			String destDirectoryName, Vector fileNames)
			throws java.io.IOException {
		boolean flag = false;
		try {
			File af = new File(destDirectoryName);
			af.mkdirs();
			for (int i = 0; i < fileNames.size(); i++) {
				cutFile(sourceDirectoryName, (String) fileNames.elementAt(i),
						destDirectoryName, (String) fileNames.elementAt(i));
			}
			/*
			 * File af1 = new File(sourceDirectoryName); af1.delete();
			 */
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * copyFile(File src,File dest) 把本地一个文件copy到另一个地方
	 * 
	 * @param src
	 *            File
	 * @param dest
	 *            File
	 * @throws IOException
	 */
	public static void copyFile(File src, File dest) throws java.io.IOException {
		int length = (int) src.length();
		// 分块拷贝转发的附件
		int block = 20000000;
		int blocknum = length / block;
		int blockmod = length % block;
		int totalblock = 0;
		if (blockmod == 0) {
			totalblock = blocknum;
		} else {
			totalblock = blocknum + 1;
		}
		FileOutputStream outfile = null;
		FileInputStream infile = null;
		try {
			int starthead = 0;
			outfile = new FileOutputStream(dest);
			infile = new FileInputStream(src);
			for (int m = 0; m < totalblock && length != 0; m++) {
				if ((m + 1) * block > length) {
					byte[] TempAttach = new byte[blockmod];
					infile.read(TempAttach, starthead, blockmod);
					outfile.write(TempAttach, starthead, blockmod);
					outfile.close();
					infile.close();
				} else {
					byte[] TempAttach = new byte[block];
					infile.read(TempAttach, starthead, block);
					outfile.write(TempAttach, starthead, block);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			outfile.close();
			infile.close();
		}
	}

	/**
	 * copyDirectory(String srcDir,String destDir) 把本地一个目录下所有文件copy到另一个目录。
	 * 
	 * @param srcDir
	 *            String
	 * @param destDir
	 *            String
	 */
	public static void copyDirectory(String srcDir, String destDir) {
		File srcDirFile = new File(srcDir);
		File destDirFile = new File(destDir);
		if (srcDirFile.exists()) {
			if (destDirFile.exists() == false) {
				destDirFile.mkdir();
			}
			File[] files = srcDirFile.listFiles();
			int i = files.length;
			int a = 0;
			for (a = 0; a < i && i > 0; a++) {
				try {
					copyFile(
							files[a],
							new File(
									destDir
											+ File.separator
											+ files[a]
													.getCanonicalPath()
													.substring(
															files[a].getCanonicalPath()
																	.lastIndexOf(
																			File.separator) + 1)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * seperateBasicFilename(String wholeFilename) 从完整的路径名中获取不包含路径信息的文件名
	 * 
	 * @param wholeFilename
	 *            String
	 * @return String
	 */
	public static String seperateBasicFilename(String wholeFilename) {
		String fg = new String(File.separator);
		int index = wholeFilename.lastIndexOf(fg);
		if (index < 0) {
			return wholeFilename;
		}
		String filename = wholeFilename.substring(index + 1);
		return filename;
	}

	/**
	 * seperateSimpleFilename(String wholeFilename) 从完整的路径名中获取不包含路径信息没有扩展名的文件名
	 * 
	 * @param wholeFilename
	 *            String
	 * @return String
	 */
	public static String seperateSimpleFilename(String wholeFilename) {
		String filename = seperateBasicFilename(wholeFilename);
		String fg = new String(".");
		int index = filename.lastIndexOf(fg);
		if (index < 0) {
			return wholeFilename;
		}
		String simplefilename = filename.substring(0, index);
		return simplefilename;
	}

	/**
	 * getFileExt(String fileName) 从文件名中取得文件扩展名
	 * 
	 * @param fileName
	 *            String
	 * @return String
	 */
	public static String getFileExt(String fileName) {
		String fileExt = "";
		int index = fileName.lastIndexOf(".");
		fileExt = fileName.substring(index, fileName.length());
		return fileExt;
	}

	/**
	 * getRealFilePath(ServletContext context,String filePath)
	 * 输入一个文件的相对路径，返回他的绝对路径，需要ServletContext信息
	 * 
	 * @param context
	 *            ServletContext
	 * @param filePath
	 *            String
	 * @return String
	 */
	public static String getRealFilePath(ServletContext context, String filePath) {
		String realFilePath = context.getRealPath(filePath);
		return realFilePath;
	}

	/**
	 * deleteFile(String filePath)
	 * 
	 * @param realFilePath
	 *            String
	 * @return boolean 删除文件或目录
	 */
	public static boolean deleteFile(String realFilePath) {
		boolean flag = false;
		try {
			File aFile = new File(realFilePath);
			flag = aFile.delete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * @param args
	 */
	// 删除文件夹下的所有文件
	public static boolean deleteAllFile(String folderFullPath) {
		boolean ret = false;
		File file = new File(folderFullPath);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] fileList = file.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					String filePath = fileList[i].getPath();
					deleteAllFile(filePath);
				}
			}
			if (file.isFile()) {
				file.delete();
			}
		}
		return ret;
	}

	/**
	 * @param args
	 */
	// 删除文件夹下的所有文件
	public static String getAllFristFile(String folderFullPath) {
		String path = "";
		File file = new File(folderFullPath);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] fileList = file.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					path = fileList[i].getPath();

				}
			}
			if (file.isFile()) {
				file.delete();
			}
		}
		return path;
	}

	/**
	 * 解压缩功能. 将ZIP_FILENAME文件解压到ZIP_DIR目录下.
	 * 
	 * @throws Exception
	 */
	public static void upZipFile(String path, String mbpath) throws Exception {
		String baseDir = mbpath;
		ZipFile zfile = new ZipFile(path);
		Enumeration zList = zfile.entries();
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		while (zList.hasMoreElements()) {
			ze = (ZipEntry) zList.nextElement();
			if (ze.isDirectory()) {
				System.out.println("Dir: " + ze.getName() + " skipped..");
				continue;
			}
			System.out.println("Extracting: " + ze.getName() + "\t"
					+ ze.getSize() + "\t" + ze.getCompressedSize());

			// 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					getRealFileName(baseDir, ze.getName())));
			InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
			System.out.println("Extracted: " + ze.getName());
		}
		zfile.close();

	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * 
	 * @param baseDir
	 *            指定根目录
	 * @param absFileName
	 *            相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	public static File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		File ret = new File(baseDir);
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
			if (!ret.exists())
				ret.mkdirs();
			ret = new File(ret, dirs[dirs.length - 1]);
			return ret;
		}
		return ret;
	}

	/**
	 * checkFilesFolder(String realFilePath) 检查文件路径是否存在
	 * 
	 * @param realFilePath
	 *            String
	 * @return boolean
	 */
	public static boolean checkFilesFolder(String realFilePath) {
		boolean flag = false;
		try {
			File aFile = new File(realFilePath);
			flag = aFile.exists();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * isAllowFile(String flag, String fileName) 检查图形文件名是否合法--*.jpg;*.gif;*.gif
	 * 
	 * @param flag
	 *            String
	 * @param fileName
	 *            String
	 * @return boolean
	 */
	public static boolean isAllowFile(String flag, String fileName) {
		boolean isAllow = false;
		if (flag.equals("pic")) {
			fileName = fileName.toLowerCase();
			if (fileName.endsWith(".jpg") || fileName.endsWith(".gif")
					|| fileName.endsWith(".gif")) {
				isAllow = true;
			}
		}
		return isAllow;
	}

	/**
	 * creatFile(InputStream stream, String outPutRealPath) 以文件流的形式建立文件
	 * 
	 * @param stream
	 *            InputStream
	 * @param outPutRealPath
	 *            String
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean creatFile(InputStream stream, String outPutRealPath)
			throws java.io.IOException {
		boolean flag = false;
		OutputStream bos = null;
		try {
			bos = new FileOutputStream(outPutRealPath);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stream.close();
			bos.close();
		}
		return flag;
	}

	/**
	 * shrinkImage(String inRealPath, String outRealPath, int outWidth, int
	 * outHeigh) 将图形文件按照指定最大宽高缩放，如果小于指定宽高，则拷贝
	 * 
	 * @param inRealPath
	 *            String
	 * @param outRealPath
	 *            String
	 * @param outWidth
	 *            int
	 * @param outHeigh
	 *            int
	 * @return boolean
	 */
	public static boolean imageOperate(String inRealPath, String outRealPath,
			int outWidth, int outHeigh) {
		boolean opFlag = false;
		try {
			File _file = new File(inRealPath); // 读入文件
			Image src = javax.imageio.ImageIO.read(_file); // 构造Image对象
			int width = src.getWidth(null); // 得到源图宽
			int height = src.getHeight(null); // 得到源图长
			int newWidth = 1;
			int newHeight = 1;
			// 根据源长宽缩放
			if (width < outWidth && height < outHeigh) {
				// 拷贝原图
				copyFile(new File(inRealPath), new File(outRealPath));
			} else {
				if (width >= height) {
					newWidth = outWidth;
					newHeight = outWidth * height / width;
				} else {
					newHeight = outHeigh;
					newWidth = outHeigh * width / height;
				}
				// 绘制缩小后的图
				BufferedImage tag = new BufferedImage(newWidth, newHeight,
						BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(src, 0, 0, newWidth, newHeight,
						null);
				// 输出到文件流
				FileOutputStream out = new FileOutputStream(outRealPath);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				// 近JPEG编码
				encoder.encode(tag);
				out.close();
			}
			opFlag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return opFlag;
	}

	/**
	 * getDirectoryFilesNum(String realPath) 获得一个目录下的文件数量
	 * 
	 * @param realPath
	 *            String
	 * @return int
	 */
	public static int getDirectoryFilesNum(String realPath) {
		int filesNum = 0;
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				filesNum = entries.length;
			}
		}
		return filesNum;
	}

	/**
	 * getDirectoryFilesName(String realPath) 获得该目录下所有文件名字（包含扩展名）
	 * 
	 * @param realPath
	 *            String
	 * @return List
	 */
	public static List getDirectoryFilesName(String realPath) {
		List ls = new ArrayList();
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].isFile())
						ls.add(entries[i].getName());
				}
			}
		}
		return ls;
	}

	/**
	 * 获得目录下所有文件对象
	 * 
	 * @param realPath
	 *            String
	 * @return List
	 */
	public static List getDirectoryFilesNameByFileType(String realPath) {
		List ls = new ArrayList();
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].isFile())
						ls.add(entries[i]);
				}
			}
		}
		return ls;
	}

	/**
	 * getSubDirsName(String realPath) 获得该目录下所有子目录
	 * 
	 * @param realPath
	 *            String
	 * @return List
	 */
	public static List getSubDirsName(String realPath) {
		List ls = new ArrayList();
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].isDirectory())
						ls.add(entries[i].getName());
				}
			}
		}
		return ls;
	}

	/**
	 * smartMakeDir(ServletContext context,String filePath) 输入context、相对路径建立目录
	 * 
	 * @param context
	 *            ServletContext
	 * @param filePath
	 *            String
	 * @return int 建立目录的数量 -1错误无法建立目录
	 */
	public static int smartMakeDir(ServletContext context, String filePath) {
		int opFlag = 0;
		// 分解路径
		String[] tempPath = filePath.substring(1).split("/");
		String tempStr = "";
		String realPath;
		for (int i = 0; i < tempPath.length; i++) {
			// 检查该路径是否存在，不存在就建立
			tempStr += "/" + tempPath[i];
			realPath = getRealFilePath(context, tempStr);
			if (!checkFilesFolder(realPath)) {
				if (makeDir(realPath)) {
					++opFlag;
				} else {
					opFlag = -1;
					break;
				}
			}
		}
		return opFlag;
	}

	/**
	 * String makeDirName(int buildID) 根据任意ID对23,19,17取模生成目录名,从而生成相应的目录结构;
	 * 
	 * @param buildID
	 *            int
	 * @return String
	 */
	public static String makeDirName(int buildID) {
		int dirID = buildID;
		StringBuffer dirStructure = new StringBuffer();
		dirStructure.append("/").append(String.valueOf(dirID % 23));
		dirStructure.append("/").append(String.valueOf(dirID % 19));
		dirStructure.append("/").append(String.valueOf(dirID % 17));
		return dirStructure.toString();
	}

	/**
	 * getDirectoryAllFiles(String realPath)
	 * 
	 * @param realPath
	 *            String
	 * @return AarrayList
	 * @todo get all files in the realPath
	 */
	public static ArrayList getDirectoryAllFiles(String realPath) {
		ArrayList fileList = new ArrayList();
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].getAbsolutePath().indexOf(".") > 0) {
						fileList.add(entries[i].getAbsolutePath());
					}
				}
			}
		}
		return fileList;
	}

	/**
	 * getAllSubDirs(String realPath)
	 * 
	 * @param realPath
	 *            String
	 * @return ArrayList
	 * @todo get all directories in the realPath
	 */
	public static ArrayList getAllSubDirs(String realPath) {
		ArrayList dirList = new ArrayList();
		if (checkFilesFolder(realPath)) {
			File directory = new File(realPath);
			if (directory.isDirectory()) {
				File[] entries = directory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (entries[i].getAbsolutePath().indexOf(".") < 0) {
						dirList.add(entries[i].getAbsolutePath());
					}
				}
			}
		}
		return dirList;
	}

	/**
	 * createImgWaterMark(String filePath, String watermark) 在图片上打上水印
	 * 
	 * @param filePath
	 *            String
	 * @param watermark
	 *            String
	 * @return ArrayList
	 */
	public static boolean createImgWaterMark(String filePath, String watermark) {
		try {
			File _file = new File(filePath); // 读入文件
			Image theImg = javax.imageio.ImageIO.read(_file); // 构造Image对象
			ImageIcon waterIcon = new ImageIcon("temp");
			Image waterImg = waterIcon.getImage();
			int width = theImg.getWidth(null);
			int height = theImg.getHeight(null);
			BufferedImage bimage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bimage.createGraphics();
			g.setColor(Color.red);
			g.setBackground(Color.white);
			g.drawImage(theImg, 0, 0, null);
			g.drawImage(waterImg, 100, 100, null);
			g.drawString(watermark, 10, 10); // 添加文字
			g.dispose();
			FileOutputStream out = new FileOutputStream(filePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(50f, true);
			encoder.encode(bimage, param);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 输入Struts的FormFile对象，上传一个文件
	 * 
	 * @param file
	 * @param request
	 * @return 文件相对路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String doUploadFile(FormFile file, HttpServletRequest request) {
		if (file.getFileSize() <= 0) {
			return null;
		}
		String filepath = "";
		String zt = "";
		String type = request.getParameter("type");
		if ("by".equals(type)) {
			zt = "by";
		}

		String dirStructure = "/" + zt + "uploadfile/" + StringUtils.getYear()
				+ "/" + StringUtils.getMonth() + "/" + StringUtils.getDay();
		String fileName = Sequence.getInstance().getSequence(16)
				+ FileIO.getFileExt(file.getFileName());
		String uploadPath = FileIO.getRealFilePath(request.getSession()
				.getServletContext(), dirStructure);
		if (!FileIO.checkFilesFolder(uploadPath)) {
			FileIO.smartMakeDir(request.getSession().getServletContext(),
					dirStructure);
		}
		try {
			InputStream stream = file.getInputStream();
			FileIO.creatFile(stream, uploadPath + File.separator + fileName);
			filepath = dirStructure + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filepath;
	}

	/**
	 * 文件上传新方法，按绝对地址上传
	 * 
	 * @param file
	 * @param request
	 * @param path
	 * @return
	 */
	public static String doUploadFile_new(FormFile file,
			HttpServletRequest request, String dirPath, String fileName) {
		if (file.getFileSize() <= 0) {
			return null;
		}

		InputStream stream = null;
		OutputStream bos = null;
		String filepath = "";

		try {
			stream = file.getInputStream();

			File tempFile = new File(dirPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}

			filepath = dirPath + File.separator + fileName;
			bos = new FileOutputStream(filepath);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}

		return filepath;
	}

	/**
	 * 输入Struts的FormFile照片对象，上传一个文件
	 * 
	 * @param file
	 * @param request
	 * @return 文件相对路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String doUploadFilezp(FormFile file,
			HttpServletRequest request) {
		if (file.getFileSize() <= 0) {
			return null;
		}
		String filepath = "";
		String zt = "";
		String type = request.getParameter("type");
		if (type.equals("by")) {
			zt = "by";
		}

		String dirStructure = "/" + zt + "uploadfile/pic";
		String fileName = Sequence.getInstance().getSequence(16)
				+ FileIO.getFileExt(file.getFileName());
		String uploadPath = FileIO.getRealFilePath(request.getSession()
				.getServletContext(), dirStructure);
		if (!FileIO.checkFilesFolder(uploadPath)) {
			FileIO.smartMakeDir(request.getSession().getServletContext(),
					dirStructure);
		}
		try {
			InputStream stream = file.getInputStream();
			FileIO.creatFile(stream, uploadPath + File.separator + fileName);
			filepath = dirStructure + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return fileName;
	}

	/**
	 * 说明：本方法指定特定路径 仅供学生相片上传 作者: 章波 上传路径: uploadfile/studentphoto/入学年份/学号.jpg
	 * 
	 */
	public static String doXSUploadFile(FormFile file,
			HttpServletRequest request, String rxnf, String xh) {
		if (file.getFileSize() <= 0) {
			return null;
		}
		String filepath = "";
		String zt = "";
		String type = request.getParameter("type");
		if (type.equals("by")) {
			zt = "by";
		}
		String dirStructure = "/" + zt + "uploadfile/studentphoto/" + rxnf;// StringUtils.getYear()
																			// +
																			// "/"
		// + StringUtils.getMonth() + "/" + StringUtils.getDay();
		String fileName = xh + FileIO.getFileExt(file.getFileName());

		String uploadPath = FileIO.getRealFilePath(request.getSession()
				.getServletContext(), dirStructure);
		if (!FileIO.checkFilesFolder(uploadPath)) {
			FileIO.smartMakeDir(request.getSession().getServletContext(),
					dirStructure);
		}
		try {
			InputStream stream = file.getInputStream();
			FileIO.creatFile(stream, uploadPath + File.separator + fileName);
			filepath = dirStructure + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filepath;
	}

	/**
	 * 输入Struts的FormFile对象，上传一个文件，指定文件名
	 * 
	 * @param file
	 * @param request
	 * @param fileName
	 * @return 文件相对路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String doUploadFile(FormFile file,
			HttpServletRequest request, String fileName) {
		if (file.getFileSize() <= 0) {
			return null;
		}
		String filepath = "";
		String zt = "";
		String type = request.getParameter("type");
		if (type.equals("by")) {
			zt = "by";
		}
		String dirStructure = "/" + zt + "uploadfile/" + StringUtils.getYear()
				+ "/" + StringUtils.getMonth() + "/" + StringUtils.getDay();
		fileName += FileIO.getFileExt(file.getFileName());
		String uploadPath = FileIO.getRealFilePath(request.getSession()
				.getServletContext(), dirStructure);
		if (!FileIO.checkFilesFolder(uploadPath)) {
			FileIO.smartMakeDir(request.getSession().getServletContext(),
					dirStructure);
		}
		try {
			InputStream stream = file.getInputStream();
			FileIO.creatFile(stream, uploadPath + File.separator + fileName);
			filepath = dirStructure + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filepath;
	}

	/**
	 * 解压文件夹
	 * 
	 * @param zipFileName
	 * @param request
	 * @param extPlace
	 * @return 文件相对路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void extZipFileList(String zipFileName, String extPlace) {
		try {

			ZipInputStream in = new ZipInputStream(new FileInputStream(
					zipFileName));

			ZipEntry entry = null;

			while ((entry = in.getNextEntry()) != null) {

				String entryName = entry.getName();

				if (entry.isDirectory()) {
					File file = new File(entryName);
					file.mkdirs();
					System.out.println("创建文件夹: " + entryName);
				} else {
					try {
						if (entryName.indexOf("/") >= 0)// 带文件夹
						{
							String[] entryNamesz = entryName.split("/");
							entryName = entryNamesz[entryNamesz.length - 1];
						}
					} catch (Exception e) {

					}

					FileOutputStream os = new FileOutputStream(extPlace + "\\"
							+ entryName);
					System.out.println("创建文件夹: " + entryName);
					// Transfer bytes from the ZIP file to the output file
					byte[] buf = new byte[1024];

					int len;
					while ((len = in.read(buf)) > 0) {
						os.write(buf, 0, len);
					}
					os.close();
					in.closeEntry();

				}
			}

		} catch (IOException e) {
			String aaaString = "";
		}
		System.out.println("解压文件成功 ");
	}

	/**
	 * 写文件
	 * 
	 * @param msg
	 * @param request
	 * @return 文件地址
	 */
	public static String writeFile(String msg, HttpServletRequest request) {
		String filepath = "";
		String zt = "";
		String type = request.getParameter("type");
		if (type.equals("by")) {
			zt = "by";
		}
		String dirStructure = "/" + zt + "uploadfile/" + StringUtils.getYear()
				+ "/" + StringUtils.getMonth() + "/" + StringUtils.getDay();
		String uploadPath = FileIO.getRealFilePath(request.getSession()
				.getServletContext(), dirStructure);
		String fileName = Sequence.getInstance().getSequence(16) + ".html";
		if (!FileIO.checkFilesFolder(uploadPath)) {
			FileIO.smartMakeDir(request.getSession().getServletContext(),
					dirStructure);
		}
		FileIO.writeFile(msg, uploadPath + File.separator + fileName);
		filepath = dirStructure + "/" + fileName;
		return filepath;
	}

	/**
	 * 通过相对地址删除一个文件
	 * 
	 * @param filePath
	 * @param request
	 * @return boolean
	 */
	public static boolean delUploadFile(String filePath,
			HttpServletRequest request) {
		return FileIO.deleteFile(FileIO.getRealFilePath(request.getSession()
				.getServletContext(), filePath));
	}

	/**
	 * 通过绝对地址删除一个文件
	 * 
	 * @param filePath
	 * @param request
	 * @return boolean
	 */
	public static boolean delUploadFile(String filePath) {
		return FileIO.deleteFile(filePath);
	}

	/**
	 * 在控制台上输出所有的映射文件信息，用于维护hibernate.cfg.xml
	 * 
	 * @param path
	 *            目录路径D:/workspace1.4/QZWF/src/com/qzdatasoft/comm/framework/
	 *            bean
	 * @param packagename
	 *            包名com/qzdatasoft/comm/framework/bean/
	 * @param cnModleName
	 *            控制框架
	 * @return 映射文件数量
	 */
	public static int getHibernaetMappingXml(String path, String packagename,
			String cnModleName) {
		// 获得framework/bean下所有的XML文件
		System.out.println("<!-- " + cnModleName + "开始 -->");
		List fbean = FileIO.getDirectoryFilesName(path);
		int count = 0;
		for (int i = 0; i < fbean.size(); i++) {
			if (FileIO.getFileExt(fbean.get(i).toString()).equals(".xml")) {
				System.out.println("<mapping resource=\"" + packagename
						+ fbean.get(i) + "\"/>");
				count++;
			}
		}
		System.out.println("<!-- " + cnModleName + "结束，共" + count + "个对象 -->");
		return count;
	}

	public static void main(String[] args) {
		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/QZWF/src/com/qzdatasoft/comm/framework/bean",
				"com/qzdatasoft/comm/framework/bean/", "控制框架");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/QZWF/src/com/qzdatasoft/comm/framework/zdbeans",
				"com/qzdatasoft/comm/framework/zdbeans/", "字典信息");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/QZWF/src/com/qzdatasoft/ggxx/bean",
				"com/qzdatasoft/ggxx/bean/", "公共信息");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/QZWF/src/com/qzdatasoft/ggxx/bean/jzg",
				"com/qzdatasoft/ggxx/bean/jzg/", "教职工");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/QZWF/src/com/qzdatasoft/ggxx/bean/xj",
				"com/qzdatasoft/ggxx/bean/xj/", "学生信息");
		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/bygl",
				"com/qzdatasoft/hdjw/bean/bygl/", "毕业管理");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/cjgl",
				"com/qzdatasoft/hdjw/bean/cjgl/", "成绩管理");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/jxjh",
				"com/qzdatasoft/hdjw/bean/jxjh/", "教学计划");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/kjgl",
				"com/qzdatasoft/hdjw/bean/kjgl/", "考级管理");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/kkgl",
				"com/qzdatasoft/hdjw/bean/kkgl/", "开课管理");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/ksgl",
				"com/qzdatasoft/hdjw/bean/ksgl/", "考试管理");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/pkgl",
				"com/qzdatasoft/hdjw/bean/pkgl/", "排课管理");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/view",
				"com/qzdatasoft/hdjw/bean/view/", "教务视图");
		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/xkgl",
				"com/qzdatasoft/hdjw/bean/xkgl/", "选课管理");
		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/xkjs",
				"com/qzdatasoft/hdjw/bean/xkjs/", "学科竞赛");

		FileIO.getHibernaetMappingXml(
				"D:/workspace1.4/JiaoWu20081013/src/com/qzdatasoft/hdjw/bean/zljk",
				"com/qzdatasoft/hdjw/bean/zljk/", "质量监控");

	}
}
