<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="Java_test.Connect" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="Java_test.SQLMethods" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="func.Calendar" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="func.DataBaseVoids" %>
<%@ page import="func.LoadData" %>
<%--
  Created by IntelliJ IDEA.
  User: iocz
  Date: 08/09/15
  Time: 00:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <%
      String traditionName = request.getParameter("traditionName");
      String id = request.getParameter("id");
      String img = request.getParameter("img");
      String country = request.getParameter("country");

        StringBuilder url = new StringBuilder("index.jsp?traditionName=".concat(
                traditionName).concat("&id=").concat(id).concat("&img=").concat(img).concat(
                "&country=").concat(country));

        System.out.println(id);
        String text = Calendar.getTraditions().get(Integer.parseInt(id)).getDescription();
        Integer reads = DataBaseVoids.getReads(Integer.parseInt(id));
        DataBaseVoids.incReads(Integer.parseInt(id), reads);
        ArrayList<String> commentsList = LoadData.getResultList(DataBaseVoids.getTraditionComment(
                Integer.parseInt(id)));
        /*
      Connection connection = Connect.getConnection();
      Statement statement = Connect.getStatement(connection);
      String resultSetDescriptionFromTradition = "SELECT DESCRIPTION FROM TRADITION WHERE ID = '" +
              id + "'";


      String text = Connect.getStringResult(statement, resultSetDescriptionFromTradition, false);
      Integer reads = SQLMethods.getReads(Integer.parseInt(id));
      SQLMethods.incReads(Integer.parseInt(id), reads);
        Connection connectionComment = Connect.getConnection();
        Statement statementComment = Connect.getStatement(connectionComment);
        ResultSet resultSetComment = Connect.resultSetSelectFromWhere(statementComment,
                "TEXT", "COMMENTS", "TRADITION_ID = " + id);
        ArrayList<String> commentsList = Connect.getResultList(resultSetComment, false);
        */
    %>
    <table class="table">
      <tr>
        <h2><%=traditionName%></h2>
        <hr width="98%" border="1" color="#f2f2f0"/>
        <div class="small">
          Country: <%=country%>  Reads: <%=reads + 1%>
        </div>
      </tr>
      <tr>
        <td><img src="<%=img%>" width="80" height="65" alt="Images"></td>
        <td>
          <%=text%>
        </td>
      </tr>
    </table>
    <hr class="dotted" border="1" color="#f2f2f0" width="98%"/>
    <table>
        <tr>
    <form method="post">
        <a>Комментариев: <%=commentsList.size()%></a>
        &nbsp;&nbsp;
        <input value=" Удалить праздник " type="submit" name="delete">
        &nbsp;&nbsp;
    </form>
    <form action="index.jsp?addNews=true&title=<%=traditionName%>&country=<%=country%>&description=<%=text%>" method="post">
        <input value=" Изменить праздник " type="submit">
    </form>
        </tr>
    </table>
    <hr class="dotted" border="1" color="#f2f2f0" width="98%"/>
    <%
      //Нужно извлекать userName по userId
      for (int i = 0; i < commentsList.size(); i++) {
        //Вместо userName=user добавть имя пользователя
        StringBuilder pg = new StringBuilder("comment.jsp?userName=user".concat(
                "&text=".concat(commentsList.get(i))
                ));
    %>
        <jsp:include page="<%=pg.toString()%>"/>
    <%
      }
    %>
    <iframe name="iframe" style="position: absolute; left: -9999px;"></iframe>
    <form action="<%=url.toString()%>" method = "post" target="iframe">
      <div>
        <h3>Добавить комментарий</h3>
        <!--<input type="text" name="holidayDescription" class="b3radius field addNewsField">!-->
        <textarea name="comment" class="b3radius field descriptionNewsField" required>
        </textarea>

        <br>
        <div align="left"><button style="width: 20%" class="sb_header b3radius">Добавить...</button></div>
      </div>
    </form>
<%
        String delete = request.getParameter("delete");
        if (delete != null) {
            SQLMethods.deleteComments(Integer.parseInt(id));
            SQLMethods.deleteStatistic(Integer.parseInt(id));
            SQLMethods.deleteTradition(Integer.parseInt(id));
            response.sendRedirect("index.jsp");
        }
    
    String comment = request.getParameter("comment");
    if (comment != null) {
        SQLMethods.addNewComment(comment, Integer.parseInt(id), 1);
        //response.sendRedirect(request.getContextPath() + url.toString());
        //response.sendRedirect(url.toString());
    }
%>
</body>
</html>
