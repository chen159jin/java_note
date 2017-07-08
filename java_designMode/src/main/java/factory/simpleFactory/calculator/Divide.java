package factory.simpleFactory.calculator;

public class Divide extends Operation {

	@Override
	public double getResult() {
		if(super.numberB!=0)
			 throw new ArithmeticException("除数不能为0."); 
		double result = super.numberA / super.numberB;
		return result;
	}

}
