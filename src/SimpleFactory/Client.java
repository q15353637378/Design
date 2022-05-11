package SimpleFactory;

/**
 * @Author qinsicheng
 * @Description 内容：简单工厂模式的本质就是面向接口编程  以及  选择合适的实现类
 * @Date 29/04/2022 21:15
 */
public class Client {
    public static void main(String[] args) {
        //在没有使用任何模式下，客户端想要面向接口编程，又必须得知道对应的实现类是谁
        color color_red = new red();
        color color_blue = new blue();  //这不符合要求，我们要暴露的越少也好

        //使用简单工厂模式  我们只需要知道  所需接口和工厂即可 不用管具体的实现类，但是这里的传参说明里面部分信息已经暴露
        color color_r = colorFactory.getColor(1);
        color color_b = colorFactory.getColor(2);

    }
}
//一般简单工厂模式被我们用来做工具类，所以也叫静态工厂，对外提供接口就好
class colorFactory{
    //简单工厂模式的核心是 选择合适的类，该如何选择？通过给定参数或者通过配置文件，但这一定程度又暴露了内部的信息
    static color getColor(int choice) {
        if (choice == 1) {
            return new red();
        } else if (choice ==2){
            return new blue();
        } else {
            return null;
        }
    }
    private colorFactory(){};
}
interface color {
    void paint();
}
class red implements color{
    @Override
    public void paint() {
        System.out.println("printing red.............");
    }
}
class blue implements color{
    @Override
    public void paint() {
        System.out.println("painting blue.......");
    }
}