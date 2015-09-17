<%--
  Created by IntelliJ IDEA.
  User: iocz
  Date: 09/09/15
  Time: 10:57
  To change this template use File | Settings | File Templates.
~--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <%
    String userName = request.getParameter("userName");
    String date = request.getParameter("date");
    String text = request.getParameter("text");
  %>
  <div class="userName">
          <%=userName%>
  </div>
  <hr width="98%" border="1" color="#f2f2f0"/>
  <div class="small">
          20:33, 12 сентября 2015 г.
  </div>
  <div>
          <%=text%>
  </div>
  <br/>
</body>
</html>
