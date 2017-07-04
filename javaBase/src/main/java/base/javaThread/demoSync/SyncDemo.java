package base.javaThread.demoSync;
/**
 * 多线程并发安全问题
 * 当多个线程访问同一资源时，由于线程切换时机不确定，
 * 可能导致多个线程执行代码出现混乱，导致程序执行
 * 出现问题，严重时可能导致系统瘫痪。
 * 
 * 解决并发安全问题，就是要将多个线程"抢"改为"排队"
 * 执行
 * @author adminitartor
 *
 */
public class SyncDemo {
	public static void main(String[] args) {
		final Table table = new Table();
		
		Thread t1 = new Thread(){
			public void run(){
				while(true){
					int bean = table.getBean();
					//模拟CPU执行到这里没有时间
					Thread.yield();
					System.out.println(
						getName()+":"+bean
					);
				}
			}
		};
		Thread t2 = new Thread(){
			public void run(){
				while(true){
					int bean = table.getBean();
					//模拟CPU执行到这里没有时间
					Thread.yield();
					System.out.println(getName()+":"+bean);
				}
			}
		};
		
		t1.start();
		t2.start();
	}
}

class Table{
	private int beans = 20;	
	/**
	 * 当一个方法被Synchronized修饰后，那么
	 * 该方法称为"同步方法"，即多个线程不能同时
	 * 进入方法内部执行
	 * 方法上使用synchronized，那么锁对象就是
	 * 当前方法所属对象，即:this
	 * @return
	 */
	public synchronized int getBean(){
		if(beans==0){
			throw new RuntimeException("没有豆子了!");
		}
		//模拟CPU执行到这里没有时间
		Thread.yield();
		return beans--;
	}
}








