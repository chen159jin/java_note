package factory.abstractFactory.concreteProduct;

import factory.abstractFactory.Apple;

/**
 * 具体产品（Concrete Product）角色
 * 
 * 抽象模式所创建的具体实例对象
 * 
 * @author Jin
 *
 */
public class NorthApple extends Apple {

	public void get() {
		System.out.println("采集北方苹果");
	}

}
