<%--
    Document   : error-404
    Created on : 26/04/2012, 11:46:04 AM
    Author     : kawatoto
--%>

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
            <title>ISFE - ERROR 404</title>
            <link rel="stylesheet" type="text/css" href="/ISFE-20110020/estilo/style.css" />
            <script type="text/javascript" src="/ISFE-20110020/js/jquery-1.7.1.min.js"></script>
            <script type="text/javascript" src="/ISFE-20110020/js/jquery-ui-1.8.17.custom.min.js"></script>
            <script src="/ISFE-20110020/js/ui/jquery.ui.core.js"></script>
            <script src="/ISFE-20110020/js/ui/jquery.ui.widget.js"></script>
            <script src="/ISFE-20110020/js/ui/jquery.effects.core.js"></script>
            <script type="text/javascript">
			$(function(){

				// Slider
				$('#slider').slider({
					range: true,
					values: [17, 67]
				});
			});
		</script>

	<center>
            <div class="principal">
            <div class="header">
                <div class="logo"><a href="/ISFE-20110020/index-user.jsp" ><img src="/ISFE-20110020/images/logo1.png" alt="ISFE" height="164"/></a></div>
            </div>
		<div class="contenido_principal">
		<!-- Comienza Menu -->
                <div class="menu">
                    <ul>
                        <li><a href="/ISFE-20110020/index-user.jsp" ><img src="/ISFE-20110020/images/icons/home.png" alt="" height="20"/> Home</a></li>
                        <li><a href="/ISFE-20110020/contact.jsp"><img src="/ISFE-20110020/images/icons/contacto_ico.png" alt=""/> Contacto</a></li>
                        <li><a href="/ISFE-20110020/Usar.jsp"><img src="/ISFE-20110020/images/icons/valida_ico.png" alt=""/>¿C&oacute;mo usar ISFE?</a></li>
                        <li><a href="/ISFE-20110020/perfil.jsp" id="current"><img src="/ISFE-20110020/images/icons/perfil_ico.png" alt=""/> Perfil</a>
                            <ul>
                              <!--  <li><a href="perfil/consultarPerfil.jsp">Consultar Perfil</a></li> -->
                                <li><a href="/ISFE-20110020/perfil/modificarPerfil.jsp">Modificar Perfil</a></li>
                                <li><a href="/ISFE-20110020/perfil/administrarFIELyCSD.jsp">Administrar FIEL y CSD</a></li>
                                <li><a href="/ISFE-20110020/perfil/administrarClientes.jsp">Administrar Clientes</a></li>
                            </ul>
                        </li>
                        <li><a href="/ISFE-20110020/factura.jsp"><img src="/ISFE-20110020/images/icons/factura_ico.png" alt=""/> Factura</a>
                            <ul>
                                <li><a href="/ISFE-20110020/factura/generarFacturaElectronica.jsp">Generar Factura Electr&oacute;nica</a></li>
                                <li><a href="/ISFE-20110020/factura/generarFacturaImprimible.jsp">Generar Factura Imprimible</a></li>
                            </ul>
                        </li>
                        <li><a href="/ISFE-20110020/cerrar.jsp" id="cerrarSesion"><img src="/ISFE-20110020/images/icons/ingreso_ico.png"/> Cerrar Sesión</a>
                        </li>
                    </ul>
                </div>
		<!-- Termina Menu -->
                <br/>
		<center>
                    <img src="/ISFE-20110020/images/error/error-404.png"/>
                </center>
            </div>
            <div class="footer">
                <br><br>
                Derechos reservados ISFE <br>
                HTML5 | CSS 2.0 | JavaScript | Apache Tomcat | J2EE
                <br>
                <a href="http://twitter.com/" ><img src="/ISFE-20110020/images/twitter.png" alt="http://twitter.com" width="25" height="25"></a>
                <a href="http://www.facebook.com/" ><img src="/ISFE-20110020/images/Facebook.png" alt="http://www.facebook.com" width="25" height="25"></a>
                <br>
                Este sitio se visualiza mejor con Google Chrome
            </div>
        </div>
    </center>
    </body>
</html>