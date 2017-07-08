package factory.simpleFactory;

/**
 * 简单工厂模式（Simple Factory Pattern）
 * 
 * 属于类的创新型模式，又叫静态工厂方法模式（Static FactoryMethod
 * Pattern）,是通过专门定义一个类来负责创建其他类的实例，被创建的实例通常都具有共同的父类。
 * 
 * 优点：工厂类是整个模式的关键所在。它包含必要的判断逻辑，能够根据外界给定的信息，决定究竟应该创建哪个具体类的对象。
 * 用户在使用时可以直接根据工厂类去创建所需的实例，而无需了解这些对象是如何创建以及如何组织的。有利于整个软件体系结构的优化。
 * 
 * 缺点：由于工厂类集中了所有实例的创建逻辑，这就直接导致一旦这个工厂出了问题，所有的客户端都会受到牵连；
 * 而且由于简单工厂模式的产品室基于一个共同的抽象类或者接口，这样一来，但产品的种类增加的时候，即有不同的产品接口或者抽象类的时候，
 * 工厂类就需要判断何时创建何种种类的产品，这就和创建何种种类产品的产品相互混淆在了一起，违背了单一职责，导致系统丧失灵活性和可维护性。而且更重要的是，
 * 简单工厂模式违背了“开放封闭原则”，就是违背了“系统对扩展开放，对修改关闭”的原则，因为当我新增加一个产品的时候必须修改工厂类，
 * 相应的工厂类就需要重新编译一遍。
 * 
 * @author Jin
 * 
 *         简单工厂模式解决的问题是如何去实例化一个合适的对象。 简单工厂模式的核心思想就是：有一个专门的类来负责创建实例的过程。
 *         具体来说，把产品看着是一系列的类的集合，这些类是由某个抽象类或者接口派生出来的一个对象树。而工厂类用来产生一个合适的对象来满足客户的要求。
 *         如果简单工厂模式所涉及到的具体产品之间没有共同的逻辑，那么我们就可以使用接口来扮演抽象产品的角色；如果具体产品之间有功能的逻辑或，
 *         我们就必须把这些共同的东西提取出来，放在一个抽象类中，然后让具体产品继承抽象类。为实现更好复用的目的，共同的东西总是应该抽象出来的。
 */
public class MainClass {
	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
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

		Fruit apple = FruitFactory.getFruit("Apple");
		Fruit banana = FruitFactory.getFruit("Banana");
		apple.get();
		banana.get();

	}
}
