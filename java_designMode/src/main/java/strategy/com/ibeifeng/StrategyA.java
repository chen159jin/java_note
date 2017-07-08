package strategy.com.ibeifeng;

public class StrategyA implements Strategy{

	public double cost(double num) {
		return num * 0.8;
	}

}
