<%@ page import="func.DataBaseVoids" %>
<%@ page import="func.Calendar" %>
<%--
  Created by IntelliJ IDEA.
  User: iocz
  Date: 16/09/15
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
  <link href="css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="sb_header b10radius"><i></i>Registration</div>
<div class="sb_nav">
  <ul>
    <form method = "post">
      <input type="text" maxlength="35" name="logFieldReg" placeholder="Enter login" class="b3radius loginField">
      <input type="password" maxlength="25" name="passFieldReg" placeholder="Enter password" class="b3radius loginField">
      <input type="password" maxlength="25" name="repeatPass" placeholder="Repeat password" class="b3radius loginField">
      <div align="center"><button style="width: 50%" class="sb_header b3radius" name="goRegister">Enter</button></div>
    </form>
  </ul>
</div>
<%
  String login = request.getParameter("logFieldReg");
  String pass1 = request.getParameter("passFieldReg");
  String pass2 = request.getParameter("repeatPass");
  if ((login != null)&&(pass1.equals(pass2))&&(pass1 != null)&&(pass2 != null)) {
    Calendar.setUserId(DataBaseVoids.addUser(login, pass1));
%>
  <jsp:forward page="index.jsp?news=true"/>
<%
  } else {
    Calendar.setIsWrongLoginPass(true);
  }
%>
</body>
</html>
