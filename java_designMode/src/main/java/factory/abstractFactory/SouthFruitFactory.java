package factory.abstractFactory;

import factory.abstractFactory.concreteProduct.SouthApple;
import factory.abstractFactory.concreteProduct.SouthBanana;

public class SouthFruitFactory implements FruitFactory {

	public Fruit getApple() {
		return new SouthApple();
	}

	public Fruit getBanana() {
		return new SouthBanana();
	}

}
