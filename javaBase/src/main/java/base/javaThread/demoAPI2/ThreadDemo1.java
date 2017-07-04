package base.javaThread.demoAPI2;
/**
 * 线程优先级
 * 线程优先级有10个等级，分别用数字1-10表示
 * 其中1最低，10最高，5为默认值
 * 理论上，优先级越高的线程，获取CPU时间片的次数
 * 多。
 * @author adminitartor
 *
 */
public class ThreadDemo1 {
	public static void main(String[] args) {
		Thread max = new Thread(){
			public void run(){
				for(int i=0;i<10000;i++){
					System.out.println("max");
				}
			}
		};
		Thread min = new Thread(){
			public void run(){
				for(int i=0;i<10000;i++){
					System.out.println("min");
				}
			}
		};
		Thread norm = new Thread(){
			public void run(){
				for(int i=0;i<10000;i++){
					System.out.println("nor");
				}
			}
		};		
		max.setPriority(Thread.MAX_PRIORITY);
		min.setPriority(Thread.MIN_PRIORITY);
		
		min.start();
		norm.start();
		max.start();
	}
}










