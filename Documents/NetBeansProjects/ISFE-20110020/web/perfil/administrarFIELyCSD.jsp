<%-- 
    Document   : administrarFIELyCSD
    Created on : 1/02/2012, 11:13:09 AM
    Author     : ISFE
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

    String valor = (String) request.getParameter("valor");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>ISFE - Administrar FIEL y CSD</title>
        <link rel="stylesheet" type="text/css" href="../estilo/style.css" />
        <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.17.custom.min.js"></script>
        <script type="text/javascript" src="../js/jquery.MultiFile.js"></script> <!-- Validar los archivos a subir -->
        <script type="text/javascript" src="../js/jquery.menu.js"></script>
        <script type="text/javascript" src="../js/ui/jquery.ui.dialog.js"></script>
        
        <script type="text/javascript">
            
            $(document).ready(function(){
                $("#idUsuario").val("<%=id%>");
                $("#idUsuarioCER").val("<%=id%>");
                
                if("<%=valor%>" == "cer"){
                    $("#mensajeConfirmarPerfil").text("Tu CSD se ha actualizado!");
                    $("#DialogoConfirmarArchivo").dialog("open");
                }
                
                if("<%=valor%>" == "fiel"){
                    $("#mensajeConfirmarPerfil").text("Tu FIEL se ha actualizado!");
                    $("#DialogoConfirmarArchivo").dialog("open");
                }
                
            });
            
                                                                          
            $(function(){
                $("#archivoREQAdministrar").MultiFile({
                    accept:'key', max:1, STRING: {
                        remove:'X',
                        selected:'Selecionado: $file',
                        denied:'REQ/Archivo de extención $ext invalido!',
                        duplicate:'Archivo ya selecionado:\n$file!'

                    }
                });
                                    
                $("#archivoCERAdministrar").MultiFile({
                    accept:'cer', max:1, STRING: {
                        remove:'X',
                        selected:'Selecionado: $file',
                        denied:'CER/Archivo de extención $ext invalido!',
                        duplicate:'Archivo ya selecionado:\n$file!'

                    }
                });
            });
            
            $(function() {
                // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
                $( "#dialog:ui-dialog" ).dialog( "destroy" );
    
                $("#DialogoConfirmarArchivo").dialog({
                    autoOpen: false,
                    height: 200,
                    width: 350,
                    modal: true,
                    buttons: {
                        "Aceptar": function() {
                            $( this ).dialog("close");                        
                        }
                    },
                    close: function() {
                        allFields.val( "" ).removeClass( "ui-state-error" );
                    }
                });
            });
            
            function ValidarPrivadaFIEL(){
               $("#ErrorClavePrivada").text("");
               if( $("#llavePrivada").val() != ""){
                   document.upformFIEL.submit();
               }else{
                   $("#ErrorClavePrivada").text("Ingresa tu clave Privada");
                   return false;
               }
            }
              
        </script>
    </head>
    <body>
        <!--Dialogo de Confirmar Perfil-->
        <div id="DialogoConfirmarArchivo" title="ISFE- Administrar FIEL y CSD">
            <p class="validateTips">
                <img src="../images/confirmar.jpg" />
                &nbsp;  
                <label class="texto" id="mensajeConfirmarPerfil"></label>
            </p>
        </div>
        <!--Termina el dialogo de Confirmacion de Perfil-->

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
                                <!-- <li><a href="../perfil/consultarPerfil.jsp">Consultar Perfil</a></li>  -->
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
                <div class="titulo_pagina">Administrar FIEL y CSD</div>
                <center>
                    <!--Comienza el formulario-->	
                    <div id="tabs">
                        <ul>
                            <li><a href="#tabs-1">Cambiar FIEL</a></li>
                            <li><a href="#tabs-2">Cambiar CSD</a></li>
                        </ul>
                        <div id="tabs-1">
                            <br/>
                            <label class="Instrucciones"> Selecciona el archivo FIEL</label>
                            <br/>

                            <form method="post" action="../SubirArchivo" name="upformFIEL" id="subirFIELAdministrar" enctype="multipart/form-data">
                                <table width="60%">
                                    <tr>
                                        <td width="30%">
                                            Archivo .KEY de la FIEL
                                            <input type="file" name="fileupload" id="archivoREQAdministrar" onclick="LimpiarError()" />
                                            <input type="hidden" name="mucho" value="upload"/>
                                            <label id="ErrorArchivoREQ"></label>
                                        </td>

                                        <td>
                                            Clave Privada:  <input type="password" id="llavePrivada" name="llavePrivada" />
                                        </td>

                                        <td>
                                            <input type="hidden" name="idUsuario" id="idUsuario"/>
                                            <input type="hidden" name="accion" value="modificar"/>
                                            <input type="hidden" name="registro" value="fiel"/>
                                            <input type="button" onclick="ValidarPrivadaFIEL()" value="&nbsp; &nbsp; Subir FIEL &nbsp; &nbsp;" name="subirFIEL" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                            <label id="ErrorClavePrivada"></label>
                        </div>
                        <div id="tabs-2">
                            <br/>
                            <label class="Instrucciones"> Selecciona el archivo CER</label>
                            <br/>
                            <form method="post" action="../SubirArchivo" name="upform" id="subirCSDAdministrar" enctype="multipart/form-data">
                                <table width="60%">
                                    <tr>
                                        <td width="30%">
                                            Archivo .CER de la Certificado CSD
                                            <input type="file" name="uploadfile" id="archivoCERAdministrar" onclick="LimpiarError()"/>
                                            <input type="hidden" name="todo" value="upload"/>
                                            <label id="ErrorArchivoCER"></label>
                                        </td>

                                        <td>
                                            <input type="hidden" name="idUsuario" id="idUsuarioCER"/>
                                            <input type="hidden" name="accion" value="modificar"/>
                                            <input type="hidden" name="registro" value="certificado"/>
                                            <input type="submit" value="&nbsp; &nbsp; Subir CSD &nbsp; &nbsp;" name="subirCSD" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                                        </td>
                                    </tr>
                                </table>
                            </form>
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