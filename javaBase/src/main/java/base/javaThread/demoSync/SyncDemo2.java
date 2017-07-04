package base.javaThread.demoSync;
/**
 * 有效的缩小同步范围可以在保证安全的前提下提高
 * 并发效率。
 * 
 * @author adminitartor
 *
 */
public class SyncDemo2 {
	public static void main(String[] args) {
		final Shop shop = new Shop();
		Thread t1 = new Thread(){
			public void run(){
				shop.buy();
			}
		};
		Thread t2 = new Thread(){
			public void run(){
				shop.buy();
			}
		};
		t1.start();
		t2.start();
	}
}

class Shop{
	public void buy(){
		Thread t = Thread.currentThread();
		try {
			System.out.println(t.getName()+":正在挑选衣服...");
			Thread.sleep(5000);
			/*
			 * 使用同步块要注意同步监视器对象(上锁对象)的
			 * 选取：
			 * 1:通常使用this即可
			 * 2:若多个线程调用同一个对象的某些方法时，
			 *   也可以将这个对象上锁。
			 *   比如，在一个方法中多个线程调用同一个
			 *   集合的方法:list.add(xxx),这时可以将
			 *   这个集合list作为上锁的对象。
			 * 原则:多个线程看到的上锁对象是同一个时，
			 *     才有同步效果。  
			 */
			synchronized (this) {
				System.out.println(t.getName()+":正在试衣服...");
				Thread.sleep(5000);
			}		
			
			System.out.println(t.getName()+":结账离开");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}










