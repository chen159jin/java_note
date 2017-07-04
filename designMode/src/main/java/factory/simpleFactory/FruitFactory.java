package factory.simpleFactory;
/**
 * 工厂（Creator）角色
 * @author Jin
 *工厂（Creator）角色 简单工厂模式的核心，它负责实现创建所有实例的内部逻辑。工厂类可以被外界直接调用，创建所需的产品对象。
 */
public class FruitFactory {
	//方式一
	/*
	 * 获得Apple类的实例
	 */
	/* public static Fruit getApple() {
	 return new Apple();
	 }*/
	
	 /*
	 * 获得Banana类实例
	 */
	/* public static Fruit getBanana() {
	 return new Banana();
	 }*/
	/*
	 * get方法，获得所有产品对象
	 * 方式二 将Apple和Banana实例合并
	 */
	public static Fruit getFruit(String type) throws InstantiationException, IllegalAccessException {
		/* if(type.equalsIgnoreCase("apple")) {
		 return Apple.class.newInstance();
		
		 } else if(type.equalsIgnoreCase("banana")) {
		 return Banana.class.newInstance();
		 } else {
		 System.out.println("找不到相应的实例化类");
		 return null;
		 }*/
		//方式三  
		Class<?> fruit = null;
		try {
			fruit = Class.forName("factory.simpleFactory."+type);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (Fruit) fruit.newInstance();

	}
}
