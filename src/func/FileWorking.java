package func;

import beans.CountryWorking;
import models.Country;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import javax.ejb.EJB;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by iocz on 10/10/15.
 */
public class FileWorking {

    public static void writeXml(Document document, String direct) throws IOException {
        XMLOutputter outPutter = new XMLOutputter();
        FileWriter writer = new FileWriter(direct);
        outPutter.output(document, writer);
        writer.close();
    }
}
