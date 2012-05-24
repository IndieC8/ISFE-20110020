<%-- 
    Document   : factura
    Created on : 1/02/2012, 09:07:44 PM
    Author     : kawatoto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
<!DOCTYPE html>
<html>
	<head>
            <title> ISFE - Factura </title>
            <link rel="stylesheet" type="text/css" href="estilo/style.css" />
            <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
            <script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
             <script type="text/javascript" src="js/jquery.menu.js"></script>
            
                   
	</head>
	<body>
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
                               <!--  <li><a href="perfil/consultarPerfil.jsp">Consultar Perfil</a></li> -->
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
                <br><br>
		<div class="titulo_pagina">Factura</div>
		<center>
                <!--Comienza el formulario-->	
		<div id="tabs">
			<ul>
                           <li><a href="#tabs-1">Facturaci&oacute;n Electr&oacute;nica</a></li>
			</ul>
			<div id="tabs-1">
                           <table border="0">
                              <tbody>
                                  <tr>
                                    <td><a href="factura/generarFacturaElectronica.jsp"><img src="images/formularios/generar_factura_electronica.png"/></a></td>
                                    <td>Generar Factura Electr&oacute;nica</td>
                                 </tr>
                                 <tr>
                                      <td><a href="factura/EstadoFactura.jsp"><img width="64" src="images/formularios/estado_factura.png"/></a></td>
                                      <td>Estado de la Factura</td>
                                  </tr>
                              </tbody>
                           </table>
                        </div>
		</div>
                <br><br>
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