<%--
  Created by IntelliJ IDEA.
  User: nike
  Date: 26/11/14
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
String type = (String)request.getAttribute("type");


   PrintWriter outp =  response.getWriter();
    outp.print(type);
%>
</body>
</html>
