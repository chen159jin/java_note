package obServer;

/**
 * Observer模式的典型应用 
 * 
 * - 侦听事件驱动程序设计中的外部事件 
 * - 侦听/监视某个对象的状态变化 -
 * 发布者/订阅者(publisher/subscriber)模型中，当一个外部事件（新的产品，消息的出现等等）被触发时，通知邮件列表中的订阅者
 * 
 * @author Jin
 *
 */
public class MainClass {
	public static void main(String[] args) {
		Person person = new Person();
		// 注册观察者
		person.addObserver(new MyObServer());
		person.addObserver(new MyObServer());
		System.out.println(person.countObservers());
		// person.deleteObservers();//删除观察者
		person.setName("lifengxing");
		person.setAge(23);
		person.setSex("男");
	}
}
