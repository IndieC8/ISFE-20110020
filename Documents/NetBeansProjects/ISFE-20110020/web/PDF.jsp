<%-- 
    Document   : PDF
    Created on : 23/05/2012, 03:59:20 AM
    Author     : Natalia Hernández
--%>

<%@page import="Datos.PDF"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        String pathAbsoluto=this.getServletContext().getRealPath("/");
        File pdf=new File(pathAbsoluto+"F.pdf");
        PDF.visualizarPDF(pdf, response, request);
        %>
    </body>
</html>
