package factory.simpleFactory.calculator;

import java.util.Scanner;

public class MianClass {
	public static void main(String[] args) {
		System.out.println("---计算器程序---");
		System.out.println("输入第一个操作数");
		Scanner scanner = new Scanner(System.in);
		String strNum1 = scanner.nextLine();
		
		System.out.println("输入运算符");
		String opera = scanner.nextLine();
		
		System.out.println("输入第二个操作数");
		String strNum2 = scanner.nextLine();
		Operation oper;  
		
        oper = OperationFactory.createOperate(opera);  
        oper.setNumberA(Double.parseDouble(strNum1));  
        oper.setNumberB(Double.parseDouble(strNum2));  
        double result = oper.getResult();  
        System.out.println(result);  
	}
}
