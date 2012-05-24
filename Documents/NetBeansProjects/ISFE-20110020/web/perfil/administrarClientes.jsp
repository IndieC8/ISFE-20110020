<%-- 
    Document   : administrarClientes
    Created on : 1/02/2012, 11:35:27 AM
    Author     : ISFE
--%>

<%@page import="org.w3c.dom.Document"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" import="dao.*" %>

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
        <title>ISFE - Administrar Clientes</title>
        <link rel="stylesheet" type="text/css" href="../estilo/style.css" />
        <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.17.custom.min.js"></script>
        <script src="../js/ui/jquery.ui.dialog.js"></script>
        <script src="../js/jquery.maskedinput.js"></script> <!--Mascara para el RFC -->
        <script src="../js/jquery.validate.js"></script> <!--Validar Formulario -->
        <script src="../js/jquery.cliente.js"></script> <!--Funciones del  Formulario -->
        <script src="../js/jquery.perfil.js"></script>
        <script src="../js/jquery.menu.js"></script>

        <script type="text/javascript">
            $(document).ready(function(){
                
                $("#actualizarCliente").validate();
                                    
                $("#formulario_registroCliente").validate({
                    rules:{
                        RFCCliente:{
                            required: true
                        }
                    },
                    messages:{
                        RFCCliente:{
                            required: "Ingresa el RFC de tu Cliente"
                        }
                    }
                });
            });
            
            $(function() {
                $("#ConfirmarDatosCliente").dialog({
                    autoOpen: false,
                    height: 380,
                    width: 750,
                    modal: true,
                    buttons: {
                        "Aceptar": function() {
                            if(Actualizacion == "actualizar"){
                                $.ajax({
                                    type:"POST", url:"../Cliente",
                                    data:"Cliente=actualizacion&idCliente="+idClienteActualizar+"&idUsuario=<%=id%>&mail="+mail+"&calle="+calle+"&interior="+interior+"&exterior="+exterior+"&idLocalidad="+colonia+"&referencia="+referencia,
                                    success: function(data){
                                        $("#ConfirmacionModificarCliente").text("Tu cliente se ha actualizado!"); regresarModificacion(); 
                                        
                                    }
                                });
                            }else{
                                $.ajax({
                                    type:"POST", url:"../Cliente",
                                    data:"Cliente=guardar&tipoPersona="+Tipo+"&Nombre="+nombre+"&Paterno="+paterno+"&Materno="+materno+"&Razon="+razon+"&RFCCliente="+
                                        rfcCliente+"&Mail="+mail+"&Calle="+calle+"&Interior="+interior+"&Exterior="+exterior+"&Colonia="+colonia+"&Municipio="+
                                        municipio+"&Referencia="+referencia+"&idUsuario=<%=id%>",
                                    success: function(data){
                                        $("#ConfirmacionGuardarCliente").text(data); Limpiar(); }
                                });
                            }
                            $( this ).dialog( "close" );     
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
            
            /*Validador del Formulario de Registro y Actualización*/
            $.validator.setDefaults({
                submitHandler: function() {
                    
                    if(Actualizacion == "actualizar"){
                        if($("#ErrorCodigoPostalModificarCliente").text() != ""){
                            $("#codigoClienteModificar").focus();
                        }else{
                            if(Tipo == "Moral"){ razon = $("#razonClienteModificar").val().toUpperCase();}
                            else{ razon = $("#nombreClienteModificar").val().toUpperCase()+" "+$("#paternoClienteModificar").val().toUpperCase()+" "+$("#maternoClienteModificar").val().toUpperCase(); }
                            
                            rfcCliente = $("#RFCClienteModificar").val().toUpperCase();
                            var aux = rfcCliente.split('-');
                            rfcCliente = aux[0]+aux[1]+aux[2];
                            mail = $("#mailClienteModificar").val();
                            calle = $("#CalleClienteModificar").val().toUpperCase();
                            exterior = $("#exteriorClienteModificar").val();
                            interior = $("#interiorClienteModificar").val();
                            colonia = $("#localidadClienteModificar").val();
                            referencia = $("#referenciaClienteModificar").val().toUpperCase();
                            
                            $("#confirmarColoniaCliente").text( $("#localidadClienteModificar option:selected").html() );
                            $("#confirmarMunicipioCliente").text( $("#delegacionClienteModificar option:selected").html() );
                            $("#confirmarEstadoCliente").text( $("#EstadoClienteModificar").val() ); 
                            $("#confirmarRazonCliente").text(razon);
                            idClienteActualizar = $("#idClienteModificar").val();
                        }
                    }else{
                    
                        if($("#indicacionesCliente").text() != ""){
                            $("#RFCCliente").focus();
                            return false;
                        }else if($("#codigoPostalCliente").text() != ""){
                            $("#codigoPostalCliente").focus();
                            return false;
                        }
                    
                        if(Tipo == "Moral"){
                            razon = $("#razonCliente").val().toUpperCase();
                            $("#confirmarRazonCliente").text(razon);
                        }else{
                            nombre = $("#nombreCliente").val().toUpperCase();   
                            paterno = $("#paternoCliente").val().toUpperCase();
                            materno = $("#maternoCliente").val().toUpperCase();
                            $("#confirmarRazonCliente").text(nombre+" "+paterno+" "+materno);
                        }

                        rfcCliente = $("#RFCCliente").val().toUpperCase();
                        var aux = rfcCliente.split('-');
                        rfcCliente = aux[0]+aux[1]+aux[2];
                        mail = $("#mailCliente").val();
                        calle = $("#calleCliente").val().toUpperCase();
                        exterior = $("#exteriorCliente").val();
                        interior = $("#interiorCliente").val();
                        colonia = $("#localidadCliente").val();
                        referencia = $("#referenciaCliente").val().toUpperCase();
                        municipio = $("#municipioCliente").val();
                        
                        $("#confirmarColoniaCliente").text( $("#localidadCliente option:selected").html() );
                        $("#confirmarMunicipioCliente").text( $("#municipioCliente option:selected").html() );
                        $("#confirmarEstadoCliente").text( $("#estadoCliente").val() ); 
                    
                    }
                    var auxCalle = "";
                    var auxInterior = "";
                    var auxExterior = "";
                    if(interior != ""){
                        auxInterior = "No. Interior: "+interior; 
                    }
                    
                    if(exterior != ""){
                        auxExterior = "No. Exterior: "+exterior;
                    }
                    
                    if(calle != ""){
                        auxCalle = "Calle: "+calle;
                    }
                    
                    $("#confirmarRFCCliente").text(rfcCliente);
                    $("#confirmarMailCliente").text(mail);
                    $("#confirmarDireccionCliente").text(auxCalle+" "+auxExterior+" "+auxInterior);
                    $("#confirmarReferenciaCliente").text(referencia);
                    $("#ConfirmarDatosCliente").dialog("open");
                    
                } 
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
        <!--Dialogo de Confirmar Datos-->
        <div id="ConfirmarDatosCliente" title="ISFE- Administración de Clientes">
            <p class="validateTips">
                <label>
                    <img src="../images/important.gif" />
                    &nbsp;  
                    Verifica que los datos de tu cliente sean correctos:
                </label>
            </p>
            <form>
                <center><table class="table1">
                        <tbody>
                            <tr>
                                <th scope="row">Razón Social</th>
                                <td id="confirmarRazonCliente"><span class="check"></span></td>

                                <th scope="row">RFC</th>
                                <td id="confirmarRFCCliente"><span class="check"></span></td>
                            </tr>
                            <tr>
                                <th scope="row">Dirección</th>
                                <td id="confirmarDireccionCliente"><span class="check"></span></td>

                                <th scope="row">Referencia</th>
                                <td id="confirmarReferenciaCliente"><span class="check"></span></td>

                            </tr>
                            <tr>
                                <th scope="row">Colonia</th>
                                <td id="confirmarColoniaCliente"><span class="check"></span></td>

                                <th scope="row">Municipio</th>
                                <td id="confirmarMunicipioCliente"><span class="check"></span></td>

                            </tr>
                            <tr>
                                <th scope="row">Estado</th>
                                <td id="confirmarEstadoCliente"><span class="check"></span></td>

                                <th scope="row">Mail</th>
                                <td id="confirmarMailCliente"><span class="check"></span></td>
                            </tr>
                        </tbody>
                    </table></center>
            </form>
        </div>
        <!--Termina el dialogo de Confirmar Datos-->

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
                                <li><a href="../factura/EstadoFactura.jsp">Estado de la Factura</a></li>
                            </ul>
                        </li>                       
                        <li><a href="../cerrar.jsp"><img src="../images/icons/ingreso_ico.png"/> Cerrar Sesión &nbsp; &nbsp; <% out.println(contribuyente);%></a></li>
                    </ul>
                </div>
                <!-- Termina Menu -->
                <br><br>
                <div class="titulo_pagina">Administrar Clientes</div>
                <center>
                    <!--Comienza el formulario-->	
                    <div id="tabs">
                        <ul>
                            <li><a onclick="BorrarErrorBaja()" href="#tabs-1">Agregar Cliente</a></li>
                            <li><a onclick="BorrarErrorBaja()" href="#tabs-2">Modificar Cliente</a></li>
                            <li><a onclick="BorrarErrorBaja()" href="#tabs-3">Dar de Baja a Cliente</a></li>
                        </ul>
                        <div id="tabs-1">
                            <font color="red">Los campos marcados con (*) son obligatorios</font><br><br>
                            <form id="formulario_registroCliente" name="formulario_registroCliente" method="post">
                                <table border="0">
                                    <tbody>
                                        <tr>
                                            <td rowspan="19"><img src="../images/formularios/add_user.png"/></td>
                                            <td>Tipo de Contribuyente*:</td> 
                                            <td><input type="radio" class="required" id="clienteMoral" name="contribuyenteCliente" value="Moral" onclick="ContribuyenteCliente(this.value)">Persona Moral<input type="radio" class="required" name="contribuyenteCliente" id="clienteFisica" value="Fisica" onclick="ContribuyenteCliente(this.value)">Persona Fisica</td>
                                        </tr>
                                        <tr style="display:none" id="NombreClient">
                                            <td>Nombre*:</td>
                                            <td><input type="text" class="required" minlength="2" maxlength="60" id="nombreCliente" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr style="display:none" id="PaternoClient">
                                            <td>Apellido Paterno*:</td>
                                            <td><input type="text" class="required" minlength="2" maxlength="60" id="paternoCliente" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr style="display:none" id="MaternoClient">
                                            <td>Apellido Materno*:</td>
                                            <td><input type="text" class="required" minlength="2" maxlength="60" id="maternoCliente" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr style="display:none" id="razonClient">
                                            <td>Razon Social:</td>
                                            <td><input type="text" class="required"  minlength="5" maxlength="200" id="razonCliente" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr>
                                            <td>R.F.C.*:</td>
                                            <td><input type="text" class="required" name="RFCCliente" id="RFCCliente" style="text-transform:uppercase" onblur="validarRFC()">
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
                                            <td><input type="text" id="interiorCliente" maxlength="5" ></td>
                                        </tr>
                                        <tr>
                                            <td>Código Postal*:</td>
                                            <td><input type="text" id="codigoPostalCliente" class="required" name="codigoPostal" maxlength="5" onblur="BuscarCodigo()" onkeyup="OnlyNumber(this.value,this)"  />
                                                <label id="ErrorCodigoPostalCliente"></label>
                                            </td>                                           
                                        </tr>
                                        <tr>
                                            <td>Estado*:</td>
                                            <td><input type="text" id="estadoCliente" class="required" readonly="readonly" ></td> 
                                        </tr>
                                        <tr>
                                            <td>Delegaci&oacute;n/Municipio*:</td>
                                            <td>
                                                <select id="municipioCliente" class="required" name="municipio" >

                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Colonia/Localidad*:</td>
                                            <td>
                                                <select id="localidadCliente" class="required" name="localidad" >

                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Referencia:</td>
                                            <td><input type="text" maxlength="25" id="referenciaCliente" style="text-transform:uppercase"></td>
                                        </tr>
                                        <tr align="center">
                                            <td><input type="reset" width="50" value="Cancelar" name="cancelar" onclick="Limpiar()" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                            <td><input type="submit" width="50" value="Guardar" name="siguiente" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>
                            <br/>
                            <label><h3 id="ConfirmacionGuardarCliente"></h3></label>
                        </div>
                        <div id="tabs-2">
                            <div id="formulario_busquedaModificacionCliente">
                                <form id="modificarCliente" action="" method="post">
                                    <font color="red">Ingrese el R.F.C. del cliente a Modificar</font><br>
                                    <table border="0">
                                        <tr>
                                            <td>R.F.C: </td>
                                            <td>
                                                <input type="text" onkeyup="BorrarErrorBaja()" style="text-transform:uppercase" id="rfcClienteModificar" width="20"/>
                                                <label id="ErrorRFCMoficiarCliente"></label>
                                            </td>
                                            <td><input type="button" value="Buscar" onclick="ModificarCliente()" name="Buscar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                        </tr>
                                    </table>
                                </form>
                                <br/><label><h3 id="ConfirmacionModificarCliente"></h3></label>
                                <div id="ResultadoModificarCliente"></div>
                                <br>
                            </div>
                            <form id="actualizarCliente">
                                <div id="formulario_actualizacionModficarCliente" style="display:none"></div>
                            </form>
                        </div>
                        <div id="tabs-3">
                            <form id="bajaCliente" action="" method="post">
                                <font color="red">Ingrese el R.F.C. del cliente a dar de baja</font><br>
                                <table border="0">
                                    <tr>
                                        <td>R.F.C: </td>
                                        <td>
                                            <input type="text" style=" text-transform:uppercase" id="rfcClienteEliminar" onkeyup="BorrarErrorBaja()" width="20"/>
                                            <label id="ErrorEliminarCliente"></label>
                                        </td>
                                        <td><input type="button" id="EliminarCliente"  value="Buscar" name="Buscar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                    </tr>
                                </table>
                            </form>
                            <br/>
                            <label><h3 id="ConfirmacionEliminarCliente"></h3></label>
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