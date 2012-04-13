<%--
    Document   : index-user
    Created on : 13/04/2012, 05:35:02 PM
    Author     : kawatoto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
            <title>ISFE - Inicio</title>
            <link rel="stylesheet" type="text/css" href="estilo/style.css" />
            <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
            <script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
            <script src="js/jquery.fileUploader.js" type="text/javascript"></script>
            <script src="js/ui/jquery.ui.core.js"></script>
            <script src="js/ui/jquery.ui.widget.js"></script>
            <script src="js/ui/jquery.ui.mouse.js"></script>
            <script src="js/ui/jquery.ui.button.js"></script>
            <script src="js/ui/jquery.ui.draggable.js"></script>
            <script src="js/ui/jquery.ui.position.js"></script>
            <script src="js/ui/jquery.ui.resizable.js"></script>
            <script src="js/ui/jquery.ui.dialog.js"></script>
            <script src="js/ui/jquery.effects.core.js"></script>
            <script src="js/jquery.maskedinput.js"></script>
            <script src="js/jquery.maskedinput.js"></script>
            <script src="js/jquery.menu.js"></script>

            <script type="text/javascript">
			$(function(){

				// Slider
				$('#slider').slider({
					range: true,
					values: [17, 67]
				});
			});

                        jQuery(function(){
                            $("#RFCLogin").mask("aaa*-999999-aaa*");
                         });
		</script>

            <script>
                    $(function() {
                        $( "input:submit, a, button", ".demo" ).button();
                        $( "a", ".demo" ).click(function() { return false; });
                    });

                    /*Validar login*/
                    function Sesion(){
                        $("#errorLoginPWD").text("");
                        $("#errorLoginRFC").text("");


                        var rfc = $("#RFCLogin").val().toUpperCase();
                        var aux = rfc.split("-");
                        rfc = aux[0]+aux[1]+aux[2];

                        var pwd = $("#passwordLogin").val();
                        var campo = document.getElementById("passwordLogin");
                        var rpwd = /^([0-9a-zA-Z])+$/;

                        if(rfc == ""){
                            $("#errorLoginRFC").text("Ingresa tu R.F.C.");
                        }else if(!campo.value.match(rpwd)){
                            $("#errorLoginPWD").text("El password solo debe contener: a-z 0-9");
                            $("#passwordLogin").val("");
                        }

                        return false;

                    }
          </script>
	<center>
            <div class="principal">
            <div class="header">
                <div class="logo"><a href="index.jsp" ><img src="images/logo1.png" alt="ISFE" height="164"/></a></div>
            </div>
		<div class="contenido_principal">
		<!-- Comienza Menu -->
                <div class="menu">
		<ul>
                    <li><a href=""><img src="images/icons/valida_ico.png" alt=""/>¿C&oacute;mo usar ISFE?</a></li>
                       <li><a href="contacto.jsp"><img src="images/icons/contacto_ico.png" alt=""/>Contacto</a></li>
                       <li><a href="registro.jsp"><img src="images/icons/registro_ico.png" alt=""/> Registro</a></li>
                       <li><a href="index.jps"><img src="images/icons/ingreso_ico.png" alt=""/> Ingreso</a></li>
		</ul>
                </div>
		<!-- Termina Menu -->
                <br/>
		<div class="titulo_pagina">Inicio</div>
		<center>
                    <table border="0">
                        <tbody>
                            <tr class="trLogin">
                                <td rowspan="2" class="tdLogin"><iframe src="slider-user.html" height="295" width="620" frameborder="0"></iframe></td>
                                <td class="tdLogin">
                                    <form action="Ingreso" method="post">
                                        <h1><center>V&iacute;nculos de Inter&eacute;s</center></h1>
                                        <table border="0">
                                            <tbody>
                                                <tr class="trLogin">
                                                    <td colspan="3" class="tdLogin">
                                                        <img src="images/sat.jpg"/> P&aacute;gina Web del SAT
                                                    </td>
                                                </tr>
                                                <tr class="trLogin">
                                                    <td>
                                                        <img src="images/pdf.jpg"/> Anexo 20 de la Miscel&aacute;nea Fiscal
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <img src="images/youtube.jpg"/> Canal oficial del SAT
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <!--Aqui termina el slider de imagenes-->
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