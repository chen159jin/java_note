package factory.simpleFactory;
/**
 *  具体产品（Concrete Product）角色：简单工厂所创建的具体实例对象，这些具体的产品往往都拥有共同的父类。
 * @author Jin
 *
 */
public class Banana implements Fruit{
	/*
	 * 采集
	 */
	public void get(){
		System.out.println("采集香蕉");
	}
}
