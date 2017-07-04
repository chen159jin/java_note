package factory.abstractFactory;

/**
 * 抽象工厂模式是所有形态的工厂模式中最为抽 象和最其一般性的。抽象工厂模式可以向客户端 提供一个接口，使得客户端在不必指定产品的具
 * 体类型的情况下，能够创建多个产品族的产品对 象。
 * 
 * 优缺点
 * 
 * 抽象工厂模式除了具有工厂方法模式的优点外，最主要的优点就是可以在类的内部对产品族进行约束。所谓的产品族，一般或多或少的都存在一定的关联，
 * 抽象工厂模式就可以在类内部对产品族的关联关系进行定义和描述，而不必专门引入一个新的类来进行管理。
 * 
 * 产品族的扩展将是一件十分费力的事情，假如产品族中需要增加一个新的产品，则几乎所有的工厂类都需要进行修改。所以使用抽象工厂模式时，
 * 对产品等级结构的划分是非常重要的。
 * 
 * @author Jin
 *
 */
public class MainClass {
	public static void main(String[] args) {
		FruitFactory ff = new NorthFruitFactory();
		Fruit apple = ff.getApple();
		apple.get();

		Fruit banana = ff.getBanana();
		banana.get();

		FruitFactory ff2 = new SouthFruitFactory();
		Fruit apple2 = ff2.getApple();
		apple2.get();

		Fruit banana2 = ff2.getBanana();
		banana2.get();

		FruitFactory ff3 = new WenshiFruitFactory();
		Fruit apple3 = ff3.getApple();
		apple3.get();

		Fruit banana3 = ff3.getBanana();
		banana3.get();
	}
}
