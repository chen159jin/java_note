package base.javaThread.demoSync;

import java.lang.reflect.Method;

/**
 * 静态方法被synchronized修饰后，该方法是一个
 * 同步静态方法，任何时候都有同步效果。
 * 静态方法上锁的对象是当前类的类对象
 * 类对象(Class类型实例)
 * 每个类在JVM加载时，JVM都会实例化一个Class类型的
 * 实例用于描述加载的这个类，而在JVM内部，每个类都
 * 有且只有一个Class类型的实例与之对应。静态方法锁
 * 的就是这个对象。
 * 
 * @author adminitartor
 *
 */
public class SyncDemo3 {
	public static void main(String[] args){
		Thread t1 = new Thread(){
			public void run(){
				Foo.dosome();
			}
		};
		Thread t2 = new Thread(){
			public void run(){
				Foo.dosome();
			}
		};
		t1.start();
		t2.start();
	}
	
}

class Foo{
	public synchronized static void dosome(){
		Thread t = Thread.currentThread();
		try {
			System.out.println(t.getName()+":正在运行dosome方法");
			Thread.sleep(5000);
			System.out.println(t.getName()+":运行dosome方法完毕!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}








