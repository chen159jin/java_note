package base.io.file;

import java.io.File;

/**
 * 删除文件
 * @author adminitartor
 *
 */
public class FileDemo2 {
	public static void main(String[] args) {
		/*
		 * 将当前目录中的test.txt文件删除
		 */
		File file = new File("test.txt");
		if(file.exists()){
			file.delete();
			System.out.println("已删除");
		}else{
			System.out.println("该文件不存在!");
		}
	}
}






