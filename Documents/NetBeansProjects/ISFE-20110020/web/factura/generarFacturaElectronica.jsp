<%--
    Document   : Generar Factura Electronica
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
        <title>ISFE - Generar Factura Electr&oacute;nica</title>
        <link rel="stylesheet" type="text/css" href="../estilo/style.css" />
        <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.17.custom.min.js"></script>
        <script src="../js/ui/jquery.ui.autocomplete.js"></script> <!--Realiza el autocomplete de los productos-->

        <script type="text/javascript">
            $(document).ready(function(){
                
                var id= $("#idUsuarioFactura").val();

                $.ajax({
                    type: "POST",
                    url: "../Factura",
                    data: "idUsuario="+ id,
                    success: function(data){
                        $("#UsuariosClienteFactura").html(data);
                    }
                });

            });

            $(function(){
                // Tabs
                $('#tabs').tabs();


            });
        </script>

        <script>
            $(function() {
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
                var descuento = $("#descuentoFactura").val();

                
                if(cantidad == "0"){
                    $("#errorCantidadFactura").text("Indica la cantidad del producto");
                }

                if(Importe != ""){
                    var Total = Importe * cantidad;
                    descuento = descuento / 100;
                    descuento = Total * descuento;
                    Total = Total - descuento;
                    var iva =  Total * .16;
                    var granTotal = Total + iva;
                    addComas(Total,document.getElementById("importeTotal"));
                    addComas(Importe,document.getElementById("valorUnitario"));
                    addComas(iva,document.getElementById("IVATotal"));
                    addComas(granTotal,document.getElementById("GranTotal"));

                }

            }

            var Importe = 0;

            function OnlyNumber(value,elemnt){
                if( isNaN(value) ){
                    elemnt.value = "";
                }

                if(elemnt.id == "valorUnitario"){
                    Importe = value;
                }
            }

            function BorrarError(id){
                if(id == "unidadProducto"){
                    $("#errorUnidadProducto").text("");
                }else{
                    $("#errorCantidadFactura").text("");
                    $("#mensajeConfirmacion").text("");
                    ValidarSuma();
                }
            }

            function borrarProducto(){
                $("#cantidadFactura").val("0");
                $("#nombreProducto").val("");
                $("#descripcionProducto").val("");
                $("#unidadProducto").val("0");
                $("#valorUnitario").val("");
                $("#importeTotal").val("");
                $("#IVATotal").val("");
                $("#GranTotal").val("");
                $("#descuentoFactura").val("0");
                Importe = 0;
                return false;
            }

            function agregarFila(){
                var oId = parseInt($("#cant_campos").val() );
                $("#cant_campos").val( oId + 1);

                var cantidad = $("#cantidadFactura").val();
                var nombre = $("#nombreProducto").val().toUpperCase();
                var descripcion = $("#descripcionProducto").val();
                var unidad = $("#unidadProducto").val();
                var unitario = $("#valorUnitario").val();
                var descuento = $("#descuentoFactura").val();
                descuento = descuento / 100;
                var importe = $("#importeTotal").val();
                var iva = $("#IVATotal").val();
                var Total = $("#GranTotal").val();


                var strHtml1 = "<td class=\"TablaTitulo\">" + cantidad + "<input type=\"hidden\" id=\"tbDetalleCantidad_" + oId + "\" value=\" " + cantidad + "\"/></td>";
                var strHtml2 = "<td class=\"TablaTitulo\">" + nombre + "<input type=\"hidden\" id=\"tbDetalleNombre_" + oId + "\" value=\" " + nombre + "\"/></td>";
                var strHtml3 = "<td class=\"TablaTitulo\"> $ " + unitario + "<input type=\"hidden\" id=\"tbDetalleUnitario_" + oId + "\" value=\" " + unitario + "\"/></td>";
                var strHtml4 = "<td class=\"TablaTitulo\"> $ " + importe + "<input type=\"hidden\" id=\"tbDetalleImporte_" + oId + "\" value=\" " + importe + "\"/></td>";
                var strHtml5 = "<td class=\"TablaTitulo\"> $ " + iva + "<input type=\"hidden\" id=\"tbDetalleIVA_" + oId + "\" value=\" " + iva + "\"/></td>";
                var strHtml6 = "<td class=\"TablaTitulo\"> $ " + Total + "<input type=\"hidden\" id=\"tbDetalleTotal_" + oId + "\" value=\" " + Total + "\"/></td>";

                var strHtml7 = '<td class=\"TablaTitulo\"><img src=\"../images/formularios/delete.png\" width=\"16\" height=\"16\" alt=\"Eliminar\" style=\"cursor:pointer\" onclick=\"eliminarFila('+ oId +')\" />';
                strHtml7 += '<input type="hidden" id="hdnIdCampos_' + oId +'" name="hdnIdCampos[]" value="' + oId + '" />';
                strHtml7 += '<input type="hidden" id="tbDetalleDescripcion_'+ oId + '" value = "'+ descripcion + '"/>';
                strHtml7 += '<input type="hidden" id="tbDetalleUnidad_'+ oId + '" value = "'+ unidad + '"/>';
                strHtml7 += '<input type="hidden" id="tbDetalleDescuento_'+ oId + '" value = "'+ descuento + '"/></td>';

                var strHtmlTr = "<tr id='rowDetalle_" + oId + "'></tr>";
                var strHtmlFinal = strHtml1 + strHtml2 +strHtml3 + strHtml4 + strHtml5 + strHtml6 + strHtml7;

                $("#tbDetalleProducto").append(strHtmlTr);

                $("#rowDetalle_" + oId).html(strHtmlFinal);

            }

            /*
             * Eliminar Productos que no deseo
             **/
            function eliminarFila(oId){
                $("#rowDetalle_" + oId).remove();
                return false;
            }





            $.validator.setDefaults({
                submitHandler: function(){

                    if( $("#unidadProducto").val() == "0"){
                        $("#errorUnidadProducto").text("Indica la unidad del producto");
                    }else{

                        agregarFila();
                        borrarProducto();
                        $("#mensajeConfirmacion").text("Producto Agregado");
                        $("#confirmacion").show();

                    }
                }
            });


            function lookup(nombreProducto) {
                if($("#cantidadFactura").val() == 0){
                    $("#errorCantidadFactura").text("Indica la cantidad del producto");
                    $("#cantidadFactura").focus();
                    $("#nombreProducto").val("");
                }               
                if(nombreProducto.length == 0) {
                    //Esconde el dialogo de sugerencia.
                    $('#suggestions').hide();
                } else
                    //var nombreProducto= $("#nombreProducto").val();
                $.ajax({
                    type: "POST",
                    url: "../Producto",
                    data: "idUsuario=<%=id%>&nombreProducto="+ nombreProducto,
                    success: function(data){
                        if(data.length >0) {
                            $('#suggestions').show();
                            $('#autoSuggestionsList').html(data);
                        }
                    }
                });

                //}
            } // lookup

            function fill(nombreProducto,descripcionProducto,unidadProducto,valorUnitario) {
                 
                if(nombreProducto != undefined){
                    $('#nombreProducto').val(nombreProducto);
                    $('#descripcionProducto').val(descripcionProducto);
                    $('#unidadProducto').val(unidadProducto);
                    $("#valorUnitario").val(valorUnitario);
                    Importe = valorUnitario;
                    ValidarSuma();
                }else{
                    $('#descripcionProducto').val("");
                    $('#unidadProducto').val("");
                    $("#valorUnitario").val("");
                    Importe = 0;
                }
                
                /*Validar que existe el importe*/
                if(Importe == undefined || Importe == 0){
                    $("#importeTotal").val("");
                    $("#valorUnitario").val("");
                    $("#IVATotal").val("");
                    $("#GranTotal").val("");
                }
                
                setTimeout("$('#suggestions').hide();", 200);    
            }
        </script>
        <style type="text/css">

            h3 {
                margin: 0px;
                padding: 0px;
            }

            .suggestionsBox {
                position: relative;
                left: 30px;
                margin: 10px 0px 0px 0px;
                width: 200px;
                background-color: #212427;
                -moz-border-radius: 7px;
                -webkit-border-radius: 7px;
                border: 2px solid #000;
                color: #fff;
            }

            .suggestionList {
                margin: 0px;
                padding: 0px;
            }

            .suggestionList li {

                margin: 0px 0px 3px 0px;
                padding: 3px;
                cursor: pointer;
            }

            .suggestionList li:hover {
                background-color: #659CD8;
            }
        </style>

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
                                <!--  <li><a href="../perfil/consultarPerfil.jsp">Consultar Perfil</a></li> -->
                                <li><a href="../perfil/modificarPerfil.jsp">Modificar Perfil</a></li>
                                <li><a href="../perfil/administrarFIELyCSD.jsp">Administrar FIEL y CSD</a></li>
                                <li><a href="../perfil/administrarClientes.jsp">Administrar Clientes</a></li>
                            </ul>
                        </li>
                        <li><a href="../factura.jsp"><img src="../images/icons/factura_ico.png" alt=""/> Factura</a>
                            <ul>
                                <li><a href="../factura/generarFacturaElectronica.jsp">Generar Factura Electr&oacute;nica</a></li>
                                <li><a href="../factura/generarFacturaImprimible.jsp">Generar Factura para Imprimir</a></li>
                                <li><a href="">Estado de la Factura</a></li>
                            </ul>
                        </li>
                        <li><a href="../cerrar.jsp"><img src="../images/icons/ingreso_ico.png"/> Cerrar Sesión &nbsp; &nbsp; <% out.println(contribuyente);%></a></li>
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
                            <li><a href="#tabs-3">Validar XML</a></li>
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
                                        <td colspan="2">
                                            Nombre Producto:<br>
                                            <div>
                                                <div>
                                                    <input type="text" size="45" minlength="5" name="nombreProducto" id="nombreProducto"  onkeyup="lookup(this.value);" onblur="fill();" style="text-transform:uppercase" maxlength="100" />
                                                </div>
                                                <div class="suggestionsBox" id="suggestions" style="display: none;">
                                                    <img style="position: relative; top: -12px; left: 30px;" src="../images/upArrow.png" alt="upArrow" />
                                                    <div class="suggestionList" id="autoSuggestionsList">
                                                        &nbsp;
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            Descripcion:<br>
                                            <textarea id="descripcionProducto" name="descripcion" minlength="7" rows="3" cols="69" style="text-transform:uppercase" maxlength="140">
                                            </textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            Unidad:<br>
                                            <select name="unidad" id="unidadProducto" onchange="BorrarError(this.id)" >
                                                <option value="0">SELECCIONE</option>
                                                <option value="Lt">LT</option>
                                                <option value="Kg">KG</option>
                                                <option value="pieza">PIEZAS</option>
                                                <option value="Oz">OZ</option>
                                                <option value="Mt">MT</option>
                                            </select>
                                            <label id="errorUnidadProducto"></label>
                                        </td>
                                        <td>
                                            Valor Unitario:<br>
                                            $<input type="text" id="valorUnitario" name="valorUnitario" maxlength="20" onblur="ValidarSuma()" onkeyup="OnlyNumber(this.value,this)"/>
                                        </td>
                                        <td>
                                            Descuento:<br>
                                            %<input type="text" id="descuentoFactura" value="0" name="descuento" maxlength="2" onblur="ValidarSuma()" onkeypress="OnlyNumber(this.value,this)"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            SubTotal:<br>
                                            $<input type="text" id="importeTotal" name="importe" maxlength="20" readonly="readonly"/>
                                        </td>
                                        <td>
                                            IVA:<br>
                                            $<input type="text" id="IVATotal" name="IVA" maxlength="20" readonly="readonly"/>
                                        </td>
                                        <td>
                                            Total:<br>
                                            $<input type="text" id="GranTotal" name="importe" maxlength="20" readonly="readonly"/>
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
                            <!--<input type="text" id="num_campos" name="num_campos" value="0" /> -->
                            <input type="hidden" id="cant_campos" name="cant_campos" value="0" />
                            <table id="TablaProductosFacturas">
                                <thead>
                                    <tr>
                                        <th class="TablaTitulo">Cantidad</th>
                                        <th class="TablaTitulo">Producto</th>
                                        <th class="TablaTitulo">Unitario</th>
                                        <th class="TablaTitulo">SubTotal</th>
                                        <th class="TablaTitulo">IVA</th>
                                        <th class="TablaTitulo">Total</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody id="tbDetalleProducto">
                                </tbody>
                            </table>
                            <br/><br/>
                            <div id="confirmacion">
                                Cliente Asociado a la Factura: <select id="UsuariosClienteFactura"></select>
                                <br/><br/><br/>
                                <div align="right">
                                    <input  type="submit" width="50" value="Confirmar Factura" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                                </div>
                            </div>
                        </div>
                        <div id="tabs-3">
                            <iframe src="https://www.consulta.sat.gob.mx/SICOFI_WEB/ModuloECFD_Plus/ValidadorComprobantes/Validador.asp" width="770" height="930" frameborder="0"> </iframe>
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