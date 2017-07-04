package base.javaThread.demoBase;

public class MyRunnable implements Runnable {
	//volatile保证了线程可以正确的读取其他线程写入的值
		//可见性 ref JMM， happens-before原则
		volatile boolean keepRunning = true;

	public void run() {
		while(keepRunning){
			//发动5连击
			System.out.println("runnable:"+Thread.currentThread().getName());
			for(int i=0;i<5;i++){
				System.out.println(Thread.currentThread().getName()+"进攻对方["+i+"]");
				//让出了处理器时间，下次该谁进攻还不一定呢！
				Thread.yield();
				//yield()从未导致线程转到等待/睡眠/阻塞状态。在大多数情况下，
				//yield()将导致线程从运行状态转到可运行状态，但有可能没有效果。
			}
		}
		System.out.println(Thread.currentThread().getName()+"结束了战斗！");


	}

}
