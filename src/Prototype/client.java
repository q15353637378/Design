package Prototype;

import java.util.*;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 04/05/2022 09:00
 */
public class client {
    public static void main(String[] args) {
        OrderAccessSystem system = new OrderAccessSystem();
        for (int i = 0; i < 21; i++) {
            Order order = new Order(i);
            system.increase(order);
        }

        Map<String, List<Order>> map = system.getMap();
        System.out.println("订单分类数："+map.size());
        for (Map.Entry<String, List<Order>> entry : map.entrySet()) {
            List<Order> value = entry.getValue();
            System.out.println(entry.getKey());
            for (Order order : value) {
                System.out.print(order+"\t");
            }
        }
    }
}
class OrderAccessSystem{
    Map<String, List<Order>> map = null;
    int time = 0;

    public OrderAccessSystem() {
        map = new HashMap<>();
        map.put(0+"",new ArrayList<>());
    }

    public void increase(Order order) {
        List<Order> orders = map.get(time+"");

        if (orders.size()+1>10) {
            time++;
            ArrayList<Order> list = new ArrayList<>();
            list.add(order);
            map.put(time+"",list);
        } else {
            orders.add(order);
        }
    }

    public Map<String, List<Order>> getMap() {
        return map;
    }
}
class Order{
    int id;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                '}';
    }

    public Order(int id) {
        this.id = id;
    }
}