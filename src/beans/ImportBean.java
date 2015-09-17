package beans;

import func.DataBaseVoids;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;
import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Stateless
public class ImportBean implements ImportWorking {
    private SAXBuilder builder = new SAXBuilder();

    //Проверка на существование элемента по id.
    private Boolean checkExist(int id, String tableName){
        DataBaseVoids.main();
        try {
            PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(
                    "SELECT COUNT(ID) FROM " + tableName + " WHERE ID = '" + id + "'");
            if (stmnt.executeQuery().next()) {
                if (stmnt.executeQuery().getInt(0) > 0) return false;
                else return true;
            }
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    private Boolean checkDoubleElement(int id, String tableName) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(
                    "SELECT * FROM " + tableName);
        ResultSet rs = stmnt.executeQuery();
        if (rs.next()) {
            //TODO fix it.
            if (rs.getInt("ID") == id) {
                return true;
            } else return false;
        } else return false;
    }

    private Boolean checkDoubleCountry(int id, String countryName) throws SQLException{
        Boolean result = false;
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(
                "SELECT * FROM COUNTRY WHERE ID = '" + id + "'");
        ResultSet rs = stmnt.executeQuery();
        while (rs.next()) {
            if (rs.getInt("ID") == id && rs.getString("NAME").equals(countryName)) {
                result = true;
            }
        }
        return result;
    }

    public ArrayList<ResultSet> loadCountry(String direct) throws IOException, JDOMException, SAXException,
            SQLException{
        DataBaseVoids.main();
        //if (!((new File(direct)).exists())) {
        //direct = XML_COUNTRY_DEFAULT_RU;
        //}
        //if (validationXSD(direct, COUNTRY_XSD) == false) {throw new SAXException();}

        ArrayList<ResultSet> resultSets = new ArrayList<>();
        Document document = builder.build(direct);
        Element root = document.getRootElement();
        List countryElem = root.getChildren();
        Iterator countryIterator = countryElem.iterator();

        while (countryIterator.hasNext()) {
            Element countryElement = (Element) countryIterator.next();
            int id = Integer.parseInt(countryElement.getChild("countryId").getText());
            String countryName = countryElement.getChild("countryName").getText();

            if (!checkDoubleCountry(id, countryName)) {
                PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(
                        "UPDATE COUNTRY SET ID ='" + id + "', NAME = '" +
                                countryName + "' WHERE ID='" + id + "'");
                resultSets.add(stmnt.executeQuery());
                System.out.println("UPDATE COUNTRY");
            }else if(!checkExist(id, "COUNTRY")) {
                try {
                    PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("INSERT INTO COUNTRY (ID, NAME) " +
                            "VALUES ('" + id + "', '" + countryName + "')");
                    resultSets.add(stmnt.executeQuery());
                    System.out.println("INSERT COUNTRY");
                } catch (Exception e) {
                    System.out.println("Ошибка добавления " + id + " " + countryName +
                            "\nТакая запись уже существует!");}
            } else {
                    System.out.println("Ошибка добавления " + id + " " + countryName +
                            "\nТакая запись уже существует!");
            }
        }
        return resultSets;
    }

    public ArrayList<ResultSet> loadHoliday(String direct)throws IOException,
            JDOMException, SAXException, SQLException{
        DataBaseVoids.main();
        //if (!((new File(direct)).exists())) {
        //direct = XML_COUNTRY_DEFAULT_RU;
        //}
        //if (validationXSD(direct, COUNTRY_XSD) == false) {throw new SAXException();}
        ArrayList<ResultSet> resultSets = new ArrayList<>();
        Document document = builder.build(direct);
        Element root = document.getRootElement();
        List holidayElem = root.getChildren();
        Iterator holidayIterator = holidayElem.iterator();

        while (holidayIterator.hasNext()) {
            Element holidayElement = (Element)holidayIterator.next();
            int id = Integer.parseInt(holidayElement.getChild("holidayId").getText());
            String holidayName = holidayElement.getChild("holidayName").getText();
            Integer holidayType = 1;// Integer.parseInt(holidayElement.getChild("holidayType").getText());
            String holidayStartDate = holidayElement.getChild("holidayStartDate").getText();

            if (checkExist(id, "HOLIDAY")) {
                PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("INSERT INTO HOLIDAY (ID, NAME, TYPE_ID," +
                        "START_DATE)" +
                        "VALUES ('" + id + "', '" + holidayName + "', '" + holidayType + "'" +
                "TO_DATE('" + holidayStartDate + "', 'YYYY-MM-DD'))");
                resultSets.add(stmnt.executeQuery());
            } else if (!checkDoubleElement(id, "HOLIDAY")) {
                PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(
                        "UPDATE HOLIDAY SET ID ='" + id + "', NAME = '" + holidayName +
                                "', TYPE_ID ='" + holidayType + "', START_DATE =TO_DATE('" +
                                holidayStartDate + "', 'YYYY-MM-DD') WHERE ID='" + id + "'");
                resultSets.add(stmnt.executeQuery());
            } else {
                System.out.println("Ошибка добавления " + id + " " + holidayName +
                        "\nТакая запись уже существует!");
            }
        }
        return resultSets;
    }

    public ArrayList<ResultSet> loadTraditions(String direct) throws IOException,
            JDOMException, SAXException, SQLException{
        DataBaseVoids.main();
        //if (!((new File(direct)).exists())) {
        //direct = XML_COUNTRY_DEFAULT_RU;
        //}
        //if (validationXSD(direct, COUNTRY_XSD) == false) {throw new SAXException();}
        ArrayList<ResultSet> resultSets = new ArrayList<>();
        Document document = builder.build(direct);
        Element root = document.getRootElement();
        List traditionElem = root.getChildren();
        Iterator traditionIterator = traditionElem.iterator();

        while (traditionIterator.hasNext()) {
            Element traditionElement = (Element) traditionIterator.next();
            int id = Integer.parseInt(traditionElement.getChild("traditionId").getText());
            String traditionDescription = traditionElement.getChild("traditionDescription").getText();
            Element holidayElement = traditionElement.getChild("holiday");
            String holidayStartDate = holidayElement.getChild("holidayStartDate").getText();
        }
        return resultSets;
    }
}
