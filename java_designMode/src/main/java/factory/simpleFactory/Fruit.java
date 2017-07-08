package factory.simpleFactory;
/**
 * 抽象（Product）产品角色：简单工厂模式所创建的所有对象的父类，注意，这里的父类可以是接口也可以是抽象类，它负责描述所有实例所共有的公共接口。
 * @author Jin
 *
 */
public interface Fruit {
	/*
	 * 采集
	 */
	public void get();
}
