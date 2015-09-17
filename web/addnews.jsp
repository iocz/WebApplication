<%@ page import="Java_test.SQLMethods" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="Java_test.Connect" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="func.Calendar" %>
<%@ page import="func.DataBaseVoids" %>
<%@ page import="func.Content" %>
<%--
  Created by IntelliJ IDEA.
  User: iocz
  Date: 05/09/15
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title></title>
  <script src="js/calendar.js" type="text/javascript"></script>
</head>
<body>
<%
  String oldHolidayName = request.getParameter("title");
  String oldDate = request.getParameter("date");
  String oldCountry = request.getParameter("country");
  String oldDescription = request.getParameter("description");
  String id = request.getParameter("id");
%>
<table class="table">
  <tr>
    <h1><%if (oldHolidayName != null) out.print("Изменить"); else
      out.print("Добавить");%> праздник</h1>
    <hr width="98%" border="1" color="#f2f2f0"/>
    <!--Загрузка изображений!-->
    <iframe name="iframe" style="position: absolute; left: -9999px;"></iframe>
    <form action="/WebApp/upload.jsp?id=<%out.print(DataBaseVoids.getLastTraditionID() + 1);%>"
          method="post" enctype="multipart/form-data" target="iframe">
      <h3>Загрузить изображение</h3>
      <input type="file" name="file" size="50" />
      <input type="submit" value="Загрузить">
    </form>

    <form action="addTraditionServlet" method="post">
      <h3>Название праздника</h3>
      <input type="text" name="holidayName" value="<%if (oldHolidayName != null)
           out.print(oldHolidayName);%>" required class="b3radius field addNewsField">
      <%
        //TODO Передать тип праздника как число
      %>
      <h3>Тип праздника</h3>
      <select required="3" class="b3radius field" name="holidayType">
        <option disabled>Выберите тип праздника</option>
        <%
          //TODO Переписать
          Connection connection = Connect.getConnection();
          Statement statement = Connect.getStatement(connection);
          ResultSet resultSet = Connect.resultSetSelectFrom(statement, "NAME", "TYPE");
          ArrayList<String> typeList = Connect.getResultList(resultSet, false);
          for (int i = 0; i < typeList.size(); i++) {
        %>
        <option value="<%=(i + 1)%>"><%=typeList.get(i)%></option>
        <%
          }
        %>
      </select>
      <%//TODO добавить обработку второй даты%>
      <h3>Дата праздника</h3>
      <input type="text" name="holidayDate" value="<%if (oldDate != null)
                    out.print(oldDate);%>" required class="b3radius field addNewsField"
             onfocus="this.select();lcs(this)"
             onclick="event.cancelBubble=true;this.select();lcs(this)">
      - <input type="text" class="b3radius field addNewsField"
               onfocus="this.select();lcs(this)"
               onclick="event.cancelBubble=true;this.select();lcs(this)">
      <%//TODO Вывести список стран%>
      <h3>Страна</h3>
      <input type="text" value="<%if (oldCountry != null) out.print(oldCountry);%>"
             name="holidayCountry" required class="b3radius field addNewsField">
      <h3>Описание традиции</h3>
      <!--<input type="text" name="holidayDescription" class="b3radius field addNewsField">!-->
        <textarea name="traditionDescription" value="<%if (oldDescription != null)
         out.print(oldDescription);%>"
                  class="b3radius field descriptionNewsField" autofocus>
        </textarea>
      <br>
      <div align="center"><button style="width: 50%" class="sb_header b3radius">
        <%if (oldHolidayName != null) out.print("Изменить праздник"); else
          out.print("Добавить праздник");%>
      </button></div>
      </form>
    <%
      /*
      String file = request.getParameter("file");
      if (file != null) {
        System.out.println(file);
      }
      */
    %>
  </tr>
</table>

</body>
</html>
