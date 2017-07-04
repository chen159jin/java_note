package base.javaThread.demoSync;
/**
 * 互斥锁
 * 当synchronized修饰两段不同代码，但是同步监视器
 * 对象相同时，这两段代码就具有了互斥性，多段代码也
 * 可以。
 * 
 * @author adminitartor
 *
 */
public class SyncDemo4 {
	public static void main(String[] args) {
		final Boo b = new Boo();
		Thread t1 = new Thread(){
			public void run(){
				b.methodA();
			}
		};
		Thread t2 = new Thread(){
			public void run(){
				b.methodB();
			}
		};
		t1.start();
		t2.start();
	}
}

class Boo{
	public synchronized void methodA(){
		Thread t = Thread.currentThread();
		try {
			System.out.println(t.getName()+":正在运行A方法");
			Thread.sleep(5000);
			System.out.println(t.getName()+":A方法运行完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	public synchronized void methodB(){
		Thread t = Thread.currentThread();
		try {
			System.out.println(t.getName()+":正在运行B方法");
			Thread.sleep(5000);
			System.out.println(t.getName()+":B方法运行完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
}






