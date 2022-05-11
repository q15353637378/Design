package AbstractFactory;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 25/01/2022 10:47
 */
public class Client {
    public static void main(String[] args) {
        SkinFactory bean = (SkinFactory)XmlUtil.getBean();
        bean.createButton();
        bean.createComboBox();
        bean.createTestField();
    }
}
//抽象产品
interface Button{
    public void display();
}
interface TestField{
    public void display();
}
interface ComboBox{
    public void display();
}
//具体产品
class SpringButton implements Button{
    @Override
    public void display() {
        System.out.println("展示绿色按钮");
    }
}
class SummerButton implements Button{
    @Override
    public void display() {
        System.out.println("展示蓝色按钮");
    }
}
class SpringTestField implements TestField{
    @Override
    public void display() {
        System.out.println("展示绿色文本框");
    }
}
class SummerTestField implements TestField{
    @Override
    public void display() {
        System.out.println("展示蓝色文本框");
    }
}
class SpringComboBox implements ComboBox{
    @Override
    public void display() {
        System.out.println("展示绿色文本框");
    }
}
class SummerComboBox implements ComboBox{
    @Override
    public void display() {
        System.out.println("展示蓝色文本框");
    }
}
//抽象工厂
interface SkinFactory{
    public void createButton();
    public void createTestField();
    public void createComboBox();
}
//具体工厂
class SpringSkinFactory implements SkinFactory {
    @Override
    public void createButton() {
        Button button = new SpringButton();
        button.display();
    }

    @Override
    public void createTestField() {
        TestField testField =  new SpringTestField();
        testField.display();
    }

    @Override
    public void createComboBox() {
        ComboBox comboBox = new SpringComboBox();
        comboBox.display();
    }
}
class SummerSkinFactory implements SkinFactory {
    @Override
    public void createButton() {
        Button button = new SummerButton();
        button.display();
    }

    @Override
    public void createTestField() {
        TestField testField = new SummerTestField();
        testField.display();
    }

    @Override
    public void createComboBox() {
        ComboBox comboBox = new SummerComboBox();
        comboBox.display();
    }
}
