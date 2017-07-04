package base.javaThread.demoAPI1;

public class JoinTest3 {
	public static void main(String[] args) {
		Thread t = new Thread(new RunnableImpl3());
		new ThreadTest(t).start();
		t.start();
		try {
			t.join();
			System.out.println("joinFinish");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadTest extends Thread {

	Thread thread;

	public ThreadTest(Thread thread) {
		this.thread = thread;
	}

	@Override
	public void run() {
		synchronized (thread) {
			System.out.println("getObjectLock"+ Thread.currentThread().getName());
			try {
				Thread.sleep(9000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			System.out.println("ReleaseObjectLock");
		}
	}
}

class RunnableImpl3 implements Runnable {

	public void run() {
		try {
			System.out.println("Begin sleep"+ Thread.currentThread().getName());
			Thread.sleep(2000);
			System.out.println("End sleep");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}