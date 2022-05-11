package SingleTon;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 30/04/2022 11:51
 */
public class client {
    public static void main(String[] args) {
        for (int i = 0; i < 40; i++) {

            new Thread(()->{
                System.out.println(thirdTon.getInstance());
            }).start();
        }
    }
}

//懒汉式  你要我再创建  缺点：要么使用syc加锁，效率低，要么使用双锁检测 volatile 效率低
//时间换空间
class singleTon_lazy {
    private volatile static singleTon_lazy instance = null;

    //双重检查机制
    public static singleTon_lazy getInstance() {
        if (instance == null) {
            synchronized (singleTon_lazy.class) {
                if (instance == null) {
                    instance = new singleTon_lazy();
                }
            }
        }
        return instance;
    }

    private singleTon_lazy() {

    }


}

//饿汉式 我一开始在系统加载时就进行创建  空间换时间
class singleTon_hunger {
    public static singleTon_hunger instance = new singleTon_hunger();

    public static singleTon_hunger getInstance() {
        return instance;
    }

    private singleTon_hunger() {
    }

    ;
}

//能不能即实现懒加载又能提高效率  使用类级内部类 效率最高
class singleTon_static {

    private static class innerClass {
        private static singleTon_static instance = new singleTon_static();
    }

    private singleTon_static() {
    }

    ;

    public static singleTon_static getInstance() {
        return innerClass.instance;
    }
}

//枚举在单例上更加简洁
enum singleTon_enum {
    uniqueInstance;

    public void singleTonOperation() {
        //功能处理
    }
}

//现在我们通过单例模式进行推广，能不能让系统里只存在3个该对象，这里不管对象间的调配算法
class thirdTon {
    //通过一个集合将多个对象进行保存，比如说用MAP
    private static final String DEFAULT_KEY = "Cache";

    private static Map<String, thirdTon> map = new HashMap<>();

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static thirdTon getInstance() {
        int time = randomGet() % 3;//0-2
        if (map.get(DEFAULT_KEY + time) == null) {
            synchronized (thirdTon.class) {
                if (map.get(DEFAULT_KEY + time) == null) {
                    map.put(DEFAULT_KEY + time, new thirdTon());
                }
            }
        }
        return map.get(DEFAULT_KEY + time);
    }

    private static Integer randomGet() {
        Integer cur;
        Integer next;
        do {
            cur = atomicInteger.get();
            next = cur + 1;
        } while (!atomicInteger.compareAndSet(cur, next));
        return next;
    }

    private thirdTon() {
    }
}
