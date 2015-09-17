<%@ page import="Java_test.SQLMethods" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="func.Calendar" %>
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

        String text = Calendar.getTraditions().get(Integer.parseInt(id)).getDescription();
        Integer reads = DataBaseVoids.getReads(Integer.parseInt(id));
        DataBaseVoids.incReads(Integer.parseInt(id), reads);
        ArrayList<String> commentsList = LoadData.getResultList(DataBaseVoids.getTraditionComment(
                Integer.parseInt(id)));
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
    <form action="index.jsp?addNews=true&id=<%=id%>&title=<%=traditionName%>&country=
        <%=country%>&description=<%=text%>" method="post">
        <input value=" Изменить праздник " type="submit">
    </form>
        </tr>
    </table>
    <hr class="dotted" border="1" color="#f2f2f0" width="98%"/>
    <%
      String userName = DataBaseVoids.getUserLogin(Calendar.getUserId());
      for (int i = 0; i < commentsList.size(); i++) {
        StringBuilder pg = new StringBuilder("comment.jsp?userName=".concat(
                userName).concat("&text=".concat(commentsList.get(i))
                ));
    %>
        <jsp:include page="<%=pg.toString()%>"/>
    <%
      }
    %>
    <iframe action="<%=url.toString()%>" name="iframe" style="position: absolute; left: -9999px;"></iframe>
    <form method = "post" target="iframe">
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
            DataBaseVoids.deleteComments(Integer.parseInt(id));
            DataBaseVoids.deleteStatistic(Integer.parseInt(id));
            DataBaseVoids.deleteTradition(Integer.parseInt(id));
        }
    
    String comment = request.getParameter("comment");
    if (comment != null) {
        //DataBaseVoids.insertComment(comment, Integer.parseInt(id), Calendar.getUserId());
        SQLMethods.addNewComment(comment, Integer.parseInt(id), Calendar.getUserId());
    }
%>
</body>
</html>
