package FactoryMethod;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 24/01/2022 13:39
 */
public class Client {
    public static void main(String[] args) {
        ExportOperate operate = new ExportDBOperate();
        operate.export("I love you");
    }
}
abstract class ExportOperate{
    boolean export(String data) {
        ExportFileApi api = factoryMethod();
        return api.export(data);
    }
    abstract ExportFileApi factoryMethod();
}

interface ExportFileApi {
    boolean export(String data);
}

class ExportTxtFileOperate extends ExportOperate{
    @Override
    ExportFileApi factoryMethod() {
        return new ExportTextFile();
    }
}

class ExportDBOperate extends ExportOperate{
    @Override
    ExportFileApi factoryMethod() {
        return new ExportDB();
    }
}

class ExportTextFile implements ExportFileApi {
    @Override
    public boolean export(String data) {
        System.out.println("正在输入到文本文件中");
        return true;
    }
}

class ExportDB implements ExportFileApi {
    @Override
    public boolean export(String data) {
        System.out.println("正在输入到数据库中");
        return true;
    }
}