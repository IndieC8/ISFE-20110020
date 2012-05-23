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
        <script src="../js/jquery.factura.js"></script>

        <script type="text/javascript">
            $(document).ready(function(){
                
                $.ajax({
                    type: "POST",
                    url: "../Factura",
                    data: "Factura=Clientes&idUsuario=<%=id%>",
                    success: function(data){
                        $("#UsuariosClienteFactura").html(data);
                    }
                });

            });

        
            function lookup(nombreProducto) {
                BorrarError("nombreProducto");
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
                                //alert(data);
                            }
                        }
                });

                //}
            } // lookup
            
            //Generación del XML
            function GenerarXML(campos,cantidad,nombre,unitario,totalProducto,descripcion,sub,iva,total){
            
                $.ajax({
                    url:"../Factura",
                    type: "POST",
                    data: "Factura=Generar&idUsuaio=<%=id%>&cant_campos="+campos+"&cantidad="+cantidad+"&nombre="+nombre+"&unitario="+unitario+"&total="+totalProducto+"&descripcion="+descripcion+"&formaDePago=Efectivo&subTotal="+sub+
                        "&iva="+iva+"&descuento=0&GranTotal="+total+"&tipoComprobante=INGRESO",
                    success: function(data){
                        alert(data);
                        $("#cant_campos").val("0");
                        $("#tbDetalleProducto").html("");
                        $("#mensajeConfirmacion").text("");
                    }
                });
            }
            
            function NuevoProducto(nombre,descripcion,unidad,valor){
                $.ajax({
                    url: "../Producto", type: "POST",
                    data:"idUsuario=<%=id%>&Producto=Agregar&nombreProducto="+nombre+"&descripcion="+descripcion+"&unidad="+unidad+"&valorUnitario="+valor
                });
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
    <!--Aqui va el dialogo de Modificar Password-->
    <div id="confirmacionFactura" title="ISFE- Generación de Factura">
        <p class="validateTips">
            <img src="../images/xml.png" />
            &nbsp;
            Los totales de la Factura son:
        </p>
        <input type="hidden" id="auxConfirmacion"/>
        <br/>
        <center><table class="table1">
                <tbody>
                    <tr>
                        <th scope="row">SubTotal</th>
                        <td id="confirmarSubTotalFactura"><span class="check"></span></td>

                        <th scope="row">IVA</th>
                        <td id="confirmarIVAFactura"><span class="check"></span></td>

                        <th scope="row">Total</th>
                        <td id="confirmarTotalFactura"><span class="check"></span></td>
                    </tr>
                </tbody> </table></center>
        <form>
            <fieldset>  
                Cliente: &nbsp; &nbsp;
                <select id="UsuariosClienteFactura"></select>
            </fieldset>
            <fieldset>  
                Forma de Pago: &nbsp; &nbsp;
                <select id="FormaPago">
                    <option value="EFECTIVO">EFECTIVO</option>    
                    <option value="TRANSFERENCIA">TRANSFERENCIA ELECTRÓNICA</option>
                    <option value="CHEQUE">CHEQUE</option>
                    <option value="DEBITO">TARJETA DE DÉBITO</option>
                    <option value="CREDITO">TARJETA DE CRÉDITO</option>
                    <option value="SERVICIO">TARJETA DE SERVICIO</option>
                    <option value="MONEDERO">MONEDERO ELECTRÓNICO</option>
                </select>
            </fieldset>
            <fieldset>  
                  Tipo de Comprobante: &nbsp; &nbsp;
                  <select id="tipoComprobante">
                      <option value="EGRESO">EGRESO</option>
                      <option value="INGRESO">INGRESO</option>
                  </select>
            </fieldset>
        </form>
    </div>
    <!--Aqui termina el dialogo de Modificar Password-->
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
                                <li><a href="../factura/EstadoFactura.jsp">Estado de la Factura</a></li>
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
                                <input type="hidden" id="idProductoFactura" value="0"/>
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
                                                    <label id="errorNombreProducto"></label>
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
                                            <textarea id="descripcionProducto" onkeypress="BorrarError(this.id)" name="descripcion" minlength="7" rows="3" cols="69" style="text-transform:uppercase" maxlength="140">
                                            </textarea>
                                            <label id="errorDescripcionProducto"></label>
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
                                            $<input type="text" id="valorUnitario" onkeypress="BorrarError(this.id)" name="valorUnitario" maxlength="20" onblur="ValidarSuma()" onkeyup="OnlyNumber(this.value,this)"/>
                                            <label id="errorValorProducto"></label>
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
                                            $<input type="text" id="IVATotal" name="IVA" maxlength="20" readonly="readonly"/></td>
                                        <td>
                                            Total:<br>
                                            $<input type="text" id="GranTotal" name="importe" maxlength="20" readonly="readonly"/>
                                        </td>
                                    </tr>
                                </table>
                                <br />
                                <label id="mensajeConfirmacion"></label>
                                <br/>
                                <input type="button" onclick="AgregarProducto()" width="50" value="Agregar Producto" name="agregar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
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
                            <div align="right">
                                <input  type="button" onclick="GenerarFactura()" width="50" value="Confirmar Factura" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
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
