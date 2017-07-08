package builder;

public class HouseDirector {	
	/*private HouseBuilder builder;
	public HouseDirector(HouseBuilder builder){
		this.builder=builder;
	}
	public void makeHouse( ) {
		builder.makeFloor();
		builder.makeWall();
		builder.makeHousetop();
	}*/
	// 方式2   性能更加高
	public void makeHouse(HouseBuilder builder) {
		builder.makeFloor();
		builder.makeWall();
		builder.makeHousetop();
	}
}
