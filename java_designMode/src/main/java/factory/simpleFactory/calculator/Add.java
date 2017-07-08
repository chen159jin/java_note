package factory.simpleFactory.calculator;

public class Add extends Operation {
	
	public double getResult() {
		return super.numberA + super.numberB;
	}

}
