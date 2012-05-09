<%-- 
    Document   : Registro
    Created on : 23/01/2012, 03:09:10 PM
    Author     : kawatoto
--%>

<%@page import="org.w3c.dom.Document"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" import="dao.*" %>

<!DOCTYPE html>
<html>
    <head>
        <title> ISFE - Registro </title>
        <link rel="stylesheet" type="text/css" href="estilo/style.css" />
        <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
        <script type="text/javascript" src="js/jquery.MultiFile.js"></script>
        <script type="text/javascript" src="js/jquery.menu.js"></script>
        <!--Este script es para la validacion de los formularios-->
        <script src="js/jquery.validate.js"></script>
        <script src="js/jquery.maskedinput.js"></script>
        <script src="js/ui/jquery.ui.dialog.js"></script>
        <script src="js/jquery.perfil.js"></script>


        <script type="text/javascript">
            var FIEL, CSD;
            $(document).ready(function(){
                                       
                $("#formulario_registro").validate({
                    rules: {
                        passwordUsuario:{
                            required: true,
                            minlength: 5
                        },
                        rePasswordUsuario: {
                            required: true,
                            minlength: 5,
                            equalTo: "#passwordUsuario"
                        }
                            
                    },
                    messages:{
                        passwordUsuario:{
                            required: "Ingresa una contaseña de acceso",
                            minlength: "Ingresa más de 5 caracteres"
                        },
                        rePasswordUsuario: {
                            required: "Confirma tu contraseña de acceso",
                            minlength: "Ingresa más de 5 caracteres",
                            equalTo: "Las contraseñas deben ser iguales"
                        }
                            
                    }
                });
            });
                
            $(function() {
                // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
                $( "#dialog:ui-dialog" ).dialog( "destroy" );
		
                
		$("#dialogPrivadaUsuario").dialog({
                    autoOpen: false,
                    height: 280,
                    width: 350,
                    modal: true,
                    buttons: {
                        "Aceptar": function() {
                            alert("ACEPTANDO");
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
                
                
            jQuery(function(){
                $("#UsuarioRFC").mask("aaa*-999999-aaa*");
                $("#RFCLogin").mask("aaa*-999999-aaa*");
                $("#CURPUsuario").mask("aaa*999999aaaaa*99");
            });
                    
                
                
            $(function(){
	
                // Tabs
                $('#tabs').tabs();
			
				
				
            });
            
	
                       
            var contribuyente;
            /*Evalua la opción si es Persona Fisica o moral*/
            function Contribuyente(value){
                contribuyten = "";
                $("#RazonUser").hide();
                $("#NombreUser").hide();
                $("#PaternoUser").hide();
                $("#MaternoUser").hide();
                $("#CURPUser").hide();
                $("#NombreUsuario").val("");
                $("#APaternoUsuario").val("");
                $("#AMaternoUsuario").val("");
                $("#CURPUsuario").val("");
                $("#RazonUsuario").val("");
                 
                if(value == "Moral"){
                    $("#RazonUser").show();
                    contribuyente = "Moral";
                }else{
                    $("#NombreUser").show();
                    $("#PaternoUser").show();
                    $("#MaternoUser").show();
                    $("#CURPUser").show();
                    contribuyente = "Fisica";
                }
            }
            
            function SubirCSD(){
                $("#dialogPrivadaUsuario").dialog("open");
            }
         
                       
            /*Busca si existe el rfc*/
            function validarRFC(){
                var rfc = $("#UsuarioRFC").val().toUpperCase();
                var aux = rfc.split('-');
                rfc = aux[0]+aux[1]+aux[2];
                $("#ErrorRFCUsuario").text("");
                
                $.ajax({
                    type: "POST",
                    url: "Registrar",
                    data: "usuario=validar&rfc="+rfc,
                    success: function(data){
                        $("#ErrorRFCUsuario").text(data);
                    }
                });
            }
            
            $.validator.setDefaults({
                submitHandler: function() {
                    $("#siguienteRegistro").attr("disabled", "disabled");
                    var rfc = $("#UsuarioRFC").val().toUpperCase();
                    var aux = rfc.split("-");
                    rfc = aux[0]+aux[1]+aux[2];
                    var tel = $("#UsuarioTelefono").val().toUpperCase();
                    var calle = $("#UsuarioCalle").val().toUpperCase();
                    var exterior = $("#UsuarioExterior").val().toUpperCase();
                    var interior = $("#UsuarioInterior").val().toUpperCase();
                    var col = $("#localidad").val();
                    var municipio = $("#municipio").val();
                    var mail = $("#emailUsuario").val().toUpperCase();
                    var referencia= $("#referenciaUsuario").val().toUpperCase();
                    var pwd = $("#rePasswordUsuario").val().toUpperCase();
                    if(contribuyente == "Moral"){
                        var razon = $("#RazonUsuario").val().toUpperCase();
                        $.ajax({
                            type: "POST",
                            url: "Registrar",
                            data: "usuario=Moral&razon="+razon+"&rfcUsuario="+rfc+"&telefono="+tel+"&calle="+calle+"&interio="+interior+"&exterior="+exterior+"&colonia="+col+"&municipio="+municipio+"&mail="+mail+"&referencia="+referencia+"&password="+pwd,
                            success: function(data){
                                alert(data);
                            }
                        });
                    }else{
                        var nombre = $("#nombreUsuario").val().toUpperCase();
                        var paterno = $("#APaternoUsuario").val().toUpperCase();
                        var materno =$("#AMaternoUsuario").val().toUpperCase();
                        var curp = $("#CURPUsuario").val().toUpperCase();
                        $.ajax({
                            type: "POST",
                            url: "Registrar",
                            data: "usuario=Fisica&nombre="+nombre+"&paterno="+paterno+"&materno="+materno+"&rfcUsuario="+rfc+"&curp="+curp+"&telefono="+tel+"&calle="+calle+"&interio="+interior+"&exterior="+exterior+"&colonia="+col+"&municipio="+municipio+"&mail="+mail+"&referencia="+referencia+"&password="+pwd,
                            success: function(data){
                                $("#formularioVerificacion").html(data);
                                $("#formularioVerificacion").show(); 
                            }
                        });
                    }
                    $("#tabs-2").show();
                    $("#tabs-3").show();
                    $("#FIELCSDUsuario").show();
                }
            });
        </script>

    </head>
    <!--Aqui va el dialogo de Modificar Password-->
    <div id="dialogPrivadaUsuario" title="ISFE- Registro de Usuario">
        <p class="validateTips">
            <img src="images/key.png" />
            &nbsp;
            Ingresa tu clave privada para válidar tu Certificado
        </p>
        <form>
            <fieldset>  
                Contraseña: &nbsp; &nbsp;
                <input type="password" id="clavePrivadaUsuario" />
            </fieldset>
        </form>
    </div>
    <!--Aqui termina el dialogo de Modificar Password-->
    <center>
        <div class="principal">
            <div class="header">
                <div class="logo"><a href="index.jsp" ><img src="images/logo1.png" alt="ISFE" height="164"/></a></div>
            </div>
            <div class="contenido_principal">
                <!-- Comienza Menu --> 
                <div class="menu">
                    <ul>
                        <li><a href="Uso.jsp"><img src="images/icons/valida_ico.png" alt=""/>¿C&oacute;mo usar ISFE?</a></li>
                        <li><a href="contacto.jsp"><img src="images/icons/contacto_ico.png" alt=""/>Contacto</a></li>
                        <li><a href="registro.jsp"><img src="images/icons/registro_ico.png" alt=""/> Registro</a></li>
                        <li><a href="index.jsp"><img src="images/icons/ingreso_ico.png" alt=""/> Ingreso</a></li>
                    </ul>
                </div>
                <!-- Termina Menu -->  
                <br><br>
                <div class="titulo_pagina">Registro</div>
                <center>
                    <!--Comienza el formulario-->	

                    <div id="tabs">
                        <ul>
                            <li><a href="#tabs-1">Datos de Usuario</a></li>
                            <li><a id="FIELCSDUsuario" href="#tabs-2">FIEL y CSD</a></li>
                            <li><a href="#tabs-3">Registrar Solicitante</a></li>
                        </ul>
                        <div id="tabs-1">
                            <font color="red">Los campos marcados con (*) son obligatorios</font><br><br>
                            <form action="" id="formulario_registro" method="post">

                                <table border="0">
                                    <tbody>
                                        <tr>
                                            <td rowspan="21"><img src="images/formularios/add_user.png"/></td>
                                            <td>Tipo de Contribuyente*:</td> 
                                            <td><input type="radio" class="required" id="personaMoral" name="contribuyenteUsuario" value="Moral" onclick="Contribuyente(this.value)">Persona Moral<input type="radio" class="required" name="contribuyenteUsuario" value="Fisica" onclick="Contribuyente(this.value)">Persona Fisica</td>
                                        </tr>
                                        <tr style="display:none" id="NombreUser">
                                            <td>Nombre:</td>
                                            <td><input type="text" maxlength="60" id="nombreUsuario" name="nombre" class="required" size="25" style="text-transform:uppercase" /></td>
                                        </tr>
                                        <tr style="display:none" id="PaternoUser">
                                            <td>Apellido Paterno:</td>
                                            <td><input type="text" maxlength="60" id="APaternoUsuario" name="apPaterno" class="required" size="25" style="text-transform:uppercase" /></td>
                                        </tr>
                                        <tr style="display:none" id="MaternoUser">
                                            <td>Apellido Materno:</td>
                                            <td><input type="text" maxlength="60" id="AMaternoUsuario" name="apMaterno" class="required" size="25" style="text-transform:uppercase" /></td>
                                        </tr>
                                        <tr style="display:none" id="CURPUser">
                                            <td>CURP*:</td>
                                            <td><input type="text" minlength="9" id="CURPUsuario" name="curp" class="required" size="25" style="text-transform:uppercase" /></td>
                                        </tr>
                                        <tr style="display:none" id="RazonUser">
                                            <td>Razón Social:</td>
                                            <td><input type="text" maxlength="180" id="RazonUsuario"  name="razonsocial" class="required" size="25" style="text-transform:uppercase" /></td>

                                        </tr>
                                        <tr>
                                            <td>RFC*:</td>
                                            <td><input type="text" class="required" minlength="9" onblur="validarRFC()" id="UsuarioRFC" name="ClienteRFC" size="25" style="text-transform:uppercase"/>
                                                <label id="ErrorRFCUsuario"></label>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Tel&eacute;fono*:</td>
                                            <td><input type="text" id="UsuarioTelefono" class="required" name="telefono" maxlength="25" size="25" onkeyup="OnlyNumber(this.value,this)" /></td>
                                        </tr>
                                        <tr>
                                            <td>Calle*:</td>
                                            <td><input type="text" id="UsuarioCalle" class="required" name="calle" maxlength="60" size="25" style="text-transform:uppercase" /></td>
                                        </tr>
                                        <tr>
                                            <td>Num. Exterior*:</td>
                                            <td><input type="text" id="UsuarioExterior" class="required" name="numexterior" maxlength="5" size="25" onkeyup="OnlyNumber(this.value,this)" /></td>
                                        </tr>
                                        <tr>
                                            <td>Num. Interior:</td>
                                            <td><input type="text" id="UsuarioInterior" name="numinterior" style="text-transform:uppercase" maxlength="5" size="25" /></td>
                                        </tr>
                                        <tr>
                                            <td>Código Postal*:</td>
                                            <td><input type="text" id="codigoPostal" class="required" name="codigoPostal" size="25" maxlength="5" onblur="BuscarCPRegistro()" onkeyup="OnlyNumber(this.value,this)"  />
                                                <label id="ErrorCodigoPostalUsuario"></label>
                                            </td>                                           
                                        </tr>
                                        <tr>
                                            <td>Estado:</td>
                                            <td><input type="text" id="estado" name="estado" readonly="readonly" size="25" ></td> 
                                        </tr>
                                        <tr>
                                            <td>Delegaci&oacute;n/Municipio:</td>
                                            <td>
                                                <select id="municipio" name="municipio" >

                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Colonia/Localidad</td>
                                            <td>
                                                <select id="localidad" name="localidad" >

                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>E-mail*:</td>
                                            <td><input type="text" id="emailUsuario" class="required email" name="email" value="" size="25" /></td>
                                        </tr>
                                        <tr>
                                            <td>Referencia*:</td>
                                            <td><input type="text" id="referenciaUsuario" class="required" name="referencia" maxlength="25" size="25" style="text-transform:uppercase" /></td>
                                        </tr>
                                        <tr>
                                            <td>Contrase&ntilde;a*:</td>
                                            <td><input type="password" id="passwordUsuario" name="passwordUsuario" size="25"  maxlength="45" /></td>
                                        </tr>
                                        <tr>
                                            <td>Repita Contrase&ntilde;a*:</td>
                                            <td><input type="password" id="rePasswordUsuario" name="rePasswordUsuario" size="25"  maxlength="45" /></td>
                                        </tr>
                                        <tr align="center">
                                            <td colspan="2"><input type="reset" width="50" value="Cancelar" name="cancelar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/> <input  id="siguienteRegistro"type="submit" width="50" value="Siguiente" name="siguiente" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                        <div id="tabs-2">
                            <label> Selecciona los archivos de FIEL y CSD a subir </label>
                            <br><br>
                            <table width="60%" >
                                <form method="post" enctype="multipart/form-data" id="subirFIEL">
                                    <tr>
                                        <td width="30%">
                                            <input type="file" id="archivoREQ" onclick="LimpiarError()" />
                                            <label id="ErrorArchivoREQ"></label>     
                                        </td>

                                        <td>
                                            <input type="button" value="&nbsp; &nbsp; Subir FIEL &nbsp; &nbsp;" name="subirFIEL" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                                        </td>
                                    </tr>     
                                </form>
                            </table>
                            <br>
                            <br>
                            <br>
                            <br>
                            <table width="60%">
                                <form method="post" enctype="multipart/form-data" id="subirCSD">
                                    <tr>
                                        <td width="30%">
                                            <input type="file" id="archivoCER" onclick="LimpiarError()">
                                            <label id="ErrorArchivoCER"></label>
                                        </td>

                                        <td>
                                            <input type="button" value=" &nbsp; &nbsp; Subir CSD &nbsp; &nbsp;" name="subirCSD" class="ui-button ui-widget ui-state-default ui-corner-all" onclick="SubirCSD()" role="button" aria-disabled="false"/>
                                        </td>

                                    </tr>
                                </form>
                            </table>
                            <script type="text/javascript">
                                                                                         
                                $(function(){
                                    $("#archivoREQ").MultiFile({
                                        accept:'req', max:1, STRING: {
                                            remove:'X',
                                            selected:'Selecionado: $file',
                                            denied:'REQ/Archivo de extención $ext invalido!',
                                            duplicate:'Archivo ya selecionado:\n$file!'

                                        }
                                    });
                                    
                                    $("#archivoCER").MultiFile({
                                        accept:'cer', max:1, STRING: {
                                            remove:'X',
                                            selected:'Selecionado: $file',
                                            denied:'CER/Archivo de extención $ext invalido!',
                                            duplicate:'Archivo ya selecionado:\n$file!'

                                        }
                                    });
                                });
                                
                            </script>
                        </div>
                        <div id="tabs-3" style="display:none">
                            <font color="red">Favor de Verificar sus datos antes de completar el registro</font>
                            <form>
                                <div id="formularioVerificacion" style="display:none"></div>
                                <br>
                                <input type="submit" value="Registrar Usuario" name="registrarUsuario" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                                <input type="button" value="Regresar" name="regresar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
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
                <a href="http://twitter.com/" ><img src="images/twitter.png" alt="http://twitter.com" width="25" height="25"></a>
                <a href="http://www.facebook.com/" ><img src="images/Facebook.png" alt="http://www.facebook.com" width="25" height="25"></a>
                <br>
                Este sitio se visualiza mejor con Google Chrome
            </div>
        </div>
    </center>
</body>
</html>