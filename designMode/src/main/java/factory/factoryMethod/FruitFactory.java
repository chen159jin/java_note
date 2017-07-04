package factory.factoryMethod;

/**
 * 抽象工厂（Creator）角色工厂方法模式的核心，任何工厂类都必须实现这个接口。
 * @author Jin
 *
 */
public interface FruitFactory {
	public Fruit getFruit();
}
