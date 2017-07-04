package base.javaThread.demoAPI1;

/**
 * Created by brian on 2016/4/11.
 */

/**
 * P23
 * 停止线程
 */

class MyThread7 extends Thread {
    @Override
    public void run() {
        super.run(); System.out.println(this.getName());
        for (int i = 0; i < 500000; i++) {
            //System.out.println("i=" + (i + 1)+";"+this.currentThread().getName());
        }
    }
}

public class Run07_interrupt01 {
    public static void main(String[] args) {
        try {
            MyThread7 myThread7 = new MyThread7();
            myThread7.start();
            Thread.sleep(10);
            System.out.println(myThread7.currentThread().getName());
            myThread7.interrupt();
             
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }

    }
}
