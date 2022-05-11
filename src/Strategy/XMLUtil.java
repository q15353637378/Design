package Strategy;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 27/01/2022 12:25
 */
public class XMLUtil {
    public static Object getBean() {
        try {
            //读取xml文件获得对象
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document parse = documentBuilder.parse("src/Strategy/config.xml");
            //识别xml文件内的信息
            NodeList nl = parse.getElementsByTagName("className");
            Node classNode = nl.item(0).getFirstChild();
            String nodeValue = classNode.getNodeValue();
            //通过反射获取类对象
            Class<?> aClass = Class.forName(nodeValue);
            return aClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
