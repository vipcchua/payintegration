package pay.wechat.tool;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class XmlUtil  {


    public  Map<String, String> XmlAnalysis(String Xmldata) throws ParserConfigurationException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        StringReader stringReader  =  new StringReader(Xmldata);
        InputSource inputSource  =  new  InputSource(stringReader);
        Document doc = null;
        try {
            doc = dBuilder.parse(inputSource);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,String> container = new LinkedHashMap<String,String>();
        NodeList list = doc.getElementsByTagName("*");
        for(int i=1;i<list.getLength();i++){
            container.put(list.item(i).getNodeName(),list.item(i).getTextContent());
            /*container.forEach((key,value)->System.out.printf("%s = %s\n",key,value));*/

        }
        return container;
    }


    /*public static void main(String[] args) {

        try {
            Map<String, String> aa = XmlAnalysis();
            String bb =aa.get("result_code");
            System.out.print(bb);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }



    }
*/


}
