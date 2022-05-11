package Visitor;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 27/01/2022 16:00
 */
public class XMLUtil {
    public static Object getBean() {
        try {
            Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("src/Visitor/config.xml");

            String className = parse.getElementsByTagName("className").item(0).getFirstChild().getNodeValue();

            return Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
