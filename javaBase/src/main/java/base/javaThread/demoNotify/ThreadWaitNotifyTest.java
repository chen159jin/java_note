package base.javaThread.demoNotify;

import java.util.Vector;
/**
 * 
 * @author Jin
 *wait( ): 类似sleep( ), 不同的是，wait( )会先释放锁住的对象，然后再执行等待的动作。
 *注意，这个函数属于Object类。另外，由于wait( )所等待的对象必须先锁住，
 *因此，它只能用在同步化程序段或者同步化方法内，否则，会抛出异常IllegalMonitorStateException.
 */
public class ThreadWaitNotifyTest {
	public static void main(String args[]) {
		Vector obj = new Vector();
		Thread consumer = new Thread(new Consumer(obj));
		Thread producter = new Thread(new Producter(obj));
		consumer.start();
		producter.start();
	}
}

/* 消费者 */
class Consumer implements Runnable {
	private Vector obj;

	public Consumer(Vector v) {
		this.obj = v;
	}

	public void run() {
		synchronized (obj) {
			while (true) {
				try {
					if (obj.size() == 0) {
						obj.wait();
					}
					System.out.println("Consumer:goods have been taken");
					System.out.println("obj size: " + obj.size());
					obj.clear();
					obj.notify();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

/* 生产者 */
class Producter implements Runnable {
	private Vector obj;

	public Producter(Vector v) {
		this.obj = v;
	}

	public void run() {
		synchronized (obj) {
			while (true) {
				try {
					if (obj.size() != 0) {
						obj.wait();
					}

					obj.add(new String("apples"));
					obj.notify();//notify方法就会通知某个正在等待这个对象的控制权的线程可以继续运行。
					System.out.println("Producter:obj are ready");
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
