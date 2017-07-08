package singleton;


public class Person {
	//饿汉模式     对于多线程能保证单例
	//加 public static final Person person = new Person();
	public static final Person person = new Person();
	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//构造函数私有化
	private Person() {
	}
	//提供一个全局的静态方法
//		public static Person getPerson() {
//			return new Person();
//		}
		
	//提供一个全局的静态方法
	public static Person getPerson() {
		return person;
	}
}
