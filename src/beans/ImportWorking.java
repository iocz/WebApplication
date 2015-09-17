package beans;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ImportWorking {
    ArrayList<ResultSet> loadCountry(String direct) throws IOException, JDOMException, SAXException,
            SQLException;
    ArrayList<ResultSet> loadHoliday(String direct)throws IOException, JDOMException, SAXException,
            SQLException;
}
