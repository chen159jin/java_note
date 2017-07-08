package factory.simpleFactory.calculator;

public class Minus extends Operation {

	@Override
	public double getResult() {
		return super.numberA - super.numberB;
	}

}
