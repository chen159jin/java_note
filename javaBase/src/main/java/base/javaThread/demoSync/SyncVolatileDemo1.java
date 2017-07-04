package base.javaThread.demoSync;

import java.util.concurrent.TimeUnit;
/**
 * http://www.importnew.com/18126.html
 * @author Jin
 *volatile关键字能保证可见性没有错，但是上面的程序错在没能保证原子性。
 *可见性只能保证每次读取的是最新的值，但是volatile没办法保证对变量的操作的原子性。
 */
public class SyncVolatileDemo1 {
	public volatile static int inc = 0;

public static void increase() {
   inc++;
}

public static void main(String[] args) {
   final Test test = new Test();
   for(int i=0;i<10;i++){
       new Thread(){
           public void run() {
               for(int j=0;j<1000;j++)
            	   SyncVolatileDemo1.increase();
           };
       }.start();
   }
    
   while(Thread.activeCount()>1)  //保证前面的线程都执行完
       Thread.yield();
   System.out.println(SyncVolatileDemo1.inc);
}
}
