<%-- 
    Document   : administrarClientes
    Created on : 1/02/2012, 11:35:27 AM
    Author     : kawatoto
--%>

<%@page import="org.w3c.dom.Document"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" import="dao.*" %>


<!DOCTYPE html>
<html>
    <head>
        <title>ISFE - Administrar Clientes</title>
        <link rel="stylesheet" type="text/css" href="../estilo/style.css" />
        <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.17.custom.min.js"></script>
        <script src="../js/jquery.fileUploader.js" type="text/javascript"></script>
        <script src="../js/ui/jquery.ui.core.js"></script>
        <script src="../js/ui/jquery.ui.widget.js"></script>
        <script src="../js/ui/jquery.ui.mouse.js"></script>
        <script src="../js/ui/jquery.ui.button.js"></script>
        <script src="../js/ui/jquery.ui.draggable.js"></script>
        <script src="../js/ui/jquery.ui.position.js"></script>
        <script src="../js/ui/jquery.ui.resizable.js"></script>
        <script src="../js/ui/jquery.ui.dialog.js"></script>
        <script src="../js/ui/jquery.effects.core.js"></script>
        <script src="../js/jquery.maskedinput.js"></script> <!--Mascara para el RFC -->
        <script src="../js/jquery.validate.js"></script> <!--Validar Formulario -->
        <script src="../js/jquery.cliente.js"></script> <!--Funciones del  Formulario -->
        <script type="text/javascript">
            
            $(document).ready(function(){
                                    
                $("#formulario_registroCliente").validate({
                    rules:{
                        RFCCliente:{
                            required: true
                        }
                    },
                    messages:{
                        RFCCliente:{
                            required: "Ingresa el RFC del Cliente"
                        }
                    }
                });
            });
                
            jQuery(function(){
                $("#RFCLogin").mask("aaa*-999999-aaa*");
                $("#RFCCliente").mask("aaa*-999999-aaa*");
                $("#rfcClienteModificar").mask("aaa*-999999-aaa*");
                $("#rfcClienteEliminar").mask("aaa*-999999-aaa*");
            });
                
            $(function(){

                // Accordion
                $("#accordion").accordion({ header: "h3" });
	
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

                // Datepicker
                $('#datepicker').datepicker({
                    inline: true
                });
				
                // Slider
                $('#slider').slider({
                    range: true,
                    values: [17, 67]
                });
				
                // Progressbar
                $("#progressbar").progressbar({
                    value: 20 
                });
				
                //hover states on the static widgets
                $('#dialog_link, ul#icons li').hover(
                function() { $(this).addClass('ui-state-hover'); }, 
                function() { $(this).removeClass('ui-state-hover'); }
            );
				
            });

            $(function() {
                $( "input:submit, a, button", ".demo" ).button();
                $( "a", ".demo" ).click(function() { return false; });
            });
               
            /*Evalua la opción si es Persona Fisica o moral*/
            var Tipo="";
            function Contribuyente(value){
                $("#razonClient").hide();
                $("#NombreClient").hide();
                $("#PaternoClient").hide();
                $("#MaternoClient").hide();
                $("#nombreCliente").val("");
                $("#paternoCliente").val("");
                $("#maternoCliente").val("");
                $("#razonCliente").val("");
                 
                if(value == "Moral"){
                    $("#razonClient").show();
                    Tipo = "Moral";
                }else{
                    $("#NombreClient").show();
                    $("#PaternoClient").show();
                    $("#MaternoClient").show();
                    Tipo = "Fisica";
                }
                
            }
            
            $.validator.setDefaults({
                submitHandler: function() { 
                    if($("#indicacionesCliente").text() != ""){
                        $("#RFCCliente").focus();
                        return false;
                    }else if($("#codigoPostalCliente").text() != ""){
                        $("#codigoPostalCliente").focus();
                        return false;
                    }
                    
                    var nombre = $("#nombreCliente").val().toUpperCase();   
                    var paterno = $("#paternoCliente").val().toUpperCase();
                    var materno = $("#maternoCliente").val().toUpperCase();
                    var razon = $("#razonCliente").val().toUpperCase();
                    var rfc = $("#RFCCliente").val().toUpperCase();
                    var aux = rfc.split('-');
                    rfc = aux[0]+aux[1]+aux[2];
                    var mail = $("#mailCliente").val();
                    var calle = $("#calleCliente").val().toUpperCase();
                    var exterior = $("#exteriorCliente").val();
                    var interior = $("#interiorCliente").val();
                    var colonia = $("#localidadCliente").val();
                    var municipio = $("#municipioCliente").val();
                    var referencia = $("#referenciaCliente").val().toUpperCase();
                
                    $.ajax({
                        type:"POST",
                        url:"../Cliente",
                        data:"Cliente=guardar&tipoPersona="+Tipo+"&Nombre="+nombre+"&Paterno="+paterno+"&Materno="+materno+"&Razon="+razon+"&RFCCliente="+
                            rfc+"&Mail="+mail+"&Calle="+calle+"&Interior="+interior+"&Exterior="+exterior+"&Colonia="+colonia+"&Municipio="+
                            municipio+"&Referencia="+referencia,
                        success: function(data){
                            alert(data);
                            Limpiar();
                        }
                    }); 
                    
                } 
            });
        
        </script>


        <script>
            /*
             *Variable de idCliente para Eliminar
             */
             var idClienteEliminar = "";
             
            /*
             *Activar el dialogo de Eliminar Cliente
             */
            $(function() {
                // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
                $( "#dialog:ui-dialog" ).dialog( "destroy" );
		
                var rfc = $( "#rfc" ),
                password = $( "#password" ),
                allFields = $( [] ).add( rfc ).add( password ),
                tips = $( ".validateTips" );

		
		
                $( "#dialog-form" ).dialog({
                    autoOpen: false,
                    height: 300,
                    width: 350,
                    modal: true,
                    buttons: {
                        "Eliminar": function() {
                            alert("Eliminando");
                                        
                        },
                        Cancel: function() {
                            $( this ).dialog( "close" );
                        }
                    },
                    close: function() {
                        allFields.val( "" ).removeClass( "ui-state-error" );
                    }
                });

                /*Funcion para activar el dialogo*/
                $( "#EliminarCliente" ).click(function() {
                    $("#ErrorEliminarCliente").text("");
                    var rfc = $("#rfcClienteEliminar").val().toUpperCase();
                    if(rfc == ""){
                        $("#ErrorEliminarCliente").text("Debes ingresar el R.F.C.");
                    }else{
                        $("#rfcErrorMensaje").text(rfc);
                        var aux = rfc.split("-");
                        rfc = aux[0]+aux[1]+aux[2];
                        
                        $.ajax({
                            type: "POST",
                            url: "../Cliente",
                            data: "Cliente=baja&rfc="+rfc,
                            success: function(data){
                                var aux = data.split("/");
                                idClienteEliminar = aux[0];
                                $("#nombreErrorMensaje").text(aux[1]);
                            }
                        });
                        $( "#dialog-form" ).dialog( "open" );
                    }
                });
                        
                
            });
        
        </script>
    </head>
    <body>
        <!--Aqui va el dialogo de Eliminar Cliente-->
        <div id="dialog-form" title="ISFE- Administrar Cliente">
            <p class="validateTips">
                <img src="../images/important.gif" />
                &nbsp;
                ¿Seguro que deseas eliminar al cliente?
            </p>
            <form>
                <fieldset>
                    R.F.C. &nbsp; &nbsp;
                    <label class="texto" id="rfcErrorMensaje"></label>
                    <br>
                    <br>

                    Nombre Cliente &nbsp; &nbsp;
                    <label class="texto" id="nombreErrorMensaje"></label>


                </fieldset>
            </form>
        </div>
        <!--Aqui termina el dialogo de Eliminar Cliente-->
    <center>
        <div class="principal">
            <div class="header">
                <div class="logo"><a href="../index.jsp" ><img src="../images/logo1.png" alt="ISFE" height="164"/></a></div>
            </div>
            <div class="contenido_principal">
                <!-- Comienza Menu -->
                <div class="menu">
                    <ul>
                        <li><a href="" ><img src="../images/icons/home.png" alt="" height="20"/> Home</a></li>
                        <li><a href="../contact.jsp"><img src="../images/icons/contacto_ico.png" alt=""/> Contacto</a></li>
                        <li><a href=""><img src="../images/icons/valida_ico.png" alt=""/>¿C&oacute;mo usar ISFE?</a></li>
                        <li><a href="../perfil.jsp" id="current"><img src="../images/icons/perfil_ico.png" alt=""/> Perfil</a>
                            <ul>
                                <li><a href="../perfil/consultarPerfil.jsp">Consultar Perfil</a></li>
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
                <div class="titulo_pagina">Administrar Clientes</div>
                <center>
                    <!--Comienza el formulario-->	
                    <div id="tabs">
                        <ul>
                            <li><a href="#tabs-1">Agregar Cliente</a></li>
                            <li><a href="#tabs-2">Modificar Cliente</a></li>
                            <li><a href="#tabs-3">Dar de Baja a Cliente</a></li>
                        </ul>
                        <div id="tabs-1">
                            <font color="red">Los campos marcados con (*) son obligatorios</font><br><br>
                            <form id="formulario_registroCliente" name="formulario_registroCliente" method="post">
                                <table border="0">
                                    <tbody>
                                        <tr>
                                            <td rowspan="19"><img src="../images/formularios/add_user.png"/></td>
                                            <td>Tipo de Contribuyente*:</td> 
                                            <td><input type="radio" class="required" id="clienteMoral" name="contribuyenteCliente" value="Moral" onclick="Contribuyente(this.value)">Persona Moral<input type="radio" class="required" name="contribuyenteCliente" id="clienteFisica" value="Fisica" onclick="Contribuyente(this.value)">Persona Fisica</td>
                                        </tr>
                                        <tr style="display:none" id="NombreClient">
                                            <td>Nombre*:</td>
                                            <td><input type="text" class="required" minlength="5" maxlength="60" id="nombreCliente" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr style="display:none" id="PaternoClient">
                                            <td>Apellido Paterno*:</td>
                                            <td><input type="text" class="required" minlength="5" maxlength="60" id="paternoCliente" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr style="display:none" id="MaternoClient">
                                            <td>Apellido Materno*:</td>
                                            <td><input type="text" class="required" minlength="5" maxlength="60" id="maternoCliente" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr style="display:none" id="razonClient">
                                            <td>Razon Social:</td>
                                            <td><input type="text" class="required"  minlength="5" maxlength="200" id="razonCliente" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr>
                                            <td>R.F.C.*:</td>
                                            <td><input type="text" name="RFCCliente" id="RFCCliente" style="text-transform:uppercase" onblur="validarRFC()">
                                                <label id="indicacionesCliente"></label>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>E-mail*:</td>
                                            <td><input type="text" class="required email" maxlength="45" id="mailCliente" ></td>
                                        </tr>
                                        <tr>
                                            <td>Calle:</td>
                                            <td><input type="text"  id="calleCliente" maxlength="45" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr>
                                            <td>N° Exterior:</td>
                                            <td><input type="text" id="exteriorCliente" maxlength="5" onkeypress="OnlyNumber(this.value,this)"></td>
                                        </tr>
                                        <tr>
                                            <td>N° Interior:</td>
                                            <td><input type="text" id="interiorCliente" maxlength="5" onkeypress="OnlyNumber(this.value,this)"></td>
                                        </tr>
                                        <tr>
                                            <td>Código Postal:</td>
                                            <td><input type="text" id="codigoPostalCliente" name="codigoPostal" maxlength="5" onblur="obtenerEstadoCliente()" onkeyup="OnlyNumber(this.value,this)"  />
                                                <label id="ErrorCodigoPostalCliente"></label>
                                            </td>                                           
                                        </tr>
                                        <tr>
                                            <td>Estado:</td>
                                            <td><input type="text" id="estadoCliente" readonly="readonly" ></td> 
                                        </tr>
                                        <tr>
                                            <td>Delegaci&oacute;n/Municipio:</td>
                                            <td>
                                                <select id="municipioCliente" name="municipio" >

                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Colonia/Localidad</td>
                                            <td>
                                                <select id="localidadCliente" name="localidad" >

                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Referencia*:</td>
                                            <td><input type="text" class="required" maxlength="25" id="referenciaCliente" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr align="center">
                                            <td><input type="reset" width="50" value="Cancelar" name="cancelar" onclick="Limpiar()" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                            <td><input type="submit" width="50" value="Guardar" name="siguiente" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                        <div id="tabs-2">
                            <div id="formulario_busquedaModificacionCliente">
                                <form id="modificarCliente" action="" method="post">
                                    <font color="red">Ingrese el R.F.C. del cliente a Modificar</font><br>
                                    <table border="0">
                                        <tr>
                                            <td>R.F.C: </td>
                                            <td>
                                                <input type="text" style="text-transform:uppercase" id="rfcClienteModificar" width="20"/>
                                                <label id="ErrorRFCMoficiarCliente"></label>
                                            </td>
                                            <td><input type="button" value="Buscar" onclick="ModificarCliente()" name="Buscar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                    </table>
                                    <br><br><br>
                                    <div id="ResultadoModificarCliente"></div>
                                    <br><br>
                                </form>
                            </div>
                            <div id="formulario_actualizacionModficarCliente" style="display:none"></div>
                        </div>
                        <div id="tabs-3">
                            <form id="bajaCliente" action="" method="post">
                                <font color="red">Ingrese el R.F.C. del cliente a dar de baja</font><br>
                                <table border="0">
                                    <tr>
                                        <td>R.F.C: </td>
                                        <td>
                                            <input type="text" style=" text-transform:uppercase" id="rfcClienteEliminar" width="20"/>
                                            <label id="ErrorEliminarCliente"></label>
                                        </td>
                                        <td><input type="button" id="EliminarCliente"  value="Buscar" name="Buscar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
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