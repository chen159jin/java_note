package base.javaThread.demoAPI1;

/**
 * Created by brian on 2016/4/11.
 */

/**
 * P25
 * 判断线程是否停止状态
 * 测试当前线程是否已经中断
 * 
如果线程中有Thread.sleep方法，当设置中断后，执行这个方法会抛出异常，就务必在异常中继续关闭线程

 * 
（1）interrupt：置线程的中断状态
（2）isInterrupt：线程是否中断
（3）interrupted：返回线程的上次的中断状态，并清除中断状态
 */
class MyThread8 extends Thread {
    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 500; i++) {
            try {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().isInterrupted());
              //  Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().isInterrupted());
            } catch (InterruptedException e) {
                e.printStackTrace();
               // Thread.currentThread().interrupt();
            }
            System.out.println("i=" + (i + 1));
        }
    }
}

public class Run8_interrupted01 {
    public static void main(String[] args) {
        try {
            MyThread8 myThread8 = new MyThread8();
            myThread8.start();
            Thread.sleep(1000);
            myThread8.interrupt();
            System.out.println("Thread.interrupted()，是否停止1？=" + Thread.interrupted());
            System.out.println("Thread.interrupted()，是否停止2？=" + Thread.interrupted());
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end");
    }
}

/*
输出：
i=1
i=2
i=3
i=4
i=5
i=6
i=7
i=8
i=9
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at com.brianway.learning.java.multithread.meet.MyThread8.run(Run8_interrupted01.java:18)
Thread.interrupted()，是否停止1？=false
Thread.interrupted()，是否停止2？=false
end
i=10
i=11
省略....

-----------------


 */