package prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Prototype模式是一种对象创建型模式，它采 取复制原型对象的方法来创建对象的实例。
 * 
 * 使用 Prototype模式创建的实例，具有与原型一样的 数据。
 * 
 * 1. 由原型对象自身创建目标对象。也就是说，对 象创建这一动作发自原型对象本身。
 * 
 * 2.目标对象是原型对象的一个克隆。也就是说， 通过Prototype模式创建的对象，不仅仅与原型 对象具有相同的结构，还与原型对象具有相同的 值。
 * 
 * 3.根据对象克隆深度层次的不同，有浅度克隆与 深度克隆。
 * 
 * @author Jin
 * 使用原型模式创建对象比直接new一个对象在性能上要好的多，因为Object类的clone方法是一个本地方法，它直接操作内存中的二进制流，特别是复制大对象时，性能的差别非常明显。
 *         - 在创建对象的时候，我们不只是希望被创建的对象继承 其基类的基本结构，还希望继承原型对象的数据。
 * 
 *         - 希望对目标对象的修改不影响既有的原型对象（深度克 隆的时候可以完全互不影响）。
 * 
 *         - 隐藏克隆操作的细节。很多时候，对对象本身的克隆需 要涉及到类本身的数据细节。
 * 
 */
public class MainClass {
	public static void main(String[] args) {
		// Person person1 = new Person();
		// person1.setName("lifengxing");
		// person1.setAge(30);
		// person1.setSex("男");
		//
		//// Person person2 = person1;
		// Person person2 = person1.clone();
		//
		// System.out.println(person1.getName());
		// System.out.println(person1.getAge());
		// System.out.println(person1.getSex());
		//
		// System.out.println(person2.getName());
		// System.out.println(person2.getAge());
		// System.out.println(person2.getSex());

		Person person1 = new Person();
		List<String> friends = new ArrayList<String>();
		friends.add("James");
		friends.add("Yao");

		person1.setFriends(friends);

		Person person2 = person1.clone();

		System.out.println(person1.getFriends());
		System.out.println(person2.getFriends());

		friends.add("Mike");
		person1.setFriends(friends);
		System.out.println(person1.getFriends());
		System.out.println(person2.getFriends());
	}
}
