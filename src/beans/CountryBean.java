package beans;

import func.DataBaseVoids;
import func.FileWorking;
import models.Country;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;


import javax.ejb.Stateless;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class CountryBean implements CountryWorking {
    private SAXBuilder builder = new SAXBuilder();

    public ResultSet selectCountries() throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM COUNTRY");
        return stmnt.executeQuery();
    }

    public ResultSet insertCountry(String countryName) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("INSERT INTO COUNTRY (ID, NAME)" +
                "VALUES (country_seq.nextval, '" + countryName + "')");
        return stmnt.executeQuery();
    }

    public void updateCountry(int id, String countryName) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("UPDATE COUNTRY SET NAME = '" +
                countryName + "' WHERE ID = '" + id + "'");
        stmnt.executeQuery();
    }

    public ResultSet getCountries() throws SQLException {
        return selectCountries();
    }

    public ResultSet getCountryTraditions(String countryName, int userId) throws SQLException {
        return selectCountryTraditions(countryName, userId);
    }

    private ResultSet selectCountryTraditions(String countryName, Integer userId) throws SQLException {
        DataBaseVoids.main();
        //TODO SELECT * FROM TRADITION WHERE USER_ID = '" +
        //TODO userId + "' AND COUNTRY_ID IN " +
        //TODO        "(SELECT ID FROM COUNTRY WHERE NAME ='" + countryName + "')
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM TRADITION WHERE COUNTRY_ID IN " +
                "(SELECT ID FROM COUNTRY WHERE NAME ='" + countryName + "')");
        return stmnt.executeQuery();
    }

    public void saveCountryXML(List<Country> countries, String direct) throws IOException {
        Element root = new Element("countrySave");
        Document doc = new Document(root);
        for (Country country : countries) {
            Element countryElement = new Element("country");
            Element countryId = new Element("countryId");
            Element countryName = new Element("countryName");
            countryId.setText(String.valueOf(country.getId()));
            countryName.setText(country.getName());
            countryElement.addContent(countryId);
            countryElement.addContent(countryName);
            root.addContent(countryElement);
            FileWorking.writeXml(doc, direct);
        }
    }

    public LinkedList<Country> loadCountry(String direct) throws IOException, JDOMException, SAXException {
        LinkedList<Country> countries = new LinkedList<Country>();
        //if (!((new File(direct)).exists())) {
            //direct = XML_COUNTRY_DEFAULT_RU;
        //}
        //if (validationXSD(direct, COUNTRY_XSD) == false) {throw new SAXException();}

        Document document = builder.build(direct);
        Element root = document.getRootElement();
        List countryElem = root.getChildren();
        Iterator countryIterator = countryElem.iterator();
        while (countryIterator.hasNext()) {
            Element countryElement = (Element) countryIterator.next();
            Country country = new Country();
            country.setId(Integer.parseInt(countryElement.getChild("countryId").getText()));
            country.setName(countryElement.getChild("countryName").getText());
            countries.add(country);
        }

        return countries;
    }

}
