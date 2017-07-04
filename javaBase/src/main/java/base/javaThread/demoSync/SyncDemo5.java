package base.javaThread.demoSync;
/**
 * 死锁
 * 两个线程都在等待对方先施放锁。
 * @author adminitartor
 *
 */
public class SyncDemo5 {
	public static void main(String[] args) {
		final Coo c = new Coo();
		Thread t1 = new Thread(){
			public void run(){
				c.methodA();
			}
		};
		Thread t2 = new Thread(){
			public void run(){
				c.methodB();
			}
		};
		t1.start();
		t2.start();
	}
}
class Coo{
	private Object oa = new Object();
	private Object ob = new Object();
	
	public void methodA(){
		Thread t = Thread.currentThread();
		synchronized (oa) {
			System.out.println(
				t.getName()+":正在执行A方法..");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			methodB();
			System.out.println(
				t.getName()+"运行A方法完毕");
		}
	}
	public void methodB(){
		Thread t = Thread.currentThread();
		synchronized (ob) {
			System.out.println(t.getName()+":正在执行B方法..");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			methodA();
			System.out.println(t.getName()+"运行B方法完毕");
		}
	}
}





