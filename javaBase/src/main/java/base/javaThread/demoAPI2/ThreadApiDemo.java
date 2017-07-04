package base.javaThread.demoAPI2;
/**
 *获取线程相关信息的一系列方法
 * @author adminitartor
 *
 */
public class ThreadApiDemo {
	public static void main(String[] args) {
		//运行main方法的线程
		Thread t = Thread.currentThread();
		long id = t.getId();
		System.out.println("id:"+id);
		
		String name = t.getName();
		System.out.println("name:"+name);
		
		int priority = t.getPriority();
		System.out.println("priority:"+priority);
		
		boolean isAlive = t.isAlive();
		System.out.println("isAlive:"+isAlive);
		
		boolean isDaemon = t.isDaemon();
		System.out.println("isDaemon:"+isDaemon);
		
		boolean isInterrupted = t.isInterrupted();
		System.out.println("isInterrupted:"+isInterrupted);
		
	}
}






