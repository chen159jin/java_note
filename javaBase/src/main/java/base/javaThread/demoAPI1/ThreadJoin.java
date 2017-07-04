package base.javaThread.demoAPI1;
/**
 * 
 * @author Jin
 *
 */
public class ThreadJoin {
	public static void main(String[] args) {
		Thread t = new Thread(new RunnableImpl());
		t.start();
		try {
			t.join();      //使调用线程 t 执行完毕后执行完毕。
			//t.join(1000);  //等待 t 线程，等待时间是1000毫秒
			//t.join(2000); // 主线程只等2 秒，不管子线程什么时候结束
			System.out.println("joinFinish"+","+Thread.currentThread().getName());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class RunnableImpl implements Runnable {
	@Override
	public void run() {
		try {
			System.out.println("Begin sleep"+","+Thread.currentThread().getName());
			Thread.sleep(10000);
			System.out.println("End sleep"+","+Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
