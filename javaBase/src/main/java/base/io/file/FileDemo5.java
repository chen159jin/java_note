package base.io.file;

import java.io.File;

/**
 * 删除目录
 * @author adminitartor
 *
 */
public class FileDemo5 {
	public static void main(String[] args) {
		/*
		 * 删除当前目录中的目录demo
		 * 删除目录有一个要求，就是该目录必须
		 * 是一个空目录
		 */
		File dir = new File("demo");
		if(dir.exists()){
			dir.delete();
			System.out.println("删除完毕!");
		}
	}
}






