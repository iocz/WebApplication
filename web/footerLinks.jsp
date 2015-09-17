<%@ page import="java.net.URLDecoder" %>
<%@ page import="Java_test.SQLMethods" %>
<%--
  Created by IntelliJ IDEA.
  User: iocz
  Date: 07/09/15
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
  String numPage = request.getParameter("numPage");
%>
<br>
<div id="footer_links" align="center">
  <%
    if (numPage != null) {
    for (int i = 1; i < Integer.parseInt(numPage) + 1; i++) {
  %>
      <a href="index.jsp?news=true&show=true&page=<%=i%>"><%=i%></a>
  <%
      }
    }
  %>
</div>
</body>
</html>
