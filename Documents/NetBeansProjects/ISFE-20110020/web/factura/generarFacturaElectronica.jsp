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
        <script src="../js/jquery.validate.js"></script> <!--Validar Formulario -->
        <script src="../js/jquery.maskedinput.js"></script> <!--Mascara para formulario -->

        <script type="text/javascript">
            $(document).ready(function(){
                $("#descripcionFactura").validate();    
            }); 
             
            $(function(){

                // Accordion
                $("#accordion").accordion({ header: "h3" });
	
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
            var importe = "";
            
            function addComas(nStr,elemnt,nombre){
                nStr = Math.round(nStr*100)/100; 
                
                if(nombre != null){
                    importe = nStr;
                }
                
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
                if(cantidad == "0"){
                    $("#errorCantidadFactura").text("Indica la cantidad del producto");
                }else{
                    var Total = importe * cantidad;
                    addComas(Total,document.getElementById("importeTotal"),null);
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
            
            
            
            $.validator.setDefaults({
                submitHandler: function(){
                    
                    if( $("#unidadProducto").val() == "0"){
                        $("#errorUnidadProducto").text("Indica la unidad del producto");
                    }else{
                        //var cantidad = $("#cantidadFactura").val();
                        var nombre = $("#nombreProducto").val().toUpperCase();
                        var numero = $("#numIdentificacion").val().toUpperCase();
                        var descripcion = $("#descripcionProducto").val().toUpperCase();
                        var unidad = $("#unidadProducto").val();
                        /*var unitario = $("#valorUnitario").val();
                        var importe = $("#importeTotal").val(); */
            
                        var tbody = document.getElementById("TablaProductosFacturas").getElementsByTagName("TBODY")[0];
                        var row = document.createElement("TR")
                        var td1 = document.createElement("TD")
                        td1.appendChild(document.createTextNode(nombre))
                        var td2 = document.createElement("TD")
                        td2.appendChild (document.createTextNode(numero))
                        var td3 = document.createElement("TD")
                        td3.appendChild (document.createTextNode(descripcion))
                        var td4 = document.createElement("TD")
                        td4.appendChild (document.createTextNode(unidad))
                        row.appendChild(td1);
                        row.appendChild(td2);
                        row.appendChild(td3);
                        row.appendChild(td4);
                        tbody.appendChild(row);
                        
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
                                            <select name="cantidad" id="cantidadFactura" onchange="BorrarError(this.id);">
                                                <option value="0">Seleccione</option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="6">6</option>
                                                <option value="7">7</option>
                                                <option value="8">8</option>
                                                <option value="9">9</option>
                                                <option value="10">10</option>
                                            </select>
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
                                            $<input type="text" id="valorUnitario" class="required" name="valorUnitario" maxlength="20" onblur="addComas(this.value,this,this.id)" onkeypress="OnlyNumber(this.value,this)"/>
                                        </td>
                                        <td>
                                            Importe:<br>
                                            $<input type="text" id="importeTotal" name="importe" class="required" maxlength="20" onblur="ValidarSuma()" onkeypress="OnlyNumber(this.value,this)"/>
                                        </td>
                                    </tr>
                                </table>
                                <br/>
                                <input type="submit" width="50" value="Agregar Producto" name="agregar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                                &nbsp; &nbsp; &nbsp; <input type="reset" width="50" value="Cancelar" name="cancelar" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
                            </form>
                        </div>
                        <div id="tabs-2">
                            <br/>
                            <br/>
                            <table border="0" id="TablaProductosFacturas">
                                <form>
                                    <tbody>
                                        <tr>
                                            <td>Nombre del Producto</td>
                                            <td>N° de Identificación</td>
                                            <td>&nbsp; Descripción &nbsp;</td>
                                            <td>Unidad</td>
                                            <td>Cantidad</td>
                                            <td>&nbsp; Valor Unitario &nbsp;</td>
                                            <td>&nbsp; Importe &nbsp;</td>
                                        </tr>
                                        <tr>
                                        </tr>
                                    </tbody>
                                </form>
                            </table>
                            <br/>
                            <div id="confirmacion" style="" align="right"><input type="submit" width="50" value="Confirmar Factura" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/> </div>
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