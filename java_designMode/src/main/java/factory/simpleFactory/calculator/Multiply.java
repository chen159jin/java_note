package factory.simpleFactory.calculator;

public class Multiply extends Operation {

	@Override
	public double getResult() {
		return super.numberA * super.numberB;
	}

}
