package base.io.file;

import java.io.File;
import java.io.FileFilter;

/**
 * 根据文件过滤器来获取一个目录中满足要求的所有
 * 子项
 * @author adminitartor
 *
 */
public class FileDemo7 {
	public static void main(String[] args) {
		/*
		 * 获取当前目录中的所有文件
		 */
		File dir = new File(".");
		if(dir.isDirectory()){
//			MyFilter filter = new MyFilter();
//			File[] subs = dir.listFiles(filter);
			
			FileFilter filter = new FileFilter(){
				public boolean accept(File f) {
					System.out.println("正在过滤:"+f.getName());
					return f.isFile();
				}
			};
			File[] subs = dir.listFiles(filter);
			
			for(File sub : subs){
				System.out.println(sub.getName());
			}
		}
	}
}
class MyFilter implements FileFilter{

	public boolean accept(File f) {

		return f.isFile();
	}
	
}




