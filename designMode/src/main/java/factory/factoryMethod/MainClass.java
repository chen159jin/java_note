package factory.factoryMethod;

/**
 * 工厂方法模式同样属于类的创建型模式又被称 为多态工厂模式 。工厂方法模式的意义是定义一个创建 产品对象的工厂接口，将实际创建工作推迟到子类当中。
 * 核心工厂类不再负责产品的创建，这样核心类成为一个 抽象工厂角色，仅负责具体工厂子类必须实现的接口， 这样进一步抽象化的好处是使得工厂方法模式可以使系
 * 统在不修改具体工厂角色的情况下引进新的产品。
 * 
 * 比较
 *
 * 工厂方法模式与简单工厂模式在结构上的不同不是很明显。工厂方 法类的核心是一个抽象工厂类，而简单工厂模式把核心放在一个具 体类上。
 * 工厂方法模式之所以有一个别名叫多态性工厂模式是因为具体工 厂类都有共同的接口，或者有共同的抽象父类。
 * 
 * 优缺点
 * 
 * 当系统扩展需要添加新的产品对象时，仅仅需要添加一个具体对 象以及一个具体工厂对象，原有工厂对象不需要进行任何修改，也
 * 不需要修改客户端，很好的符合了“开放－封闭”原则。而简单工厂 模式在添加新产品对象后不得不修改工厂方法，扩展性不好。
 * 工厂方法模式退化后可以演变成简单工厂模式。
 * 
 * 
 * @author Jin
 *
 * 
 * 
 */
public class MainClass {
	public static void main(String[] args) {
		// 获得AppleFactory
		FruitFactory ff = new AppleFactory();
		// 通过AppleFactory来获得Apple实例对象
		Fruit apple = ff.getFruit();
		apple.get();

		// 获得BananaFactory
		FruitFactory ff2 = new BananaFactory();
		Fruit banana = ff2.getFruit();
		banana.get();

		// 获得PearFactory
		FruitFactory ff3 = new PearFactory();
		Fruit pear = ff3.getFruit();
		pear.get();
	}
}
