package Strategy;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 27/01/2022 12:29
 */
public class Client {
    public static void main(String[] args) {
        Discount bean = (Discount)XMLUtil.getBean();
        MovieTicket movieTicket = new MovieTicket();
        movieTicket.setDiscount(bean);
        Double calculate = movieTicket.calculate(100.0);
        System.out.println(calculate);
    }
}
