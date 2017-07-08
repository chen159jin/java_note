package strategy;
/**
 * ConcreteStrategy    各种策略（算法）的具体实现。
 * @author Jin
 *
 */
public class MDSStrategy implements Strategy{


	public void encrypt() {
		System.out.println("执行MDS加密");
	}

}
