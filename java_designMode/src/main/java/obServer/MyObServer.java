package obServer;

import java.util.Observable;
import java.util.Observer;

/**
 * Observer（观察者）     接口或抽象类。当Subject的状态发生变化时，Observer对象将通过一个callback函数得到通知。
 * 
 * ConcreteObserver     观察者的具体实现。得到通知后将完成一些具体的业务逻辑处理。
 * 
 * 
 * @author Jin
 *
 */
public class MyObServer implements Observer {

	public void update(Observable o, Object arg) {
		System.out.println("对象发生变化");
	}
}
