<%--
  Created by IntelliJ IDEA.
  User: iocz
  Date: 11/10/15
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
  <style>
    select {
      width: 300px; /* Ширина списка в пикселах */
    }
  </style>
</head>
<body>
<jsp:include page="selectElements?type=country"/>
<jsp:include page="selectElements?type=holiday"/>
<jsp:include page="selectElements?type=tradition"/>
</body>
</html>
