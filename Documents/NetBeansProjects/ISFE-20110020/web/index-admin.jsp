<%--
    Document   : index-admin
    Created on : 27/04/2012, 11:43:16 AM
    Author     : kawatoto
--%>

<%@page import="java.util.Calendar"%>
<%@page import="subsistemaISFE.ReportesMensuales"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Calendar fechaActual = Calendar.getInstance();%>
<%!  String contribuyente = ""; %>
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
 %>
<!DOCTYPE html>
<html>
	<head>
            <title>ISFE - Administrador</title>
            <link rel="stylesheet" type="text/css" href="estilo/style.css" />
            <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
            <script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
            <script type="text/javascript" src="js/jquery.menu.js"></script>
            
            <script type="text/javascript">
           
            function ValidarFolios() {
                $("#ConfirmacionDeSolicitudFolios").text("");
                var numFolios=$("#numeroFolios").val();
                $.ajax({
                    type: "POST",
                    url: "Administrador",
                    data: "Action=folios&numeroFolios="+numFolios,
                    success: function(data){
                        $("#ConfirmacionDeSolicitudFolios").text(data);
                    }
                });
            }

            function GenerarReportes() {
            $("#ConfirmacionEnvioDeReportesMensuales").text("");
                var mesReportado=$("#mesReportado").val();
                var anioReportado=$("#anioReportado").val();
                $.ajax({
                    type: "POST",
                    url: "Administrador",
                    data: "Action=reportes&mesReportado="+mesReportado+"&anioReportado="+anioReportado,
                    success: function(data){
                        $("#ConfirmacionEnvioDeReportesMensuales").text(data);
                    }
                });
            }

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
                        <li><a href="cerrar.jsp" id="cerrarSesion"><img src="images/icons/ingreso_ico.png"/> Cerrar Sesión &nbsp; &nbsp; <% out.println(contribuyente); %></a>
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
                        </ul>
                        <div id="tabs-1">
                            <table>
                                <tr>
                                    <td>
                                        <%
                                            if(fechaActual.get(Calendar.DAY_OF_MONTH)<5 || (fechaActual.get(Calendar.DAY_OF_MONTH)>25&&fechaActual.get(Calendar.MONTH)-1<fechaActual.get(Calendar.MONTH)))
                                            {
                                                out.println("Seleccione el numero de folios a solicitar:<br>");
                                                out.println("<form id=\"formFolios\" action=\"Administrador\" name=\"formFolios\" method=\"post\">");
                                                out.println("<input type=\"number\" min=\"1\" value=\"1\" id=\"numeroFolios\" name=\"numeroFolios\"/>");
                                                out.println("</td>");
                                                out.println("<td>");
                                                out.println("<input type=\"button\" onclick=\"ValidarFolios()\"  id=\"solicitarFolios\" value=\"Solicitar Bloque de Folios al SAT\" class=\"ui-button ui-widget ui-state-default ui-corner-all\" role=\"button\" aria-disabled=\"false\"/>");
                                                out.println("</form>");
                                            }
                                            else
                                            {
                                                out.println("No es aun fecha de Solicitar Folios al SAT");
                                            }
                                        %>
                                    </td>
                                </tr>
                            </table>
                                    <br><br>
                                    <label id="ConfirmacionDeSolicitudFolios"></label>
                        </div>
                        <div id="tabs-2">
                            <table>
                                <tr>
                                    <td>
                                        <%
                                            if(fechaActual.get(Calendar.DAY_OF_MONTH)<5 || (fechaActual.get(Calendar.DAY_OF_MONTH)>25&&fechaActual.get(Calendar.MONTH)-1<fechaActual.get(Calendar.MONTH)))
                                            {
                                                out.println("Esta a punto de realizar el Reporte Mensual:<br>");
                                                out.println("<form id=\"formReportes\" action=\"Administrador\" name=\"formReportes\" method=\"post\">");
                                                out.println("<input type=\"hidden\" id=\"mesReportado\" name=\"mesReportado\" value=\""+fechaActual.get(Calendar.MONTH)+"\"/>");
                                                out.println("<input type=\"hidden\" id=\"anioReportado\" name=\"anioReportado\" value=\""+fechaActual.get(Calendar.YEAR)+"\"/>");
                                                out.println("<input type=\"button\" onclick=\"GenerarReportes()\" id=\"generarReportes\" value=\"Generar Reporte Mensual al SAT\" class=\"ui-button ui-widget ui-state-default ui-corner-all\" role=\"button\" aria-disabled=\"false\"/>");
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
                                    <br><br>
                                    <label id="ConfirmacionEnvioDeReportesMensuales"></label>
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
<%}%>