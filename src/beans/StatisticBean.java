package beans;

import func.DataBaseVoids;
import func.FileWorking;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by iocz on 12/10/15.
 */
@Stateless
public class StatisticBean implements StatisticWorking{
    public ResultSet selectStatistic() throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM STATISTIC");
        return stmnt.executeQuery();
    }

    public ResultSet getTraditionStatistic(Integer traditionId) throws SQLException{
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement(
                "SELECT * FROM STATISTIC WHERE TRADITION_ID = '" + traditionId + "'");
        return stmnt.executeQuery();
    }

    public void saveStatisticXML(ResultSet resultSet, String direct) throws IOException, SQLException {
        Element root = new Element("statisticSave");
        Document doc = new Document(root);
        while (resultSet.next()) {
            Element statisticElement = new Element("statistic");
            Element statisticId = new Element("statisticId");
            Element statisticReads = new Element("statisticReads");
            Element traditionId = new Element("traditionId");
            statisticId.setText(resultSet.getString("ID"));
            statisticReads.setText(resultSet.getString("READS"));
            traditionId.setText("TRADITION_ID");
            statisticElement.addContent(statisticId);
            statisticElement.addContent(statisticReads);
            statisticElement.addContent(traditionId);
            root.addContent(statisticElement);
            FileWorking.writeXml(doc, direct);
        }
    }
}
