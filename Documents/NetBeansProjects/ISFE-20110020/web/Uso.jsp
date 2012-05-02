<%--
    Document   : comousarisfe
    Created on : 19/04/2012, 11:33:51 AM
    Author     : kawatoto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" import="dao.*" session="true"
         import="java.util.*"%>

<!DOCTYPE html>
<html>
	<head>
            <title>ISFE - Inicio</title>
            <link rel="stylesheet" type="text/css" href="estilo/style.css" />
            <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
            <script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
            <script type="text/javascript" src="js/jquery.MultiFile.js"></script>
                


            <script type="text/javascript">
			 $(function(){

				// Tabs
				$('#tabs').tabs();

			});

                        
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
                    <li><a href="Uso.jsp"><img src="images/icons/valida_ico.png" alt=""/>¿C&oacute;mo usar ISFE?</a></li>
                       <li><a href="contacto.jsp"><img src="images/icons/contacto_ico.png" alt=""/>Contacto</a></li>
                       <li><a href="registro.jsp"><img src="images/icons/registro_ico.png" alt=""/> Registro</a></li>
                       <li><a href="/ISFE-20110020/index.jsp"><img src="/ISFE-20110020/images/icons/ingreso_ico.png" alt=""/> Ingreso</a></li>
		</ul>
                </div>
		<!-- Termina Menu -->
                <br/>
		<div class="titulo_pagina">
                    <br/>
                    ¿Cómo usar ISFE ?</div>
                <br/>
		<center>
                    <div id="tabs">
                        <ul>
                            <li><a href="#tabs-1">Presentaci&oacute;n</a></li>
                            <li><a href="#tabs-2">Registro a ISFE</a></li>
                            <li><a href="#tabs-3">Inicio de Sesi&oacute;n</a></li>
                            <li><a href="#tabs-4">Manejo de Perfil</a></li>
                            <li><a href="#tabs-5">Factura Electr&oacute;nica</a></li>
                        </ul>
                        <div id="tabs-1">
                            <h2>Esta es una presentacion general del sistema asi como una reseña de la legislacion y las funcionalidades mas importantes de ISFE.</h2><br>
                            <iframe width="560" height="315" src="http://www.youtube.com/embed/yEoHFzEmld0" frameborder="0"></iframe>
                        </div>
                        <div id="tabs-2">
                            <h2>En este video se muestra como realizar el registro de un nuevo usuario al sistema.</h2><br>
                            <iframe width="420" height="315" src="http://www.youtube.com/embed/o1tj2zJ2Wvg" frameborder="0"></iframe>
                        </div>
                        <div id="tabs-3">
                            <h2>Aqui se mostrara como se realiza el inicio de sesion al sistema asi como las restriocciones dadas en caso de no estar dado de alta.</h2><br>
                            <iframe width="420" height="315" src="http://www.youtube.com/embed/qfB8fmHzc5Q" frameborder="0"></iframe>
                        </div>
                        <div id="tabs-4">
                            <h2>Este video tutorial consta de cuatro partes en las que se veran la administracion de los clientes del usuario, como administrar la FIEL y CSD, la consulta del perfil y la modificaicon del mismo.</h2>
                            <br><br>
                            Administacion de Clientes.<br>
                            <iframe width="560" height="315" src="http://www.youtube.com/embed/cl64-XHE7zo" frameborder="0"></iframe>
                            <br><br>
                            Administracion de FIEL y CSD<br>
                            <iframe width="420" height="315" src="http://www.youtube.com/embed/co6WMzDOh1o" frameborder="0"></iframe>
                            <br><br>
                            Consulta de perfil<br>
                            <iframe width="560" height="315" src="http://www.youtube.com/embed/w8KQmps-Sog" frameborder="0"></iframe>
                            <br><br>
                            Modifical Perfil<br>
                            <iframe width="420" height="315" src="http://www.youtube.com/embed/V8EA2jrBvso" frameborder="0"></iframe>
                        </div>
                        <div id="tabs-5">
                            <h2>En esta seccion se vera la captura de los datos correspondientes a la factura electr&oacute;nica asi como la generacion de la misma en formato PDF</h2><br>
                            <br>
                            Generaci&oacute;n de Factura Electr&oacute;nica<br>
                            <iframe width="420" height="315" src="http://www.youtube.com/embed/SYVxvY68DZQ" frameborder="0"></iframe>
                            <br><br>
                            Generaci&oacute;n de Factura Imprimible (PDF)<br>
                            <iframe width="560" height="315" src="http://www.youtube.com/embed/a1ZB3Wbm0ek" frameborder="0"></iframe>
                        </div>
                    </div>
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