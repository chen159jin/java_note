package strategy;
/**
 * ConcreteStrategy    各种策略（算法）的具体实现。
 * @author Jin
 *
 */
public class MD5Strategy implements Strategy {

	public void encrypt() {
		System.out.println("执行MD5加密");
	}

}
