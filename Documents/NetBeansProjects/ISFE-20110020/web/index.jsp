<%--
    Document   : Index
    Created on : 23/01/2012, 03:09:10 PM
    Author     : kawatoto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" import="dao.*" session="true"
         import="java.util.*"%>

<!DOCTYPE html>
<html>
	<head>
            
            <title>ISFE - Inicio</title>
            <link rel="stylesheet" type="text/css" href="/ISFE-20110020/estilo/style.css" />
            <script defer type="text/javascript" src="js/pngfix.js"></script>
            <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
            <script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
            <script src="/ISFE-20110020/js/jquery.maskedinput.js"></script>
            <script src="/ISFE-20110020/js/jquery.menu.js"></script>

            <script type="text/javascript">
			$(function(){

				// Slider
				$('#slider').slider({
					range: true,
					values: [17, 67]
				});
			});

                        jQuery(function(){
                            $("#RFCLogin").mask("aaa*-999999-aa*");
                         });
		</script>

            <script>
                    $(function() {
                        $( "input:submit, a, button", ".demo" ).button();
                        $( "a", ".demo" ).click(function() { return false; });
                    });

                    /*Validar login*/
                    function Sesion(){
                        $("#errorUsuarioNoEncontrado").text("");
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

                        $.ajax({
                            type: "POST",
                            url: "Ingreso",
                            data: "Login=consulta&RFCLogin="+rfc+"&passwordLogin="+pwd,
                            success: function(data){
                                if(data == ""){
                                    $("#errorUsuarioNoEncontrado").text("Usuario no dado del Alta");
                                    $("#RFCLogin").val("");
                                    $("#RFCLogin").focus();
                                    $("#passwordLogin").val("");
                                }else{
                                    document.fLogin.submit();
                                }
                            }
                        });

                    }
          </script>
	<center>
            <div class="principal">
            <div class="header">
                <div class="logo"><a href="/ISFE-20110020/index.jsp" ><img src="/ISFE-20110020/images/logo1.png" alt="ISFE" height="164"/></a></div>
            </div>
		<div class="contenido_principal">
		<!-- Comienza Menu -->
                <div class="menu">
		<ul>
                    <li><a href="/ISFE-20110020/Uso.jsp"><img src="/ISFE-20110020/images/icons/valida_ico.png" alt=""/>¿C&oacute;mo usar ISFE?</a></li>
                       <li><a href="/ISFE-20110020/contacto.jsp"><img src="/ISFE-20110020/images/icons/contacto_ico.png" alt=""/>Contacto</a></li>
                       <li><a href="/ISFE-20110020/registro.jsp"><img src="/ISFE-20110020/images/icons/registro_ico.png" alt=""/> Registro</a></li>
                       <li><a href="/ISFE-20110020/index.jsp"><img src="/ISFE-20110020/images/icons/ingreso_ico.png" alt=""/> Ingreso</a></li>
		</ul>
                </div>
		<!-- Termina Menu -->
                <br/>
		<div class="titulo_pagina">Inicio</div>
		<center>
                    <table border="0">
                        <tbody>
                            <tr class="trLogin">
                                <td rowspan="2" class="tdLogin"><iframe src="/ISFE-20110020/slider.html" height="295" width="620" frameborder="0"></iframe></td>
                                <td class="tdLogin">
                                    <form action="Ingreso" name="fLogin" method="post">
                                        <table border="0">
                                            <tbody>
                                                <tr class="trLogin">
                                                    <h2>&nbsp;&nbsp;&nbsp;Ingrese a ISFE</h2>
                                                    &nbsp; &nbsp; R.F.C. del contribuyente:
                                                    <td colspan="3" class="tdLogin">
                                                        <input type="text" name="RFCLogin" style="text-transform:uppercase" id="RFCLogin"  class="text ui-widget-content ui-corner-all" />
                                                        <label id="errorLoginRFC"></label>
                                                    </td>
                                                </tr>
                                                <tr class="trLogin">
                                                    <td class="tdLogin">
                                                        <input type="password" name="passwordLogin" id="passwordLogin"  class="text ui-widget-content ui-corner-all" />
                                                        <label id="errorLoginPWD"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="tdLogin">
                                                        <label id="errorUsuarioNoEncontrado"></label>
                                                        <input type="button" onclick="Sesion()"  value="Iniciar Sesi&oacute;n" class="ui-button ui-widget ui-state-default ui-corner-all"><input type="reset" value="Cancelar" class="ui-button ui-widget ui-state-default ui-corner-all">
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </td>
                            </tr>
                            <tr class="trLogin">
                                <td class="tdLogin">
                                    <h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¿Eres nuevo en ISFE?</h2>
                                    <h3><a class="estiloLink" href="/ISFE-20110020/registro.jsp">&nbsp;&nbsp;&nbsp;&nbsp;Reg&iacute;strate haciendo click aqui</a></h3>
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
                <a href="http://twitter.com/" ><img src="/ISFE-20110020/images/twitter.png" alt="http://twitter.com" width="25" height="25"></a>
                <a href="http://www.facebook.com/" ><img src="/ISFE-20110020/images/Facebook.png" alt="http://www.facebook.com" width="25" height="25"></a>
                <br>
                Este sitio se visualiza mejor con Google Chrome
            </div>
        </div>
    </center>
    </body>
</html>