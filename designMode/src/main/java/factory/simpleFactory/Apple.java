package factory.simpleFactory;
/**
 * 具体产品（Concrete Product）角色
 * 简单工厂模式所创建的具体实例对象
 * @author Jin
 *
 */
public class Apple implements Fruit{
	/*
	 * 采集
	 */
	public void get(){
		System.out.println("采集苹果");
	}
}
