package vt.smt;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import sun.security.ssl.Debug;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import org.w3c.dom.Document;


/**
 * Created by semitro on 08.02.17.
 */

//Как сделать так, чтобы при добавлении наследников физического объекта не приходилось дописывать парсер?
public class XmlParser {
    public boolean hasNext(){
      //  Debug.println("Has next" , Integer.toString(currentNode));
        if(list.getLength()-currentNode <= 0)
            return false;
        else
            return true;
    }
    XmlParser(String path) throws FileNotFoundException{
        File file = new File(path);
        if(!file.exists())
            throw new FileNotFoundException();
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                doc = builder.parse(file);
                doc.getDocumentElement().normalize();
                doc.normalize();
                doc.normalizeDocument();
                list = doc.getDocumentElement().getElementsByTagName("Thing");
               // list = doc.getChildNodes().item(0).getChildNodes();
              //  Debug.println("List len", Integer.toString(list.getLength()));
            }catch (Exception e){
                Debug.println("Exception: ", "Something wrongs with XML-file: " + path);

            }
    }
    public PhysicalObject getNext(){
        if (hasNext()) {
            Element element = (Element)list.item(currentNode);
            currentNode++;
            return new Toy(
                    element.getElementsByTagName("Name").item(0).getTextContent(),
                    Double.parseDouble(element.getElementsByTagName("Weight").item(0).getTextContent()),
                    Boolean.parseBoolean(element.getElementsByTagName("IsClean").item(0).getTextContent())
            );
        }
        else
            return null;

    }
    private Document doc;
    private NodeList list;
    private int currentNode;
}
