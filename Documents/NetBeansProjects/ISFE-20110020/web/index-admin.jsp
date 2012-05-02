<%--
    Document   : index-admin
    Created on : 27/04/2012, 11:43:16 AM
    Author     : kawatoto
--%>

<%@page import="java.util.Calendar"%>
<%@page import="subsistemaAutomatico.ReportesMensuales"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--
    String contribuyente = "";
    int id = 0 ;
    HttpSession sesionOk = request.getSession();
    if (sesionOk.getAttribute("contribuyente") == null) {
        %>
        <jsp:forward page="index.jsp">
            <jsp:param name="error" value="Es obligatorio identificarse"></jsp:param>
        </jsp:forward>
        <%
    } else {
        contribuyente = (String) sesionOk.getAttribute("contribuyente");//Recoge la session
        id = (Integer) sesionOk.getAttribute("identificador");//Recoge la session
        out.println(id);
 --%>
<!DOCTYPE html>
<html>
	<head>
            <title>ISFE - Administrador</title>
            <link rel="stylesheet" type="text/css" href="estilo/style.css" />
            <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
            <script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
            <script src="js/ui/jquery.ui.core.js"></script>
            <script src="js/ui/jquery.ui.widget.js"></script>
            <script src="js/ui/jquery.effects.core.js"></script>
            <script type="text/javascript">
			$(function(){

				// Slider
				$('#slider').slider({
					range: true,
					values: [17, 67]
				});
			});

                        $(function(){
                            // Tabs
                            $('#tabs').tabs();
                        });
		</script>

	<center>
            <div class="principal">
            <div class="header">
                <div class="logo"><a href="index-user.jsp" ><img src="images/logo1.png" alt="ISFE" height="164"/></a></div>
            </div>
		<div class="contenido_principal">
		<!-- Comienza Menu -->
                <div class="menu">
                    <ul>
                        <li><a href="cerrar.jsp" id="cerrarSesion"><img src="images/icons/ingreso_ico.png"/> Cerrar Sesión</a>
                    </ul>
                </div>
		<!-- Termina Menu -->
                <br/>
		<div class="titulo_pagina">Bienvenido Administrador</div>
		<center>
                  <div id="tabs">
                        <ul>
                           <li><a href="#tabs-1">Solicitud de Folios al SAT</a></li>
                           <li><a href="#tabs-2">Env&iacute;o de Reportes Mensuales</a></li>
                           <li><a href="#tabs-3">Administraci&oacute;n de Facturas</a></li>
                        </ul>
                        <div id="tabs-1">
                            <table>
                                <tr>
                                    <td>
                                        <%
                                            Calendar fechaActual = Calendar.getInstance();
                                            if(fechaActual.get(Calendar.DAY_OF_MONTH)<5 || (fechaActual.get(Calendar.DAY_OF_MONTH)>25&&fechaActual.get(Calendar.MONTH)-1<fechaActual.get(Calendar.MONTH)))
                                            {
                                                out.println("Seleccione el numero de folios a solicitar:<br>");
                                                out.println("<form action=\"Administrador\" method=\"post\">");
                                                out.println("<input type=\"number\" min=\"1\" value=\"0\" id=\"numeroFolios\" name=\"numeroFolios\"/>");
                                                out.println("</td>");
                                                out.println("<td>");
                                                out.println("<input type=\"submit\" value=\"Solicitar Bloque de Folios al SAT\" name=\"solicitarFolios\" class=\"ui-button ui-widget ui-state-default ui-corner-all\" role=\"button\" aria-disabled=\"false\"/>");
                                                out.println("</form>");
                                            }
                                            else
                                            {
                                                out.println("No es aun fecha de generar Reportes Mensuales");
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                        <%

                            String mensaje=(String)session.getAttribute("mensaje");
                            session.setAttribute("mensaje", null);
                            if(mensaje!=null)
                            {
                                out.println("<label>"+mensaje+"</label><br>");
                            }
                        %>
                        </div>
                        <div id="tabs-2">

                        </div>
                        <div id="tabs-3">

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
