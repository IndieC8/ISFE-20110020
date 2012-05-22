<%-- 
    Document   : Perfil
    Created on : 23/01/2012, 03:09:10 PM
    Author     : ISFE
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
        <title> ISFE - Perfil </title>
        <link rel="stylesheet" type="text/css" href="estilo/style.css" />
        <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
  

        <script type="text/javascript">
                
            $(function(){

                 // Tabs
                $('#tabs').tabs();
	

                // Dialog			
                $('#dialog').dialog({
                    autoOpen: false,
                    width: 600,
                    buttons: {
                        "Ok": function() { 
                            $(this).dialog("close"); 
                        }, 
                        "Cancel": function() { 
                            $(this).dialog("close"); 
                        } 
                    }
                });
		
                			
                //hover states on the static widgets
                $('#dialog_link, ul#icons li').hover(
                function() { $(this).addClass('ui-state-hover'); }, 
                function() { $(this).removeClass('ui-state-hover'); }
            );
				
            });
        </script>
        

    </head>

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
                              <!--  <li><a href="perfil/consultarPerfil.jsp">Consultar Perfil</a></li>  -->
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
                <br>
                <br>
                <div class="titulo_pagina">Perfil</div>
                <center>
                    <!--Comienza el formulario-->	
                    <div id="tabs">
                        <ul>
                            <li><a href="#tabs-1">Administrar Perfil</a></li>
                        </ul>
                        <div id="tabs-1">
                            <table border="0">
                                <tbody>
                                    <tr>
                                        <td><a href="perfil/modificarPerfil.jsp"><img src="images/formularios/edit_profile.png"/></a></td>
                                        <td>Modificar Perfil</td>
                                    </tr>
                                    <tr>
                                        <td><a href="perfil/administrarFIELyCSD.jsp"><img src="images/formularios/admin_fiel_csd.png"/></a></td>
                                        <td>Administrar FIEL y CSD</td>
                                    </tr>
                                    <tr>
                                        <td><a href="perfil/administrarClientes.jsp"><img src="images/formularios/add_client.png"/></a></td>
                                        <td>Administrar Clientes</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <br><br>
                </center>
            </div>

    </center>
</body>
</html>
<%
    }
%>