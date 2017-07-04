package base.javathis;
/**
 *   1） this 关键字是类内部当中对自己的一个引用，可以方便类中方法访问自己的属性；

   2）可以返回对象的自己这个类的引用，同时还可以在一个构造函数当中调用另一个构造函数。
 * @author Jin
 *
 */
public class ThisDemo {  
    String name;
    int age;
    public ThisDemo (){ 
        this.age=21;
   }     
    public ThisDemo(String name,int age){
        this();
        this.name="Mick";
    }     
  private void print(){
         System.out.println("最终名字="+this.name);
         System.out.println("最终的年龄="+this.age);
    }
    public static void main(String[] args) {
       ThisDemo tt=new ThisDemo("",0); //随便传进去的参数
       tt.print();
    }
}
