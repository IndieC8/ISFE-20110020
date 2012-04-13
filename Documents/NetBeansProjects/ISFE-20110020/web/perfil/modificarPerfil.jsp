<%-- 
    Document   : modificarPerfil
    Created on : 1/02/2012, 11:12:34 AM
    Author     : kawatoto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>ISFE - Modificar Perfil</title>
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
        
            <script type="text/javascript">
                
                 jQuery(function(){
                    $("#RFCLogin").mask("aaa*-999999-aaa*");
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
		</script>
            
            <script>
                    $(function() {
                        $( "input:submit, a, button", ".demo" ).button();
                        $( "a", ".demo" ).click(function() { return false; });
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
		<div class="titulo_pagina">Modificar Perfil</div>
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
                                        <td>Elemento 1</td>
                                        <td><input type="text" name="Valor A 1" value="Valor A 1" onclick="select()"/></td>
                                        <td><input type="submit" value="Editar" name="Valor A 1" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                    </tr>
                                    <tr>
                                        <td>Elemento 2</td>
                                        <td><input type="text" name="Valor A 2" value="Valor A 2" onclick="select()"/></td>
                                        <td><input type="submit" value="Editar" name="Valor A 2" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                    </tr>
                                    <tr>
                                        <td>Elemento 3</td>
                                        <td><input type="text" name="Valor A 3" value="Valor A 3" onclick="select()"/></td>
                                        <td><input type="submit" value="Editar" name="Valor A 3" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                    </tr>
                                    <tr>
                                        <td>Elemento 4</td>
                                        <td><input type="text" name="Valor A 4" value="Valor A 4" onclick="select()"/></td>
                                        <td><input type="submit" value="Editar" name="Valor A 4" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                    </tr>
                                    <tr>
                                        <td>Elemento 5</td>
                                        <td><input type="text" name="Valor A 5" value="Valor A 5" onclick="select()"/></td>
                                        <td><input type="submit" value="Editar" name="Valor A 5" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
                                    </tr>
                                    <tr>
                                        <td>Elemento 6</td>
                                        <td><input type="text" name="Valor A 6" value="Valor A 6" onclick="select()"/></td>
                                        <td><input type="submit" value="Editar" name="Valor A 6" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/></td>
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