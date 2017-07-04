package factory.simpleFactory;

/**
 * 
 * 简单工厂模式属于类的创建型模式,又叫做静态 工厂方法模式。 通过专门定义一个类来负责创建 其他类的实例，被创建的实例通常都具有共同的父类。
 * 
 * 优缺点
 * 
 * 工厂类是整个模式的关键所在。它包含必要的判断 逻辑，能够根据外界给定的信息，决定究竟应该创建哪个具体类的
 * 对象。用户在使用时可以直接根据工厂类去创建所需的实例，而无 需了解这些对象是如何创建以及如何组织的。 有利于整个软件体系 结构的优化。
 * 
 * 简单工厂模式的缺点也正体现在其工厂类上，由于工厂类集中 了所有实例的创建逻辑，所以“高内聚”方面做的并不好。另外，当系统中的
 * 具体产品类不断增多时，可能会出现要求工厂类也要做相应的修改，扩展 性并不很好。
 * 
 * @author Jin
 * 
 * 
 */
public class MainClass {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		// //实例化一个Apple
		// Apple apple = new Apple();
		// //实例化一个Banana
		// Banana banana = new Banana();
		//
		// apple.get();
		// banana.get();

		// //实例化一个Apple,用到了多态
		// Fruit apple = new Apple();
		// Fruit banana = new Banana();
		// apple.get();
		// banana.get();

		// //实例化一个Apple
		// Fruit apple = FruitFactory.getApple();
		// Fruit banana = FruitFactory.getBanana();
		// apple.get();
		// banana.get();

		/*
		 * Fruit apple = FruitFactory.getApple(); Fruit banana =
		 * FruitFactory.getBanana(); apple.get(); banana.get();
		 */

		// 方式三
		Fruit apple2 = FruitFactory.getFruit("Apple");
		Fruit banana2 = FruitFactory.getFruit("Banana");
		apple2.get();
		banana2.get();

	}
}
