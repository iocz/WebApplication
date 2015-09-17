<%@ page import="func.DataBaseVoids" %>
<%@ page import="func.Calendar" %>
<%--
  Created by IntelliJ IDEA.
  User: iocz
  Date: 15/09/15
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>
<div class="sb_header b10radius"><i></i>Log in</div>
<div class="sb_nav">
  <ul>
    <form action="logInServlet" method = "post">
      <input type="text" maxlength="35" name="logField" placeholder="Enter login" class="b3radius loginField">
      <input type="password" maxlength="25" name="passField" placeholder="Enter password" class="b3radius loginField">
      <%
        if (Calendar.getIsWrongLoginPass()) {
      %>
      <small><p align="center">Wrong username or password!</p></small>
      <%
        }
      %>
      <div align="center"><button style="width: 50%" class="sb_header b3radius">Enter</button></div>
    </form>
    <form action = "index.jsp" method = "get">
      <div align="center"><button style="width: 50%" name="registration" class="sb_header b3radius">Registration</button></div>
    </form>

  </ul>
</div>
</body>
</html>




