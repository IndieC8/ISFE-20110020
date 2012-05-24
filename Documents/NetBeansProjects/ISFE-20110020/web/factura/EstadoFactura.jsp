<%-- 
    Document   : Generar Factura Imprimible
    Created on : 23/01/2012, 03:09:10 PM
    Author     : Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!    String contribuyente = "";
    String id = "";
%>
<%
    HttpSession sesionOk = request.getSession();
    if (sesionOk.getAttribute("contribuyente") == null) {
%>
<jsp:forward page="/index.jsp">
    <jsp:param name="error" value="Es obligatorio identificarse"></jsp:param>
</jsp:forward>
<%        } else {
    contribuyente = (String) sesionOk.getAttribute("contribuyente");//Recoge la session
    id = (String) sesionOk.getAttribute("identificador");//Recoge la session
%>
<!DOCTYPE html>
<html>
    <head>
        <title>ISFE - Estado de la Factura</title>
        <link rel="stylesheet" type="text/css" href="../estilo/style.css" />
        <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.17.custom.min.js"></script>
        <script src="../js/ui/jquery.ui.core.js"></script>
        <script src="../js/ui/jquery.ui.widget.js"></script>
        <script src="../js/ui/jquery.ui.datepicker.js"></script>
        <script src="../js/ui/jquery.ui.dialog.js"></script>
        <script src ="../js/jquery.factura.js"></script>


        <script type="text/javascript">
            function AJAXCancelar(){
                $.ajax({
                    url: "../Mensuales",
                    type: "POST",
                    data: "Factura=Buscar&idUsuario=<%=id%>",
                    success: function(data){
                        if(data != 0){
                            $("#TablaFacturasACancelar").html(data);
                            $("#TablaFacturasACancelar").show();
                        }else{
                            $("#ErrorFechaCancelar").text("No hay facturas con esa fecha!"); 
                        }
                    }
                });
            }
            
            function AJAXBuscar(fecha){
                $.ajax({
                    url: "../Impresa",
                    type: "POST",
                    data: "Factura=Buscar&idUsuario=<%=id%>&Fecha="+fecha,
                    success: function(data){
                        if(data == 0){
                            $("#ErrorFechaElaboracion").text("No hay factura con esa fecha!");
                        }else{
                            $("#ResultadoFacturas").html(data);
                            $("#ResultadoFacturas").show();
                        }
                    }
                });
            }
            
            function GenerarPDF(idFactura){
                $("#idFacturaImpresa").val(idFactura);
                document.GenerarPDF.submit();
            }
            
            
            
        </script>
    </head>
    <body>
        <!--Aqui va el dialogo de Eliminar Cliente-->
        <div id="dialogCancelar" title="ISFE- Cancelar Factura">
            <p class="validateTips">
                <img src="../images/important.gif" />
                &nbsp; 
                ¿Seguro que deseas cancelar tu facutura?
            </p>
            <form>
                <fieldset>
                    <label class="texto" id="instruccionCancelacion"></label>
                </fieldset>
            </form>
        </div>
        <!--Aqui termina el dialogo de Eliminar Cliente-->

    <center>
        <div class="principal">
            <div class="header">
                <div class="logo"><a href="../index-user.jsp" ><img src="../images/logo1.png" alt="ISFE" height="164"/></a></div>
            </div>
            <div class="contenido_principal">
                <!-- Comienza Menu -->
                <div class="menu">
                    <ul>
                        <li><a href="../index-user.jsp" ><img src="../images/icons/home.png" alt="" height="20"/> Home</a></li>
                        <li><a href="../contact.jsp"><img src="../images/icons/contacto_ico.png" alt=""/> Contacto</a></li>
                        <li><a href="../Usar.jsp"><img src="../images/icons/valida_ico.png" alt=""/>¿C&oacute;mo usar ISFE?</a></li>
                        <li><a href="../perfil.jsp" id="current"><img src="../images/icons/perfil_ico.png" alt=""/> Perfil</a>
                            <ul>
                                <!--  <li><a href="../perfil/consultarPerfil.jsp">Consultar Perfil</a></li>-->
                                <li><a href="../perfil/modificarPerfil.jsp">Modificar Perfil</a></li>
                                <li><a href="../perfil/administrarFIELyCSD.jsp">Administrar FIEL y CSD</a></li>
                                <li><a href="../perfil/administrarClientes.jsp">Administrar Clientes</a></li>
                            </ul>
                        </li>
                        <li><a href="../factura.jsp"><img src="../images/icons/factura_ico.png" alt=""/> Factura</a>
                            <ul>
                                <li><a href="../factura/generarFacturaElectronica.jsp">Generar Factura Electr&oacute;nica</a></li>
                                <li><a href="../factura/EstadoFactura.jsp">Estado de la Factura</a></li>
                            </ul>
                        </li>                       
                        <li><a href="../cerrar.jsp"><img src="../images/icons/ingreso_ico.png"/> Cerrar Sesión &nbsp; &nbsp; <% out.println(contribuyente);%></a></li>
                    </ul>
                </div>
                <!-- Termina Menu -->
                <br><br>
                <div class="titulo_pagina">Estado de tus Facturas</div>
                <center>
                    <!--Comienza el formulario-->	
                    <div id="tabs">
                        <ul>
                            <li><a href="#tabs-1">Generar Facutra Impresa</a></li>
                            <li><a href="#tabs-2">Cancelar Facturas</a></li>
                        </ul>
                        <div id="tabs-1">
                            <form id="buscarFacturasAdministrar">
                                <font color="red">Ingrese la fecha de Elaboración de tu Factura</font><br>
                                <table border="0">
                                    <tr>
                                        <td rowspan="19"><img src="../images/formularios/factura_pdf.jpg"/></td>
                                        <td>Fecha de Elaboración: </td>
                                        <td>
                                            <input type="text" id="datepicker" onclick="Limpiar()" width="20"/>
                                            <label id="ErrorFechaElaboracion"></label>
                                        </td>
                                        <td><input type="button" onclick="Buscar()" value="&nbsp; Buscar Facturas &nbsp;" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                    </tr>
                                </table>
                            </form>
                            <br/><br/>
                            <div id="ResultadoFacturas" style="display: none"></div>
                        </div>
                        <div id="tabs-2">
                            <form id="buscarFacturasCancelar">
                                <font color="red">Ingrese el día de Elaboración de tu Factura</font><br>
                                <table border="0">
                                    <tr>
                                        <td rowspan="19"><img src="../images/formularios/factura_cancelar.jpg"/></td>
                                        <td>Fecha de Elaboración: </td>
                                        <td>
                                            <input type="text" id="cancelar" onclick="Limpiar()" width="20"/>
                                            <label id="ErrorFechaCancelar"></label>
                                        </td>
                                        <td><input type="button" onclick="BuscarCancelacion()" value="&nbsp; Buscar Facturas &nbsp;" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                    </tr>
                                </table>
                            </form>
                            <form action="../Impresa" name="GenerarPDF" method="POST"> <input type="hidden" name="idFacturaImpresa" value="idFacturaImpresa"/></form>
                            <br/><br/>
                            <div id="TablaFacturasACancelar" style="display: none"></div>
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
                <a href="http://twitter.com/" ><img src="../images/twitter.png" alt="http://twitter.com" width="25" height="25"></a>
                <a href="http://www.facebook.com/" ><img src="../images/Facebook.png" alt="http://www.facebook.com" width="25" height="25"></a>
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