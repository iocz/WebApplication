<%@ page import="Java_test.SQLMethods" %>
<%--
  Created by IntelliJ IDEA.
  User: iocz
  Date: 12/09/15
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<iframe name="iframe" style="position: absolute; left: -9999px;"></iframe>
<form method = "post" target="iframe">
  <div>
    <h3>Введите ваше имя</h3>
    <input type="text" name="name" required class="b3radius field addNewsField">
    <h3>Добавить запись</h3>
    <textarea name="comment" class="b3radius field descriptionNewsField" required>
    </textarea>
    <br>
    <div align="left"><button style="width: 20%" class="sb_header b3radius">Добавить...</button></div>
  </div>
</form>
<%
  //TODO Прикрутить к БД
  String name = request.getParameter("name");
  String comment = request.getParameter("comment");
  if (comment != null && name != null) {
    SQLMethods.addNewNode(name, comment);
  }
%>
</body>
</html>
