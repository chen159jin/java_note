package base.javaThread.demoAPI2;
/**
 * 线程提供了一个方法:join
 * 该方法是用来协调线程间的同步的。
 * 当一个线程调用另一个线程的join方法时，该线程
 * 会进入阻塞状态，直到另一个线程工作完毕才会解除
 * 阻塞。
 * @author adminitartor
 *
 */
public class ThreadDemo4 {
	//该属性表示文件是否下载完毕
	public static boolean isFinish = false;
	
	public static void main(String[] args) {
		final Thread download = new Thread(){
			public void run(){
				System.out.println("down:开始下载图片...");
				for(int i=1;i<=100;i++){
					System.out.println("down:"+i+"%");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("down:图片下载完毕!");
				isFinish = true;
			}
		};
		
		Thread show = new Thread(){
			public void run(){
				System.out.println("show:开始加载图片..");
				/*
				 * 在加载图片前应当等待下载线程先将图片下载完毕
				 */
				try {
					download.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(!isFinish){
					throw new RuntimeException("图片没有下载完毕!");
				}
				System.out.println("show:显示图片完毕!");
			}
		};
		
		download.start();
		show.start();
	}
}







