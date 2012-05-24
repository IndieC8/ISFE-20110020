<%--
    Document   : Usar.jsp, Para el menu ya ingresado
    Created on : 19/Abril/2012
    Author     : lupe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" import="dao.*" session="true"
         import="java.util.*"%>

<!DOCTYPE html>
<%!
    String contribuyente = "";
    String id = "" ;
%>
<%
    HttpSession sesionOk = request.getSession();
    if (sesionOk.getAttribute("contribuyente") == null) {
        %>
        <jsp:forward page="index.jsp">
            <jsp:param name="error" value="Es obligatorio identificarse"></jsp:param>
        </jsp:forward>
        <%
    } else {
        contribuyente = (String) sesionOk.getAttribute("contribuyente");//Recoge la session
        id = (String) sesionOk.getAttribute("identificador");//Recoge la session
%>
<html>
	<head>
            <title>ISFE - Inicio</title>
            <link rel="stylesheet" type="text/css" href="estilo/style.css" />
            <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
            <script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
            <script type="text/javascript" src="js/jquery.MultiFile.js"></script>
            <script type="text/javascript" src="js/jquery.menu.js"></script>
                    
	<center>
            <div class="principal">
            <div class="header">
                <div class="logo"><a href="index-user.jsp" ><img src="images/logo1.png" alt="ISFE" height="164"/></a></div>
            </div>
		<div class="contenido_principal">
		<div class="menu">
                    <ul>
                        <li><a href="index-user.jsp" ><img src="images/icons/home.png" alt="" height="20"/> Home</a></li>
                        <li><a href="contact.jsp"><img src="images/icons/contacto_ico.png" alt=""/> Contacto</a></li>
                        <li><a href="Usar.jsp"><img src="images/icons/valida_ico.png" alt=""/>¿C&oacute;mo usar ISFE?</a></li>
                        <li><a href="perfil.jsp" id="current"><img src="images/icons/perfil_ico.png" alt=""/> Perfil</a>
                            <ul>
                                <!-- <li><a href="perfil/consultarPerfil.jsp">Consultar Perfil</a></li>-->
                                <li><a href="perfil/modificarPerfil.jsp">Modificar Perfil</a></li>
                                <li><a href="perfil/administrarFIELyCSD.jsp">Administrar FIEL y CSD</a></li>
                                <li><a href="perfil/administrarClientes.jsp">Administrar Clientes</a></li>
                            </ul>
                        </li>
                        <li><a href="factura.jsp"><img src="images/icons/factura_ico.png" alt=""/> Factura</a>
                            <ul>
                                <li><a href="factura/generarFacturaElectronica.jsp">Generar Factura Electr&oacute;nica</a></li>
                                <li><a href="factura/EstadoFactura.jsp">Estado de la Factura</a></li>
                            </ul>
                        </li>                       
                        <li><a href="cerrar.jsp" id="cerrarSesion"><img src="images/icons/ingreso_ico.png"/> Cerrar Sesión &nbsp; &nbsp; <% out.println(contribuyente); %></a>
                        </li>    
                    </ul>
                </div>
                <!-- Termina Menu -->
                <br/>
		<div class="titulo_pagina">
                    <br/>
                    ¿Cómo usar ISFE ?</div>
                <br/>
		<center>
                    <div id="tabs">
                        <ul>
                            <li><a href="#tabs-1">Presentaci&oacute;n</a></li>
                            <li><a href="#tabs-2">Registro a ISFE</a></li>
                            <li><a href="#tabs-3">Inicio de Sesi&oacute;n</a></li>
                            <li><a href="#tabs-4">Manejo de Perfil</a></li>
                            <li><a href="#tabs-5">Factura Electr&oacute;nica</a></li>
                        </ul>
                        <div id="tabs-1">
                            <h2>Esta es una presentacion general del sistema asi como una reseña de la legislacion y las funcionalidades mas importantes de ISFE.</h2><br>
                            <iframe width="560" height="315" src="http://www.youtube.com/embed/yEoHFzEmld0" frameborder="0"></iframe>
                        </div>
                        <div id="tabs-2">
                            <h2>En este video se muestra como realizar el registro de un nuevo usuario al sistema.</h2><br>
                            <iframe width="420" height="315" src="http://www.youtube.com/embed/o1tj2zJ2Wvg" frameborder="0"></iframe>
                        </div>
                        <div id="tabs-3">
                            <h2>Aqui se mostrara como se realiza el inicio de sesion al sistema asi como las restriocciones dadas en caso de no estar dado de alta.</h2><br>
                            <iframe width="420" height="315" src="http://www.youtube.com/embed/qfB8fmHzc5Q" frameborder="0"></iframe>
                        </div>
                        <div id="tabs-4">
                            <h2>Este video tutorial consta de cuatro partes en las que se veran la administracion de los clientes del usuario, como administrar la FIEL y CSD, la consulta del perfil y la modificaicon del mismo.</h2>
                            <br><br>
                            Administacion de Clientes.<br>
                            <iframe width="560" height="315" src="http://www.youtube.com/embed/cl64-XHE7zo" frameborder="0"></iframe>
                            <br><br>
                            Administracion de FIEL y CSD<br>
                            <iframe width="420" height="315" src="http://www.youtube.com/embed/co6WMzDOh1o" frameborder="0"></iframe>
                            <br><br>
                            Consulta de perfil<br>
                            <iframe width="560" height="315" src="http://www.youtube.com/embed/w8KQmps-Sog" frameborder="0"></iframe>
                            <br><br>
                            Modifical Perfil<br>
                            <iframe width="420" height="315" src="http://www.youtube.com/embed/V8EA2jrBvso" frameborder="0"></iframe>
                        </div>
                        <div id="tabs-5">
                            <h2>En esta seccion se vera la captura de los datos correspondientes a la factura electr&oacute;nica asi como la generacion de la misma en formato PDF</h2><br>
                            <br>
                            Generaci&oacute;n de Factura Electr&oacute;nica<br>
                            <iframe width="420" height="315" src="http://www.youtube.com/embed/SYVxvY68DZQ" frameborder="0"></iframe>
                            <br><br>
                            Generaci&oacute;n de Factura Imprimible (PDF)<br>
                            <iframe width="560" height="315" src="http://www.youtube.com/embed/a1ZB3Wbm0ek" frameborder="0"></iframe>
                        </div>
                    </div>
                </center>
            </div>
            <div class="footer">
                <br><br>
                Derechos reservados ISFE <br>
                HTML5 | CSS 2.0 | JavaScript | Apache Tomcat | J2EE
                <br>
                <a href="http://twitter.com/" ><img src="images/twitter.png" alt="http://twitter.com" width="25" height="25"></a>
                <a href="http://www.facebook.com/" ><img src="images/Facebook.png" alt="http://www.facebook.com" width="25" height="25"></a>
                <br>
                Este sitio se visualiza mejor con Google Chrome
            </div>
        </div>
    </center>
    </body>
</html>
<%
    }
%>