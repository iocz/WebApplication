package beans;

import func.DataBaseVoids;
import func.FileWorking;
import models.Holiday;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by iocz on 12/10/15.
 */
@Stateless
public class TypeBean implements TypeWorking{
    private ResultSet selectType() throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM TYPE");
        return stmnt.executeQuery();
    }

    public void saveTypeXML(ResultSet resultSet, String direct)
        throws IOException, SQLException {
        Element root = new Element("typeSave");
        Document doc = new Document(root);
        while (resultSet.next()) {
            Element typeElement = new Element("type");
            Element typeId = new Element("typeId");
            Element typeName = new Element("typeName");
            typeId.setText(resultSet.getString("ID"));
            typeName.setText(resultSet.getString("NAME"));
            typeElement.addContent(typeId);
            typeElement.addContent(typeName);
            root.addContent(typeElement);
            FileWorking.writeXml(doc, direct);
        }
    }

    public void saveTypeXML(String direct) throws IOException, SQLException {
        Element root = new Element("typeSave");
        Document doc = new Document(root);
        ResultSet resultSet = selectType();
        while (resultSet.next()) {
            Element typeElement = new Element("type");
            Element typeId = new Element("typeId");
            Element typeName = new Element("typeName");
            typeId.setText(resultSet.getString("ID"));
            typeName.setText(resultSet.getString("NAME"));
            typeElement.addContent(typeId);
            typeElement.addContent(typeName);
            root.addContent(typeElement);
            FileWorking.writeXml(doc, direct);
        }
    }
}
