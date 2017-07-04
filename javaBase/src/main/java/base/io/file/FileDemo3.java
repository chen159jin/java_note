package base.io.file;

import java.io.File;

/**
 * 创建一个目录
 * @author adminitartor
 *
 */
public class FileDemo3 {
	public static void main(String[] args) {
		/*
		 * 在当前目录中创建目录demo
		 */
		File dir = new File("demo");
		if(!dir.exists()){
			dir.mkdir();
			System.out.println("创建完毕!");
		}
	}
}






