<%-- 
    Document   : cerrar
    Created on : 5/04/2012, 12:05:14 PM
    Author     : lupe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%

  session.invalidate();//destruye la session
%>

<jsp:forward page="index.jsp"></jsp:forward>
