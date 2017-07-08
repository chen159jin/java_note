package factory.simpleFactory.calculator;

public class OperationFactory {
	public static Operation createOperate(String operate) {  
        Operation oper = null;  
        switch (operate) {  
            case "+":  
                oper = new Add();  
                break;  
            case "-":  
                oper = new Minus();  
                break;  
            case "*":  
                oper = new Multiply();  
                break;  
            case "/":  
                oper = new Divide();  
                break;  
        }  
        return oper;  
    }  
}
