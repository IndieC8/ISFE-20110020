<%-- 
    Document   : administrarFIELyCSD
    Created on : 1/02/2012, 11:13:09 AM
    Author     : ISFE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>ISFE - Administrar FIEL y CSD</title>
        <link rel="stylesheet" type="text/css" href="../estilo/style.css" />
        <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.17.custom.min.js"></script>
        <script type="text/javascript" src="../js/jquery.MultiFile.js"></script> <!-- Validar los archivos a subir -->
        <script type="text/javascript" src="../js/jquery.menu.js"></script>
        <script type="text/javascript">
                
                
            $(function(){
                // Tabs
                $('#tabs').tabs();

							
            });
                                
                                                                           
            $(function(){
                $("#archivoREQAdministrar").MultiFile({
                    accept:'req', max:1, STRING: {
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
              
        </script>
    </head>
    <body>
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
                                <li><a href="../factura/generarFacturaImprimible.jsp">Generar Factura Imprimible</a></li>
                            </ul>
                        </li>                       
                        <li><a id="cerrarSesion"><img src="../images/icons/ingreso_ico.png" alt=""/> Cerrar Sesión</a></li>
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
                            <table width="60%">
                                <form method="post" enctype="multipart/form-data" id="subirFIELAdministrar">
                                    <tr>
                                        <td width="30%">
                                            <input type="file" id="archivoREQAdministrar" onclick="LimpiarError()" />
                                            <label id="ErrorArchivoREQ"></label>
                                        </td>

                                        <td>
                                            <input type="button" value=" &nbsp; Subir FIEL &nbsp;" name="subirFIEL" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                                        </td>
                                    </tr>
                                </form>
                            </table>
                        </div>
                        <div id="tabs-2">
                            <br/>
                            <label class="Instrucciones"> Selecciona el archivo FIEL</label>
                            <br/>
                            <table width="60%">
                                <form method="post" enctype="multipart/form-data" id="subirCSDAdministrar">
                                    <tr>
                                        <td width="30%">
                                            <input type="file" id="archivoCERAdministrar" onclick="LimpiarError()" />
                                            <label id="ErrorArchivoCER"></label>
                                        </td>

                                        <td>
                                            <input type="button" value=" &nbsp; Subir CSD &nbsp;" name="subirCSD" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                                        </td>
                                    </tr>
                                </form>
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
                <a href="http://twitter.com/" ><img src="../images/twitter.png" alt="http://twitter.com" width="25" height="25"></a>
                <a href="http://www.facebook.com/" ><img src="../images/Facebook.png" alt="http://www.facebook.com" width="25" height="25"></a>
                <br>
                Este sitio se visualiza mejor con Google Chrome
            </div>
        </div>
    </center>
</body>
</html>