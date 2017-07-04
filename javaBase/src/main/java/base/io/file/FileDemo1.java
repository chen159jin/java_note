package base.io.file;

import java.io.File;

/**
 * java.io.File
 * File的每一个实例用于表示文件系统中的一个文件
 * 或目录
 * 通过File可以:
 * 访问其表示的文件或目录的属性(名字，大小，权限等)
 * 操作文件或目录(创建，删除)
 * 访问目录的所有子项
 * 
 * 但是不可以:
 * 访问文件内容 
 * @author Jin
 *
 */
public class FileDemo1 {
	public static void main(String[] args) {
		File file = new File(
			"."+File.separator+"demo.txt"
		);
		
		String name = file.getName();
		System.out.println("文件名:"+name);
		
		long length = file.length();
		System.out.println("大小:"+length+"字节");
		
		boolean cr = file.canRead();
		System.out.println("可读:"+cr);
		
		boolean cw = file.canWrite();
		System.out.println("可写:"+cw);
		
		boolean ih = file.isHidden();
		System.out.println("是否隐藏:"+ih);
	}
}




