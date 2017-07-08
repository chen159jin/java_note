package builder;

/**
 * 对象的创建：Builder模式是为对象的创建而设计的模式
 * 
 * 建造者模式：将一个复杂的对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
 * 
 * 创建的是一个复合对象：被创建的对象为一个具有复合属性的复合对象
 * 
 * 关注对象创建的各部分的创建过程：不同的工厂（这里指builder生成器）对产品属性有不同的创建方法
 * 
 * @author Jin
 * 
 *         抽象工厂或者简单工厂 一个函数就可以创造一个对象 建造者模式是创造复杂对象时候的一种分解方式
 */
public class MainClass {

	public static void main(String[] args) {
		// //客户直接造房子
		// House house = new House();
		// house.setFloor("地板");
		// house.setWall("墙");
		// house.setHousetop("屋顶");

		// 由工程队来修
		/*
		 * HouseBuilder builder = new PingFangBuilder(); // 调用工程队
		 * builder.makeFloor(); builder.makeHousetop(); builder.makeWall(); //
		 * HouseDirector director = new HouseDirector(); //
		 * director.makeHouse(builder);
		 * 
		 * House house = builder.getHouse();
		 * System.out.println(house.getFloor());
		 * System.out.println(house.getWall());
		 * System.out.println(house.getHousetop());
		 */
		// 由工程队来修
		HouseBuilder builder = new GongyuBuilder();
		// 设计者来做
		/*
		 * HouseDirector director = new HouseDirector(builder);
		 */ // 方式2
		HouseDirector director = new HouseDirector();
		director.makeHouse(builder);

		House house = builder.getHouse();
		System.out.println(house.getFloor());
		System.out.println(house.getWall());
		System.out.println(house.getHousetop());
	}

}
