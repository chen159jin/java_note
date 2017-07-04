package base.io.file;

import java.io.File;

/**
 * 获取一个目录中的所有子项
 * @author adminitartor
 *
 */
public class FileDemo6 {
	public static void main(String[] args) {
		/*
		 * 获取当前目录中的所有子项
		 */
		File dir = new File(".");
		/*
		 * 判断当前File表示的是文件还是目录
		 * boolean isDirectory()
		 * boolean isFile()
		 */
		if(dir.isDirectory()){
			File[] subs = dir.listFiles();
			for(File sub : subs){
				System.out.print(sub.isFile()?"文件:":"目录:");
				System.out.println(sub.getName());
			}
		}
	}
}







