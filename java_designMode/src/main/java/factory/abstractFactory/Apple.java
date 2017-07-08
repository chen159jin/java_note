package factory.abstractFactory;

/**
 * 抽象（Product）角色
 * 
 * 抽象模式所创建的所有对象的父类，它负责描述所有实例所共有的公共接口。
 * 
 * @author Jin
 *
 */
public abstract class Apple implements Fruit {
	/*
	 * 采集
	 */
	public abstract void get();
}
