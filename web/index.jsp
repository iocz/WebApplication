<%@ page import="java.sql.*" %>
<%@ page import="func.DataBaseVoids" %>
<%@ page import="func.LoadData" %>
<%@ page import="func.Content" %>
<%@ page import="func.Calendar" %>
<%@ page import="java.util.*" %>
<%@ page import="models.Tradition" %>
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
          String holidayName = request.getParameter("holidayName");

          String traditionName = request.getParameter("traditionName");
          String faq = request.getParameter("faq-ru");
          String xml = request.getParameter("xml");
          String importXML = request.getParameter("importXML");
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
              //LoadData.setHashMapCountries(DataBaseVoids.selectCountries());
              //LoadData.setHashMapHolidays(DataBaseVoids.selectHoliday());
              //LoadData.setHashMapTraditions(DataBaseVoids.selectAllTraditions());
              Calendar.setCountriesList(DataBaseVoids.selectCountries());
              Calendar.setHolidaysList(DataBaseVoids.selectHoliday());
              Calendar.setTraditionsList(DataBaseVoids.selectAllTraditions());
          }
          if (Calendar.getUserId() > 0 && Calendar.getShowNews()) {
              ArrayList<String> pages = Content.getPages();

              String currentPage = request.getParameter("page");
              currentPage = currentPage != null ? currentPage : Integer.toString(1);
              if (currentPage != null) {
              Integer pageNum = Integer.parseInt(currentPage);
              for (int i = (pageNum - 1) * 10; i < (pageNum * 10 < pages.size() ? pageNum * 10 :
              pages.size()); i++) {
      %>
            <jsp:include page="<%= pages.get(i)%>"/>
      <%
            }}
              Calendar.setShowNews(false);
          }if (traditionName != null) {
      %>
            <jsp:include page="full_news.jsp"/>
      <%}  else if (addNews != null) {//TODO Дописать всe параметры
          //String type = request.getParameter("type");
          //String date = request.getParameter("date");
          String country = request.getParameter("country");
          String description = request.getParameter("description");
          String id = request.getParameter("id");

          String url = "addnews.jsp";
          if (title != null) {
          StringBuilder changePageUrl = new StringBuilder("addnews.jsp?title=".concat(title).concat(
                  "&id=").concat(id).concat("&country=").concat(country).concat(
                  "&description=").concat(description)
          );
            url = changePageUrl.toString();
          }
      %>
            <jsp:include page="<%=url%>"/>
      <%
      } else if (holidayName != null) {
          //TODO всё в методы
          ResultSet traditions = DataBaseVoids.getHolidayTraditions(request.getParameter("holidayName"),
                  Calendar.getUserId());

          while (traditions.next()) {
              Calendar.getTraditions().clear();
              Calendar.setTraditionsList(DataBaseVoids.getHolidayTraditions(request.getParameter(
                      "holidayName"), Calendar.getUserId()));
          }
          ArrayList<String> pages = Content.getPages();
          for (int i = 0; i < pages.size(); i++) {
      %>
      <jsp:include page="<%= pages.get(i)%>"/>
      <%
          }
          Calendar.getTraditions().clear();
          Calendar.setTraditionsList(traditions);

      }else if (request.getParameter("holidays") != null) {
          ResultSet set = DataBaseVoids.getUserHolidays(Calendar.getUserId());
              out.print("<div class=holidays>");
              out.print("<ul>");
              while (set.next()) {
              String current = set.getString("NAME");
      %>
          <li>
              <a href="index.jsp?holidayName=<%=current%>"><%=current%></a>
          </li>
      <%
      } out.print("</ul>"); out.print("</div>");
      } else if (request.getParameter("countries") != null) {
          ResultSet set = DataBaseVoids.getCountries();
          out.print("<div class=holidays>");
          out.print("<ul>");
              while (set.next()) {
              String current = set.getString("NAME");
          %>
          <li>
              <a href="index.jsp?countryName=<%=current%>"><%=current%></a>
          </li>
          <%}
          %>
      </ul>
      </div> <%
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
            Calendar.setTraditionsList(DataBaseVoids.getCountryTraditions(request.getParameter(
                    "countryName"), Calendar.getUserId()));
      }
      ArrayList<String> pages = Content.getPages();
      for (int i = 0; i < pages.size(); i++) {
      %>
             <jsp:include page="<%= pages.get(i)%>"/>
      <%
          }

      }  else if (faq != null) {
      %>
            <jsp:include page="faq-ru.html"/>
      <%
      } else if (xml != null) {
      %>
            <jsp:include page="XMLForm.jsp"/>
      <%
      } else if (importXML != null) {
        %>
            <jsp:include page="loadData.html"/>
    <%
      }else if (gbook != null) {
      %>
            <jsp:include page="guest_book.jsp"/>
      <%
      }else if (request.getParameter("topsearch") != null) {
          DataBaseVoids.searchHoliday(request.getParameter("topsearch"));
          ArrayList<String> pages = Content.getPages();

          for (int i = 0; i < pages.size(); i++) {
              System.out.println(pages.get(i));
      %>
    <jsp:include page="<%= pages.get(i)%>"/>
    <%
        }
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
                }else if (registration != null){
           %>
                <jsp:include page="registration.html"/>
           <%
               } else if (Calendar.getUserId() < 1) {
           %>
                <jsp:include page="login.jsp"/>
           <%
               }
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

