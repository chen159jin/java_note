package base.javathis;
/**
 * 通过this 这个关键字返回自身这个对象然后在一条语句里面实现多次的操作，还是贴出来。
 * @author Jin
 *
 */
public class ThisDemo1 {  
    int number;
    ThisDemo1 increment(){
         number++;
         return this;
    }  
  private void print(){
         System.out.println("number="+number);
    }
    public static void main(String[] args) {
        ThisDemo1 tt=new ThisDemo1();
         tt.increment().increment().increment().print();
    }
}