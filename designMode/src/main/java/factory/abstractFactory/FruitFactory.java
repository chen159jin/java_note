package factory.abstractFactory;

/**
 * 抽象工厂（Creator）角色
 * 
 * 抽象工厂模式的核心，包含对多个产品结构的声明，任何工厂类都必须实现这个接口。
 * 
 * @author Jin
 *
 */
public interface FruitFactory {
	// 实例化Apple
	public Fruit getApple();

	// 实例化Banana
	public Fruit getBanana();
}
