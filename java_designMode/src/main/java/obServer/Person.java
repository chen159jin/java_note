package obServer;
import java.util.Observable;
/**
 * Subject（被观察者）    被观察的对象。当需要被观察的状态发生变化时，需要通知队列中所有观察者对象。Subject需要维持（添加，删除，通知）一个观察者对象的队列列表。
 * 
 * ConcreteSubject    被观察者的具体实现。包含一些基本的属性状态及其他操作。
 * @author Jin
 *
 */
public class Person extends Observable {
	private String name;
	private String sex;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.setChanged();
		this.notifyObservers();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
		this.setChanged();
		this.notifyObservers();
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.setChanged();
		this.notifyObservers();
		this.age = age;
	}

}
