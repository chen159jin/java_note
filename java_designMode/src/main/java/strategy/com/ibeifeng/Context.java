package strategy.com.ibeifeng;

public class Context {
	private Strategy strategy;
	
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public double cost(double num){
		return this.strategy.cost(num);
	}
	
}
