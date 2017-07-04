package factory.simpleFactory;
/**
 * 抽象（Product）角色
 * @author Jin
 *简单工厂模式所创建的所有对象的父类，它负责描述所有实例所共有的公共接口。
 */
public interface Fruit {
	/*
	 * 采集
	 */
	public void get();
}
