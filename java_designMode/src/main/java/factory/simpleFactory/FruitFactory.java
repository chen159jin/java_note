package factory.simpleFactory;

/**
 * 工厂角色（Creator）：这是简单工厂模式的核心，由它负责创建所有的类的内部逻辑。
 * 当然工厂类必须能够被外界调用，创建所需要的产品对象。
 * 
 * @author Jin
 *
 */
public class FruitFactory {
	// /*
	// * 获得Apple类的实例
	// */
	// public static Fruit getApple() {
	// return new Apple();
	// }
	//
	// /*
	// * 获得Banana类实例
	// */
	// public static Fruit getBanana() {
	// return new Banana();
	// }
	/*
	 * get方法，获得所有产品对象
	 */
	public static Fruit getFruit(String type)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// if(type.equalsIgnoreCase("apple")) {
		// return Apple.class.newInstance();
		//
		// } else if(type.equalsIgnoreCase("banana")) {
		// return Banana.class.newInstance();
		// } else {
		// System.out.println("找不到相应的实例化类");
		// return null;
		// }
		Class fruit = Class.forName(type);
		return (Fruit) fruit.newInstance();

	}
}
