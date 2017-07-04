package base.javaThread.demoAPI2;
/**
 * 守护线程
 * 当一个进程中的所有前台线程都结束后，进程结束，
 * 这时进程中的所有正在运行的后台线程都会被强制中断。
 * 
 * @author adminitartor
 *
 */
public class ThreadDemo3 {
	public static void main(String[] args) {
		/*
		 * rose:前台线程
		 */
		Thread rose = new Thread(){
			public void run(){
				for(int i=0;i<5;i++){
					System.out.println("rose:let me go!");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("rose:啊啊啊啊啊AAAAAaaaaa.....");
				System.out.println("音效:噗通!");
			}
		};
		
		Thread jack = new Thread(){
			public void run(){
				while(true){
					System.out.println("jack:you jump!i jump!");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		//设置为守护线程,必须在线程启动前设置
		jack.setDaemon(true);
		
		
		rose.start();
		jack.start();
		
	}
}












