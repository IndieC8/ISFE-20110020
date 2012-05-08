<%-- 
    Document   : modificarPerfil
    Created on : 1/02/2012, 11:12:34 AM
    Author     : ISFE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="dao.*" %>

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
        <title>ISFE - Modificar Perfil</title>
        <link rel="stylesheet" type="text/css" href="../estilo/style.css" />
        <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.17.custom.min.js"></script>
        <script src="../js/jquery.perfil.js"></script>
        <script src="../js/ui/jquery.ui.dialog.js"></script>
        <script src="../js/jquery.validate.js"></script>

        <script type="text/javascript">
            $(document).ready(function(){
                
                               
               $.ajax({
                        type:"POST",
                        url:"../Perfil",
                        data:"idUsuario=<%=id%>",
                        success: function(data){
                            var aux = data.split("/");
                            
                            $("#nombreModificarPerfil").text(aux[0]);
                            $("#passwordModificarPerfil").val(aux[1]);
                            $("#mailModificarPerfil").val(aux[2]);
                            $("#telefonoModificarPerfil").val(aux[3]);
                            $("#calleModificarPerfil").val(aux[4]);
                            $("#exteriorModificarPerfil").val(aux[5]);
                            $("#interiorModificarPerfil").val(aux[6]);
                            $("#referenciaModificarPerfil").val(aux[7]);
                            $("#ColoniaModificarPerfil").val(aux[8]);
                            $("#MunicipioModificarPerfil").val(aux[9]);
                            
                        }
               });
               
               $("#FormularioModificacionPerfil").validate();
                 
            });
                
            
        </script>
    </head>
    <body>
        <!--Aqui va el dialogo de Modificar Password-->
        <div id="dialogPwdPerfil" title="ISFE- Modificar Perfil">
            <p class="validateTips">
                <img src="../images/key.png" />
                &nbsp;
                Define tu nueva Contraseña
                <label id="errorModificarPwd"></label>
            </p>
            <form>
                <fieldset>  
                    Contraseña: &nbsp; &nbsp;
                    <input type="password" id="pwdModificarPerfil" maxlength="30" />
                    <br>
                    Repite tu Contraseña: &nbsp; &nbsp;
                    <input type="password" id="pwd2ModificarPerfil" maxlength="30" />
                </fieldset>
            </form>
        </div>
        <!--Aqui termina el dialogo de Modificar Password-->

        <!--Aqui va el dialogo de Modificar Colonia-->
        <div id="dialogDireccionPerfil" title="ISFE- Modificar Perfil">
            <p class="validateTips">
                <img src="../images/info.gif" />
                &nbsp;
                Define tu Colonia y Municipio
                <label id="errorModificarColonia"></label>
            </p>
            <form>
                <fieldset>  
                    <div> Código Postal: <input type="text" maxlength="5" onblur="BuscarCP()" size="5" onkeypress="OnlyNumber(this.value,this)" id="codigoModificarPerfil" /> </div>
                    <br>
                    <div> Estado:  <input type="text" readonly="readonly" id="estadoModificarPerfil" /></div>
                    <br/>
                    <div>Delegación/Municipio: &nbsp; <select id="municipioModificarPerfil"></select> </div>
                    <br/>
                    <div>Colonia/Localidad: &nbsp; <select id="localidadModificarPerfil"></select> </div>
                </fieldset>
            </form>
        </div>
        <!--Aqui termina el dialogo de Modificar Colonia-->
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
                                <!-- <li><a href="../perfil/consultarPerfil.jsp">Consultar Perfil</a></li> -->
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
                        <li><a href="../cerrar.jsp" id="cerrarSesion"><img src="../images/icons/ingreso_ico.png" alt=""/> Cerrar Sesión &nbsp; &nbsp;  <%out.println(contribuyente);%> </a></li>
                    </ul>
                </div>
                <!-- Termina Menu -->
                <div class="titulo_pagina"> <br/>Modificar Perfil</div>
                <br/>
                <center>
                    <!--Comienza el formulario-->	
                    <div id="tabs">
                        <ul>
                            <li><a href="#tabs-1">Modificar Datos de Usuario</a></li>
                            <li><a href="#tabs-2">Verificar nuevos datos</a></li>
                        </ul>
                        <div id="tabs-1">
                            <div align="left">
                                <p>
                                    USUARIO: <label class="texto" id="nombreModificarPerfil"></label> 
                                </p>
                            </div>
                            <br/>
                            <form id="FormularioModificacionPerfil">
                                <table border="0" id="mytable">
                                    <thead>
                                        <tr>
                                            <th scope="col">Elemento</th>
                                            <th scope="col">Valor</th>
                                            <th scope="col">Editar</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>Mail</td>
                                            <td><input type="text" id="mailModificarPerfil" size="35" readonly="readonly" maxlength="40" class="required email" /></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="mailModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Teléfono</td>
                                            <td><input type="text" id="telefonoModificarPerfil" size="35" readonly="readonly" maxlength="20" onkeypress="OnlyNumber(this.value,this)"/></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="telefonoModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Contraseña</td>
                                            <td><input type="password" size="35" id="passwordModificarPerfil" readonly="readonly" /></td>
                                            <td><input type="button" onclick="ModificarPassword()" value="Editar" name="passwordModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Av / Calle</td>
                                            <td><input type="text" size="35" maxlength="50" id="calleModificarPerfil" readonly="readonly" style="text-transform: uppercase" /></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="calleModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Número Interior</td>
                                            <td><input type="text" size="35" maxlength="5" id="interiorModificarPerfil" readonly="readonly" style="text-transform: uppercase" /></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="interiorModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Número Exterior</td>
                                            <td><input type="text" size="35" maxlength="5" id="exteriorModificarPerfil" readonly="readonly" onkeypress="OnlyNumber(this.value,this)" /></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="exteriorModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>

                                        <tr>
                                            <td>Colonia</td>
                                            <td><input type="text" size="35" id="ColoniaModificarPerfil" readonly="readonly" /></td>
                                            <td><input type="button" onclick="ModificarDireccion()" value="Editar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Municipio</td>
                                            <td><input type="text" size="35" id="MunicipioModificarPerfil" readonly="readonly" /></td>
                                            <td><input type="button" onclick="ModificarDireccion()" value="Editar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Referencia</td>
                                            <td><input type="text" size="35" maxlength="25" id="referenciaModificarPerfil" readonly="readonly" style="text-transform: uppercase" /></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="referenciaModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <br><br>
                                <div align="right">
                                    <input type="submit" style="display: none" value="Confirmar Modificaciones" id="ConfirmarModificacion" name="mailModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                                </div>
                            </form>
                        </div>
                        <div id="tabs-2">

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