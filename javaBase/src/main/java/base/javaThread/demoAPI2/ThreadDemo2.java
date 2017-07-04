package base.javaThread.demoAPI2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * sleep阻塞
 * 线程提供了静态方法:static void sleep(long ms)
 * 该方法会将调用该方法的线程阻塞指定毫秒，当阻塞
 * 超时后，线程会自动回到runnable状态等待分配CPU
 * 时间片继续并发运行。
 * @author adminitartor
 *
 */
public class ThreadDemo2 {
	public static void main(String[] args) {
		/*
		 * 电子表
		 * 每秒钟输出一次当前系统时间
		 * 格式:HH:mm:ss    
		 * 例如:09:53:25
		 */
		SimpleDateFormat sdf
			= new SimpleDateFormat(
				"HH:mm:ss"	
			);
		
		while(true){
			try {				
				System.out.println(
					sdf.format(new Date())
				);
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}








