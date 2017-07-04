package base.javaThread.demoAPI1;
/**
 * 
 * @author Jin
 *public void Thread.interrupt() // 中断线程
  public boolean Thread.isInterrupted() // 判断是否被中断
  public static boolean Thread.interrupted() // 判断是否被中断，并清除当前中断状态
    但在Java里Thread.interrupt()方法实际上通过某种方式通知线程，并不会直接中止该线程。具体做什么事情由写代码的人决定，通常我们会中止该线程。
 */
public class ThreadInterrupt extends Thread{
	public static void main(String[] args) {
		ThreadInterrupt th = new ThreadInterrupt();
		th.start();
		
		th.interrupt();
	}
	public void run(){
		int i=0;
		while(true){
			System.out.println(Thread.currentThread().getName()+";"+i++);
			if(Thread.currentThread().isInterrupted()){
			System.out.println("Interruted!");
			break;
			}
			try {
			Thread.sleep(200);//休眠时间
			} catch (InterruptedException e) {
			System.out.println("Interruted When Sleep");
			//设置中断状态，抛出异常后会清除中断标记位
			Thread.currentThread().interrupt();
			}
			Thread.yield();
			}
	}
}
