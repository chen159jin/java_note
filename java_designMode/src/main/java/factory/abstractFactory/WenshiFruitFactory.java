package factory.abstractFactory;

import factory.abstractFactory.concreteProduct.WenshiApple;
import factory.abstractFactory.concreteProduct.WenshiBanana;

public class WenshiFruitFactory implements FruitFactory {

	public Fruit getApple() {
		return new WenshiApple();
	}

	public Fruit getBanana() {
		return new WenshiBanana();
	}

}
