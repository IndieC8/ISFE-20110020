<%-- 
    Document   : Generar Factura Electronica
    Created on : 23/01/2012, 03:09:10 PM
    Author     : kawatoto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>ISFE - Generar Factura Electr&oacute;nica</title>
        <link rel="stylesheet" type="text/css" href="../estilo/style.css" />
        <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.17.custom.min.js"></script>
        <script src="../js/jquery.validate.js"></script> <!--Validar Formulario -->
        <script src="../js/jquery.maskedinput.js"></script> <!--Mascara para formulario -->

        <script type="text/javascript">
            $(document).ready(function(){
                $("#descripcionFactura").validate();
           
            }); 
             
            $(function(){
                // Tabs
                $('#tabs').tabs();
	
				
            });
        </script>

        <script>
            $(function() {
                $( "input:submit, a, button", ".demo" ).button();
                $( "a", ".demo" ).click(function() { return false; });
            });
                
        </script>
        <script>
                        
            function addComas(nStr,elemnt){
                nStr = Math.round(nStr*100)/100; 
                nStr += '';
                x = nStr.split('.');
                x1 = x[0];
                x2 = x.length > 1 ? '.' + x[1] : '';
                var rgx = /(\d+)(\d{3})/;
                while (rgx.test(x1)) {
                    x1 = x1.replace(rgx, '$1' + ',' + '$2');
                }
                nStr =  x1 + x2;
                
                elemnt.value = nStr;
     
            }
            
            function ValidarSuma(){
                
                var cantidad = $("#cantidadFactura").val();
                var valor = $("#valorUnitario").val();
                if(cantidad == "0"){
                    $("#errorCantidadFactura").text("Indica la cantidad del producto");
                }if(valor != ""){
                    var Total = valor * cantidad;
                    addComas(Total,document.getElementById("importeTotal"));
                    addComas(valor,document.getElementById("valorUnitario"));
                    
                }
            }
            
            function OnlyNumber(value,elemnt){
                if( isNaN(value) ){
                    elemnt.value = "";
                }
            }
            
            function BorrarError(id){
                if(id == "unidadProducto"){
                    $("#errorUnidadProducto").text("");
                }else{ 
                    $("#errorCantidadFactura").text("");
                    ValidarSuma();
                }
            }
            
            function agregarFila(obj){
                $("#cant_campos").val(parseInt($("#cant_campos").val()) + 1);
                
                var cantidad = $("#cantidadFactura").val();
                var nombre = $("#nombreProducto").val().toUpperCase();
                var descripcion = $("#descripcionProducto").val().toUpperCase();
                var unidad = $("#unidadProducto").val();
                var unitario = $("#valorUnitario").val();
                var importe = $("#importeTotal").val(); 

                var strHtml1 = "<td class=\"TablaTitulo\">" + cantidad + '<input type="text" id="tbDetalleCantidad_' + oId + '" name="hdnNombre_' + oId + '" value="' + nombre + '"/></td>';
                var strHtml2 = "<td class=\"TablaTitulo\">" + nombre + '<input type="text" id="tbDetalleNombre_' + oId + '" name="hdnEdad_' + oId + '" value="' + edad + '"/></td>' ;
                var strHtml3 = "<td class=\"TablaTitulo\">" + descripcion + '<input type="text" id="tbDetalleDescripcion_' + oId + '" name="hdnDireccion_' + oId + '" value="' + direccion + '"/></td>' ;
                var strHtml4 = "<td class=\"TablaTitulo\">" + unidad + '<input type="text" id="tbDetalleUnidad_' + oId + '" name="hdnSexo_' + oId + '" value="' + sexo + '"/></td>' ;
                var strHtml5 = "<td class=\"TablaTitulo\">" + unitario + '<input type="text" id="tbDetalleUnitario_' + oId + '" name="hdnEstCivil_' + oId + '" value="' + estCivil + '"/></td>' ;
                var strHtml6 = "<td class=\"TablaTitulo\">" + importe + '<input type="text" id="tbDetalleImporte_' + oId + '" name="hdnEstCivil_' + oId + '" value="' + estCivil + '"/></td>' ;
                var strHtml7 = '<td class=\"TablaTitulo\"><img src=\"../images/formularios/delete.png\" width=\"16\" height=\"16\" alt=\"Eliminar\" "/>';
                strHtml7 += '<input type="text" id="hdnIdCampos_' + oId +'" name="hdnIdCampos[]" value="' + oId + '" /></td>';
                var strHtmlTr = "<tr id='rowDetalle_" + oId + "'></tr>";
                var strHtmlFinal = strHtml1 + strHtml2 + strHtml3 + strHtml4 + strHtml5 + strHtml6+ strHtml7;
                //tambien se puede agregar todo el HTML de una sola vez.
                //var strHtmlTr = "<tr id='rowDetalle_" + oId + "'>" + strHtml1 + strHtml2 + strHtml3 + strHtml4 + strHtml5 + strHtml6 +"</tr>";
                $("#tbDetalleProducto").append(strHtmlTr);
                //si se agrega el HTML de una sola vez se debe comentar la linea siguiente.
                $("#rowDetalle_" + oId).html(strHtmlFinal);
                
                alert("Entra");
                return false;
            }
            
            
            
            $.validator.setDefaults({
                submitHandler: function(){
                    
                    if( $("#unidadProducto").val() == "0"){
                        $("#errorUnidadProducto").text("Indica la unidad del producto");
                    }else{
                        
                        //agregarFila(document.getElementById('cant_campos'));
                        
                        $("#mensajeConfirmacion").text("Producto Agregado");
                        $("#confirmacion").show();
                        
                    }
                }
            });
        </script>
    </head>
    <body>
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
                <div class="titulo_pagina">Generar Factura Electr&oacute;nica</div>
                <center>
                    <!--Comienza el formulario-->	
                    <div id="tabs">
                        <ul>
                            <li><a href="#tabs-1">Nueva Factura</a></li>
                            <li><a href="#tabs-2">Confirmar Datos de la Factura</a></li>
                        </ul>
                        <div id="tabs-1">
                            <form id="descripcionFactura">
                                <table>
                                    <tr>
                                        <td>
                                            Cantidad:<br>
                                            <input type="number" min="1" value="0" onchange="BorrarError(this.id);"  id="cantidadFactura" />
                                            <label id="errorCantidadFactura"></label>
                                        </td>
                                        <td>
                                            Nombre Producto:<br>
                                            <input type="text" class="required" minlength="5" id="nombreProducto" style="text-transform:uppercase" maxlength="100" />
                                        </td>
                                        <td>
                                            N° Identificacion del Producto<br>
                                            <input type="text" id="numIdentificacion" style="text-transform:uppercase" maxlength="30" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            Descripcion:<br>
                                            <textarea id="descripcionProducto" name="descripcion" class="required" minlength="7" rows="5" cols="50" style="text-transform:uppercase" maxlength="255">
                                            </textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            Unidad:<br>
                                            <select name="unidad" id="unidadProducto" onchange="BorrarError(this.id)" class="required">
                                                <option value="0">Seleccione</option>
                                                <option value="Lt">Lt</option>
                                                <option value="Kg">Kg</option>
                                                <option value="Pieza">Piezas</option>
                                                <option value="Oz">Oz</option>
                                                <option value="Mt">Mt</option>    
                                            </select>
                                            <label id="errorUnidadProducto"></label>
                                        </td>
                                        <td>
                                            Valor Unitario:<br>
                                            $<input type="text" id="valorUnitario" class="required" name="valorUnitario" maxlength="20" onblur="ValidarSuma()" onkeypress="OnlyNumber(this.value,this)"/>
                                        </td>
                                        <td>
                                            Importe:<br>
                                            $<input type="text" id="importeTotal" name="importe" class="required" maxlength="20" readonly="readonly"/>
                                        </td>
                                    </tr>
                                </table>
                                <br />
                                <label id="mensajeConfirmacion"></label>
                                <br/>
                                <input type="submit" width="50" value="Agregar Producto" name="agregar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                                &nbsp; &nbsp; &nbsp; <input type="reset" width="50" value="Cancelar" name="cancelar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>

                            </form>
                        </div>
                        <div id="tabs-2">
                            <br/>
                            <br/>
                            <input type="hidden" id="num_campos" name="num_campos" value="0" />
                            <input type="hidden" id="cant_campos" name="cant_campos" value="0" />
                            <table id="TablaProductosFacturas">
                                <thead>
                                    <tr>
                                        <th class="TablaTitulo">Cantidad</th>
                                        <th class="TablaTitulo">Producto</th>
                                        <th class="TablaTitulo">Descripción</th>
                                        <th class="TablaTitulo">Unidad</th>
                                        <th class="TablaTitulo">Valor Unitario</th>
                                        <th class="TablaTitulo">Importe</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody id="tbDetalleProducto">
                                </tbody>
                            </table>
                            <br/>
                            <div id="confirmacion" style="display: none" align="right"><input type="submit" width="50" value="Confirmar Factura" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/> </div>
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