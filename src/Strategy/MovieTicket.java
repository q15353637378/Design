package Strategy;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 27/01/2022 12:13
 */
public class MovieTicket {
    Discount discount;

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Double calculate(Double price) {
        return discount.calculate(price);
    }
}
abstract class Discount{
    public double calculate(Double price) {
        return price;
    }
}
class StudentDiscount extends Discount{
    @Override
    public double calculate(Double price) {
        //学生八折
        return (double)price*8/10;
    }
}
class VIPDiscount extends Discount{
    @Override
    public double calculate(Double price) {
        //票价折半，并积分
        return (double)price/2;
    }
}
class ChildrenDiscount extends Discount{
    @Override
    public double calculate(Double price) {
        //二十元以上可以减十块
        return (double)price>=20?price-10:price;
    }
}

