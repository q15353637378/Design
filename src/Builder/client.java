package Builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 03/05/2022 15:49
 */
public class client {
    public static void main(String[] args) {
        ExportHeaderModel ehm = new ExportHeaderModel("12", "2022-03-05");
        ExportFooterModel efm = new ExportFooterModel("QSC");
        HashMap<String, Collection<ExportBodyMode>> map = new HashMap<>();
        ArrayList<ExportBodyMode> list = new ArrayList<>();
        list.add(new ExportBodyMode("1",12));
        list.add(new ExportBodyMode("2",14));

        ArrayList<ExportBodyMode> list1 = new ArrayList<>();
        list.add(new ExportBodyMode("3",16));
        list.add(new ExportBodyMode("4",18));
        map.put("一号",list);
        map.put("二号",list1);

        BuildToText buildToText = new BuildToText();
        Director director = new Director(buildToText);
        director.construct(ehm,map,efm);
        System.out.println(buildToText.getStringBuilder());
    }
}
class ExportHeaderModel{
    String depID;
    String exportData;

    public ExportHeaderModel(String depID, String exportData) {
        this.depID = depID;
        this.exportData = exportData;
    }
}
class ExportBodyMode{
    String productID;
    int price;

    public ExportBodyMode(String productID, int price) {
        this.productID = productID;
        this.price = price;
    }
}
class ExportFooterModel{
    String exportUser;

    public ExportFooterModel(String exportUser) {
        this.exportUser = exportUser;
    }
}

/*原始方法*/
class ExportToTxt{
    StringBuilder stringBuilder = new StringBuilder();
    void export(ExportHeaderModel ehm, Map<String, Collection<ExportBodyMode>> ebm,ExportFooterModel efm) {
        //拼接文件头
        stringBuilder.append(ehm.depID+"---"+ehm.exportData+"\n");
        //拼接文件体
        for (String tblName : ebm.keySet()) {
            stringBuilder.append(tblName+"\n");
            for (ExportBodyMode edm : ebm.get(tblName)) {
                stringBuilder.append(edm.productID+","+edm.price+"\n");
            }
        }
        //拼接文件尾
        stringBuilder.append(efm.exportUser);

        //输入文件
        System.out.println(stringBuilder);
    }
}
class ExportToXml{
    StringBuilder stringBuilder = new StringBuilder();

    void export(ExportHeaderModel ehm,Map<String,Collection<ExportBodyMode>> ebm,ExportFooterModel efm){
        //代表XMl的不同
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE configuration\n" +
                "        PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n" +
                "        \"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n" +
                "<configuration>");
        //拼接文件头
        stringBuilder.append(ehm.depID+"---"+ehm.exportData+"\n");
        //拼接文件体
        for (String tblName : ebm.keySet()) {
            stringBuilder.append(tblName+"\n");
            for (ExportBodyMode edm : ebm.get(tblName)) {
                stringBuilder.append(edm.productID+","+edm.price+"\n");
            }
        }
        //拼接文件尾
        stringBuilder.append(efm.exportUser);

        //输入文件
        System.out.println(stringBuilder);
    }
}

/*利用生成器*/
class Director{
    private Builder builder;

    void construct(ExportHeaderModel ehm, Map<String, Collection<ExportBodyMode>> ebm,ExportFooterModel efm) {
        builder.buildHead(ehm);
        builder.buildBody(ebm);
        builder.buildFood(efm);
    }

    public Director(Builder builder) {
        this.builder = builder;
    }
}

interface Builder{
    void buildHead(ExportHeaderModel ehm);
    void buildBody(Map<String,Collection<ExportBodyMode>> map);
    void buildFood(ExportFooterModel efm);
}

class BuildToText implements Builder{
    StringBuilder stringBuilder = new StringBuilder();
    @Override
    public void buildHead(ExportHeaderModel ehm) {
        stringBuilder.append(ehm.depID+"---"+ehm.exportData+"\n");
    }

    @Override
    public void buildBody(Map<String, Collection<ExportBodyMode>> ebm) {
        for (String tblName : ebm.keySet()) {
            stringBuilder.append(tblName+"\n");
            for (ExportBodyMode edm : ebm.get(tblName)) {
                stringBuilder.append(edm.productID+","+edm.price+"\n");
            }
        }
    }

    @Override
    public void buildFood(ExportFooterModel efm) {
        stringBuilder.append(efm.exportUser);
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }
}

class BuildToXML implements Builder{
    StringBuilder stringBuilder = new StringBuilder();
    @Override
    public void buildHead(ExportHeaderModel ehm) {
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE configuration\n" +
                "        PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n" +
                "        \"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n" +
                "<configuration>");

        stringBuilder.append(ehm.depID+"---"+ehm.exportData+"\n");
    }

    @Override
    public void buildBody(Map<String, Collection<ExportBodyMode>> ebm) {
        for (String tblName : ebm.keySet()) {
            stringBuilder.append(tblName+"\n");
            for (ExportBodyMode edm : ebm.get(tblName)) {
                stringBuilder.append(edm.productID+","+edm.price+"\n");
            }
        }
    }

    @Override
    public void buildFood(ExportFooterModel efm) {
        stringBuilder.append(efm.exportUser);
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }
}