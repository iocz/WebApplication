<%@ page import="java.net.URLDecoder" %>
<%@ page import="Java_test.SQLMethods" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="func.DataBaseVoids" %>
<%--
  Created by IntelliJ IDEA.
  User: !!!!!!!
  Date: 31.05.2015
  Time: 0:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
  String title = request.getParameter("title");
  String img = request.getParameter("img");
  String text = request.getParameter("text");
  String country = request.getParameter("country");
  String id = request.getParameter("id");
  Integer reads = DataBaseVoids.getReads(Integer.parseInt(id));
  Integer comments = DataBaseVoids.getCommentCount(Integer.parseInt(id));
%>

<table class="table">
  <tr>
    <h2><a href="index.jsp?traditionName=<%=title%>&img=<%=img%>&id=<%=id%>&country=<%=country%>"><%=title%></a></h2>
    <hr width="98%" border="1" color="#f2f2f0"/>
    <div class="small">
      <%//TODO Дописать тип праздника%>
        Country: <%=country%>  Reads: <%=reads%> Comments: <%=comments%>
    </div>
  </tr>
  <tr>
    <td><img src="<%=img%>" width="80" height="65" alt="Images"></td>
    <td>
      <%=text%>
    </td>
  </tr>
</table>
</body>
</html>
