<%-- 
    Document   : modificarPerfil
    Created on : 1/02/2012, 11:12:34 AM
    Author     : ISFE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String contribuyente = "";
    int id = 0 ;
    HttpSession sesionOk = request.getSession();
    if (sesionOk.getAttribute("contribuyente") == null) {
        %>
        <jsp:forward page="/index.jsp">
            <jsp:param name="error" value="Es obligatorio identificarse"></jsp:param>
        </jsp:forward>
        <%
    } else {
        contribuyente = (String) sesionOk.getAttribute("contribuyente");//Recoge la session
        id = (Integer) sesionOk.getAttribute("identificador");//Recoge la session
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
				
                // Dialog Link
                $('#dialog_link').click(function(){
                    $('#dialog').dialog('open');
                    return false;
                });

				
            });
            
                        
            /*
             *Activar el dialogo de Modificar password
             */
            $(function() {
                // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
                $( "#dialog:ui-dialog" ).dialog( "destroy" );
		
                
		$("#dialogDireccionPerfil").dialog({
                    autoOpen: false,
                    height: 400,
                    width: 450,
                    modal: true,
                    buttons: {
                        "Aceptar": function() {
                            //alert("ACEPTANDO");
                            $("#ColoniaModificarPerfil").val($("#localidadModificarPerfil option:selected").html());
                            $("#MunicipioModificarPerfil").val($("#municipioModificarPerfil option:selected").html());
                            
                            $( this ).dialog("close");
                                        
                        },
                        Cancel: function() {
                            $( this ).dialog( "close" );
                        }
                    },
                    close: function() {
                        allFields.val( "" ).removeClass( "ui-state-error" );
                    }
                });
                
                
		
                $( "#dialogPwdPerfil" ).dialog({
                    autoOpen: false,
                    height: 300,
                    width: 350,
                    modal: true,
                    buttons: {
                        "Aceptar": function() {
                            var pwd1 = $("#pwdModificarPerfil").val();
                            var pwd2 = $("#pwd2ModificarPerfil").val();
                            var rpwd = /^([0-9a-zA-Z])+$/;
                            var campo = document.getElementById("pwdModificarPerfil");
                            
                            if(!campo.value.match(rpwd)){
                                $("#errorModificarPwd").text("La contraseña solo debe contener: a-z 0-9");
                                $("#pwdModificarPerfil").val("");
                                $("#pwd2ModificarPerfil").val("");
                                $("#pwdModificarPerfil").focus();
                                return false;
                            }else if(pwd1 != pwd2){
                                $("#errorModificarPwd").text("Las contraseñas deben ser iguales");
                                $("#pwdModificarPerfil").val("");
                                $("#pwd2ModificarPerfil").val("");
                                $("#pwdModificarPerfil").focus();
                                return false;
                            }else if(pwd1.length < 5){
                                $("#errorModificarPwd").text("Ingresa más de 5 caracteres");
                                $("#pwdModificarPerfil").val("");
                                $("#pwd2ModificarPerfil").val("");
                                $("#pwdModificarPerfil").focus();
                                return false;
                            }else{
                                $("#passwordModificarPerfil").val(pwd2);
                            }
                            
                            $( this ).dialog("close");
                                        
                        },
                        Cancel: function() {
                            $( this ).dialog( "close" );
                        }
                    },
                    close: function() {
                        allFields.val( "" ).removeClass( "ui-state-error" );
                    }
                });
            });

            function ModificarPassword(){
                $("#errorModificarPwd").text("");
                $("#pwdModificarPerfil").val("");
                $("#pwd2ModificarPerfil").val("");
                $( "#dialogPwdPerfil" ).dialog( "open" );
            }
            
            function ModificarDireccion(){
                $("#errorModificarColonia").text("");
                $("#codigoModificarPerfil").val("");
                $("#estadoModificarPerfil").val("");
                $("#localidadModificarPerfil").html("");
                $("#municipioModificarPerfil").html("");
                $("#dialogDireccionPerfil").dialog("open");
            }
            
            function BuscarCP(){
                $("#errorModificarColonia").text("");
                var codigo = $("#codigoModificarPerfil").val();
                obtenerEstado(codigo,"errorModificarColonia","estadoModificarPerfil","municipioModificarPerfil","localidadModificarPerfil","codigoModificarPerfil");
                
            }
            
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
                    <input type="password" id="pwdModificarPerfil" />
                    <br>
                    Repite tu Contraseña: &nbsp; &nbsp;
                    <input type="password" id="pwd2ModificarPerfil" />
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
                        <li><a href="cerrar.jsp" id="cerrarSesion"><img src="../images/icons/ingreso_ico.png" alt=""/> Cerrar Sesión</a></li>
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
                            <form>
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
                                            <td>Nombre o Razón Social</td>
                                            <td><input type="text" id="nombreModificarPerfil" size="35" readonly="readonly" style="text-transform: uppercase" /></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" name="nombreModificarPerfil" value="Editar"  class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>RFC</td>
                                            <td><input type="text" id="rfcModificarPerfil" size="35" readonly="readonly" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>CURP</td>
                                            <td><input type="text" id="curpModificarPerfil" size="35" readonly="readonly" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Mail</td>
                                            <td><input type="text" id="mailModificarPerfil" size="35" readonly="readonly" /></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="mailModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Teléfono</td>
                                            <td><input type="text" id="telefonoModificarPerfil" size="35" readonly="readonly"/></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="telefonoModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Contraseña</td>
                                            <td><input type="password" size="35" id="passwordModificarPerfil" readonly="readonly" /></td>
                                            <td><input type="button" onclick="ModificarPassword()" value="Editar" name="passwordModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Av / Calle</td>
                                            <td><input type="text" size="35" id="calleModificarPerfil" readonly="readonly" style="text-transform: uppercase" /></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="calleModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Número Interior</td>
                                            <td><input type="text" size="35" id="interiorModificarPerfil" readonly="readonly" style="text-transform: uppercase" /></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="interiorModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                        <tr>
                                            <td>Número Exterior</td>
                                            <td><input type="text" size="35" id="exteriorModificarPerfil" readonly="readonly" onkeypress="OnlyNumber(this.value,this)" /></td>
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
                                            <td><input type="text" size="35" id="referenciaModificarPerfil" readonly="readonly" style="text-transform: uppercase" /></td>
                                            <td><input type="button" onclick="EditarModificacion(this.name)" value="Editar" name="referenciaModificarPerfil" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                    </tbody>
                                </table>
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