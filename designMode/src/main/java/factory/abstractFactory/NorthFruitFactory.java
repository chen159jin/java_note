package factory.abstractFactory;

import factory.abstractFactory.concreteProduct.NorthApple;
import factory.abstractFactory.concreteProduct.NorthBanana;

/**
 * 具体工厂（ Concrete Creator）角色
 * 
 * 具体工厂类是抽象工厂的一个实现，负责实例化某个产品族中的产品对象。
 * 
 * 
 * @author Jin
 *
 */
public class NorthFruitFactory implements FruitFactory {

	public Fruit getApple() {
		return new NorthApple();
	}

	public Fruit getBanana() {
		return new NorthBanana();
	}

}
