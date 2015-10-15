package beans;

import func.DataBaseVoids;
import func.FileWorking;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//
@Stateless
public class CommentsBean implements CommentsWorking{
    public ResultSet selectComments() throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM COMMENTS");
        return stmnt.executeQuery();
    }

    public ResultSet selectTraditionComments(Integer traditionId) throws SQLException {
        DataBaseVoids.main();
        PreparedStatement stmnt = DataBaseVoids.con.prepareStatement("SELECT * FROM COMMENTS " +
                "WHERE TRADITION_ID = '" + traditionId + "'");
        return stmnt.executeQuery();
    }

    public void saveCommentsXML(ResultSet resultSet, String direct)
            throws IOException, SQLException {
        Element root = new Element("commentsSave");
        Document doc = new Document(root);
        while (resultSet.next()) {
            Element commentElement = new Element("comment");
            Element commentId = new Element("commentId");
            Element commentText = new Element("commentText");
            Element traditionId = new Element("traditionId");
            Element userId = new Element("userId");
            Element currentDate = new Element("currentDate");
            commentId.setText(resultSet.getString("ID"));
            commentText.setText(resultSet.getString("TEXT"));
            traditionId.setText(resultSet.getString("TRADITION_ID"));
            userId.setText(resultSet.getString("USER_ID"));
            currentDate.setText(resultSet.getString("CUR_DATE"));
            commentElement.addContent(commentId);
            commentElement.addContent(commentText);
            commentElement.addContent(traditionId);
            commentElement.addContent(userId);
            commentElement.addContent(currentDate);
            root.addContent(commentElement);
            FileWorking.writeXml(doc, direct);
        }
    }
}
