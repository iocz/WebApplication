<%@ page import="java.sql.*" %>
<%@ page import="func.DataBaseVoids" %>
<%@ page import="func.LoadData" %>
<%@ page import="func.Content" %>
<%@ page import="func.Calendar" %>
<%@ page import="model.Tradition" %>
<%@ page import="java.util.*" %>
<%--
  Created by IntelliJ IDEA.
  User: !!!!!!!
  Date: 24.05.2015
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Heretic 2.0</title>
  <!--<meta charset="utf-8">!-->
    <meta name="keywords" content="Holiday, library">
    <meta name="description" content="Your own holiday library!">
  <link href="css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<%//TODO сменить отображение%>
<jsp:directive.page errorPage="error.jsp" />
<div id="page_align" class="b3radius">
  <div id="header">
      <jsp:include page="header.html"/>
      <jsp:include page="header_nav.html"/>
  </div>
  <div id="content" class="f_right">
      <%
          String news = request.getParameter("news");
          String registration = request.getParameter("registration");
          String addNews = request.getParameter("addNews");
          String title = request.getParameter("title");
          String showNews = request.getParameter("show");

          String traditionName = request.getParameter("traditionName");
          String faq = request.getParameter("faq-ru");
          String gbook = request.getParameter("gbook");

          func.DataBaseVoids.main();

          if (showNews != null) {
              Calendar.setShowNews(true);
          }
          if (news != null) {
              Calendar.getCountries().clear();
              Calendar.getHolidays().clear();
              Calendar.getTraditions().clear();
              //Загрузка данных в HashMap.
              LoadData.setHashMapCountries(DataBaseVoids.selectCountries());
              LoadData.setHashMapHolidays(DataBaseVoids.selectHoliday());
              LoadData.setHashMapTraditions(DataBaseVoids.selectAllTraditions());
          }
          if (Calendar.getUserId() > 0 && Calendar.getShowNews()) {
              ArrayList<String> pages = Content.getPages();

              String currentPage = request.getParameter("page");
              currentPage = currentPage != null ? currentPage : Integer.toString(1);
              if (currentPage != null) {
              Integer pageNum = Integer.parseInt(currentPage);
              for (int i = (pageNum - 1) * 10; i < (pageNum * 10 < pages.size() ? pageNum * 10 :
              pages.size()); i++) {
                  System.out.println("size = " + pages.size());
                  System.out.println(i);
      %>
            <jsp:include page="<%= pages.get(i)%>"/>
      <%
            }}
              Calendar.setShowNews(false);
          }if (traditionName != null) {
      %>
            <jsp:include page="full_news.jsp"/>
      <%
      } else if (addNews != null) {//TODO Дописать всe параметры
          String url = title != null ? "addnews.jsp?title=" + title : "addnews.jsp";
      %>
            <jsp:include page="<%=url%>"/>
      <%
      } else if (request.getParameter("holidays") != null) {
          ResultSet set = DataBaseVoids.getUserHolidays(Calendar.getUserId());
      out.print("<ul>");
              while (set.next()) {
              String current = set.getString("NAME");
      %>
          <li>
              <a href="index.jsp?holidayName=<%=current%>"><%=current%></a>
          </li>
      <%
          }
      out.print("</ul>");
      } else if (request.getParameter("countries") != null) {
          ResultSet set = DataBaseVoids.getCountries();
          out.print("<ul>");
              while (set.next()) {
              String current = set.getString("NAME");
          %>
          <li>
              <a href="index.jsp?countryName=<%=current%>"><%=current%></a>
          </li>
          <%}
          %>
      </ul> <%
      } else if (request.getParameter("traditions") != null) {
         ResultSet set = DataBaseVoids.getUserTraditions(Calendar.getUserId());
         while (set.next()) {
            out.println(set.getObject("DESCRIPTION").toString()+"\n");
         }
      } else if (request.getParameter("logout") != null) {
         Calendar.setUserId(-1);
         response.sendRedirect("index.jsp");
      } else if (request.getParameter("countryName") != null) {
         ResultSet traditions = DataBaseVoids.getCountryTraditions(request.getParameter("countryName"), Calendar.getUserId());
         while (traditions.next()) {
            Calendar.getTraditions().clear();
            LoadData.setHashMapTraditions(DataBaseVoids.getCountryTraditions(request.getParameter(
                  "countryName"), Calendar.getUserId()));
      }
      ArrayList<String> pages = Content.getPages();
      for (int i = 0; i < pages.size(); i++) {
      %>
             <jsp:include page="<%= pages.get(i)%>"/>
      <%
          }
      } else if (request.getParameter("holidayName") != null) {
        ResultSet traditions = DataBaseVoids.getHolidayTraditions(request.getParameter("holidayName"),Calendar.getUserId());
        while (traditions.next())
          out.print(traditions.getString("DESCRIPTION").toString());
      } else if (faq != null) {
      %>
            <jsp:include page="faq-ru.html"/>
      <%
      } else if (gbook != null) {
      %>
            <jsp:include page="guest_book.jsp"/>
      <%
      } else if (Calendar.getUserId() < 1) {
      %>
            <jsp:include page="welcome.html"/>
      <%
          }
      %>
 </div>
  <div id="sidebar" class="f_left">
           <%
               if (Calendar.getUserId() > 0) {
           %>
                <jsp:include page="menu.html"/>
           <%
               } else if (Calendar.getUserId() < 1) {
           %>
                <jsp:include page="login.jsp"/>
           <%
               }else if (registration != null){
           %>
                <jsp:include page="registration.jsp"/>
           <%
               } else if (Calendar.getIsWrongLoginPass()) {
           %>
                <jsp:include page="wrongLogIn.html"/>
           <%  }
           %>
   </div>
   <div class="clr"></div>
    <%
        int numPage = Calendar.getTraditions().size() / 10 + 1;
        if (news != null) {
            StringBuilder footerLinks = new StringBuilder(
                    "footerLinks.jsp?numPage=".concat(Integer.toString(numPage))
            );
    %>
    <jsp:include page="<%= footerLinks.toString()%>"/>
    <%
        }
    %>
    <jsp:include page="footer.html"/>
 </div>
 </body>
 </html>

