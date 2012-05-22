<%-- 
    Document   : contacto2
    Created on : 30/03/2012
    Author     : isfe
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>


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
        <title>ISFE - Contacto</title>
        <link rel="stylesheet" type="text/css" href="estilo/style.css" />
        <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
        <script src="js/ui/jquery.ui.widget.js"></script>
        <script src="js/ui/jquery.ui.draggable.js"></script>
        <script src="js/ui/jquery.effects.core.js"></script>

        <script type="text/javascript">
            $(function(){
                //hover states on the static widgets
                $('#dialog_link, ul#icons li').hover(
                function() { $(this).addClass('ui-state-hover'); }, 
                function() { $(this).removeClass('ui-state-hover'); }
            );
				
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
                        <li><a href="index-user.jsp" ><img src="images/icons/home.png" alt="" height="20"/> Home</a></li>
                        <li><a href="contact.jsp"><img src="images/icons/contacto_ico.png" alt=""/> Contacto</a></li>
                        <li><a href="Usar.jsp"><img src="images/icons/valida_ico.png" alt=""/>¿C&oacute;mo usar ISFE?</a></li>
                        <li><a href="perfil.jsp" id="current"><img src="images/icons/perfil_ico.png" alt=""/> Perfil</a>
                            <ul>
                                <!-- <li><a href="perfil/consultarPerfil.jsp">Consultar Perfil</a></li> -->
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
                <div class="titulo_pagina">Inicio</div>
                <center>                   
                    <div id="fb-root"></div>
                    <script>(function(d, s, id) {
                        var js, fjs = d.getElementsByTagName(s)[0];
                        if (d.getElementById(id)) return;
                        js = d.createElement(s); js.id = id;
                        js.src = "//connect.facebook.net/es_LA/all.js#xfbml=1";
                        fjs.parentNode.insertBefore(js, fjs);
                    }(document, 'script', 'facebook-jssdk'));
                    </script>
                    <table border="0">
                        <tbody>
                            <tr>
                                <td>
                                    <script charset="utf-8" src="http://widgets.twimg.com/j/2/widget.js"></script>
                                    <script>
                                    new TWTR.Widget({
                                        version: 2,
                                        type: 'profile',
                                        rpp: 4,
                                        interval: 30000,
                                        width: 250,
                                        height: 300,
                                        theme: {
                                            shell: {
                                                background: '#333333',
                                                color: '#ffffff'
                                            },
                                            tweets: {
                                                background: '#c2c7cc',
                                                color: '#ffffff',
                                                links: '#0c1b47'
                                            }
                                        },
                                        features: {
                                            scrollbar: false,
                                            loop: false,
                                            live: true,
                                            behavior: 'all'
                                        }
                                    }).render().setUser('kawatoto').start();
                                    </script>
                                </td>
                                <td align="center">
                                    <img src="images/email.png" />
                                    <h3>¿Dudas? ¿Comentarios? ¿Sugerencias?<br>
                                        Ponte en contacto con nosotros<br>
                                        al siguiente e-mail:<br><br>
                                        isfe@isfesoft.com<br><br><br>
                                        o al siguiente tel&eacute;fono:<br><br>
                                        (55) 552-35-040
                                    </h3>
                                </td>
                                <td>
                                    <div class="fb-activity" data-site="https://www.facebook.com/events/239407649488864/" data-width="250" data-height="300" data-header="true" data-font="verdana" data-recommendations="true"></div>
                                </td>
                            </tr>
                        </tbody>
                    </table>                
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
<%  } //END ELSE 
%>