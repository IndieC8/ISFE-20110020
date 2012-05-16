/* #codigoPostal"
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*Variables Globales*/
var FIEL, CSD;
var contribuyente;
var rfc, tel, calle, exterior, interior, col , mail , referencia, pwd, nombre, paterno, materno , curp,razon;

/*Funciones Generales del Menu del Usuario y Registro*/
$(function(){

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

				
});
            
                        

$(function() {
    // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
		
                
    $("#dialogDireccionPerfil").dialog({
        autoOpen: false,
        height: 400,
        width: 450,
        modal: true,
        buttons: {
            "Aceptar": function() {
                $("#ColoniaModificarPerfil").val($("#localidadModificarPerfil option:selected").html());
                $("#MunicipioModificarPerfil").val($("#municipioModificarPerfil option:selected").html());
                $("#idLocalidadModificar").val( $("#localidadModificarPerfil").val() );
                $( this ).dialog("close");
                                        
            },
            Cancelar: function() {
                $("#ConfirmarModificacion").hide();
                $("#MensajeConfirmarModificacion").text("");
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });
                
                
		
    $( "#dialogPwdPerfil" ).dialog({
        autoOpen: false,
        height: 300,
        width: 380,
        modal: true,
        buttons: {
            "Aceptar": function() {
                var pwd1 = $("#pwdModificarPerfil").val();
                var pwd2 = $("#pwd2ModificarPerfil").val();
                var rpwd = /^([0-9a-zA-Z])+$/;
                var campo = document.getElementById("pwdModificarPerfil");
                            
                if(!campo.value.match(rpwd)){
                    $("#errorModificarPwd").text("La contraseña solo debe contener: a-z 0-9");
                    $("#pwdModificarPerfil").val("");
                    $("#pwd2ModificarPerfil").val("");
                    $("#pwdModificarPerfil").focus();
                    return false;
                }else if(pwd1 != pwd2){
                    $("#errorModificarPwd").text("Las contraseñas deben ser iguales");
                    $("#pwdModificarPerfil").val("");
                    $("#pwd2ModificarPerfil").val("");
                    $("#pwdModificarPerfil").focus();
                    return false;
                }else if(pwd1.length < 5){
                    $("#errorModificarPwd").text("Ingresa más de 5 caracteres");
                    $("#pwdModificarPerfil").val("");
                    $("#pwd2ModificarPerfil").val("");
                    $("#pwdModificarPerfil").focus();
                    return false;
                }else{
                    $("#passwordModificarPerfil").val(pwd2);
                }
                            
                $( this ).dialog("close");
                                        
            },
            Cancelar: function() {
                $("#ConfirmarModificacion").hide();
                $("#MensajeConfirmarModificacion").text("");
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });
    
    /*REGISTRO*/
    $("#ConfirmarDatos").dialog({
        autoOpen: false,
        height: 380,
        width: 750,
        modal: true,
        buttons: {
            "Aceptar": function() {
                
                $.ajax({
                    type: "POST",
                    url: "Registrar",
                    data: "usuario=Guardar&Tipo="+contribuyente+"&nombre="+nombre+"&paterno="+paterno+"&materno="+materno+"&razon="+razon+"&rfcUsuario="+rfc+"&curp="+curp+"&telefono="+tel+"&calle="+calle+"&interior="+interior+"&exterior="+exterior+"&localidad="+col+"&mail="+mail+"&referencia="+referencia+"&password="+pwd,
                    success: function(data){
                        alert("Registro Hecho");
                        LimpiarRegistro();
                                
                    }
                });
                $( this ).dialog("close");
                                        
            },
            Cancelar: function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });
                
    $("#dialogPrivadaUsuario").dialog({
        autoOpen: false,
        height: 280,
        width: 350,
        modal: true,
        buttons: {
            "Aceptar": function() {
                alert("ACEPTANDO");
                $( this ).dialog("close");
                                        
            },
            Cancelar: function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });
});

/*Limpiar los campos del Registro*/
function LimpiarRegistro(){
    $("#UsuarioRFC").val("");
    $("#UsuarioTelefono").val("");
    $("#UsuarioCalle").val("");
    $("#UsuarioExterior").val("");
    $("#UsuarioInterior").val("")
    $("#estado").val("")
    $("#localidad").html("");
    $("#municipio").html("");
    $("#emailUsuario").val("");
    $("#referenciaUsuario").val("");
    $("#rePasswordUsuario").val("");
    $("#RazonUsuario").val("");
    $("#RazonUser").hide();
    $("#nombreUsuario").val("");
    $("#NombreUser").hide();
    $("#APaternoUsuario").val("");
    $("#PaternoUser").hide();
    $("#AMaternoUsuario").val("");
    $("#MaternoUser").hide();
    $("#CURPUsuario").val("");
    $("#CURPUser").hide();
    $("#codigoPostal").val("");
    $("#passwordUsuario").val("");
    //$("#personaMoral").attr('checked','');
    //$("#personaFisica").attr('checked','');
    return false;
    
}

/*Valida los datos del Registro*/
$.validator.setDefaults({
    submitHandler: function() {
                    
        //$("#siguienteRegistro").attr("disabled", "disabled");
        rfc = $("#UsuarioRFC").val().toUpperCase();
        var aux = rfc.split("-");
        rfc = aux[0]+aux[1]+aux[2];
        tel = $("#UsuarioTelefono").val().toUpperCase();
        calle = $("#UsuarioCalle").val().toUpperCase();
        exterior = $("#UsuarioExterior").val().toUpperCase();
        interior = $("#UsuarioInterior").val().toUpperCase();
        col = $("#localidad").val();
        mail = $("#emailUsuario").val();
        referencia= $("#referenciaUsuario").val().toUpperCase();
        pwd = $("#rePasswordUsuario").val();
                    
        if(interior != ""){
            var aux = "No Interior "+interior
        }else{
            var aux = "";
        }
                    
        $("#confirmarRFC").text(rfc);
        $("#confirmarTelefono").text(tel);
        $("#confirmarMail").text(mail);
        $("#confirmarDireccion").text("Calle: "+calle+" No. Exterior "+exterior+" "+aux);
        $("#confirmarReferencia").text(referencia);
        $("#confirmarColonia").text( $("#localidad option:selected").html() );
        $("#confirmarMunicipio").text( $("#municipio option:selected").html() );
        $("#confirmarEstado").text( $("#estado").val() ); 
                    
                    
        if(contribuyente == "Moral"){
            razon = $("#RazonUsuario").val().toUpperCase();
            $("#confirmarRazonSocial").text(razon);
                        
        }else{
            nombre = $("#nombreUsuario").val().toUpperCase();
            paterno = $("#APaternoUsuario").val().toUpperCase();
            materno =$("#AMaternoUsuario").val().toUpperCase();
            curp = $("#CURPUsuario").val().toUpperCase();
            $("#confirmarRazonSocial").text(nombre+" "+paterno+" "+materno);
            if(curp != ""){
                $("#confirmarCURP").text(curp);
                $("#CUPRConfirmacion").show();
                $("#confirmarCURP").show();
            }
        }
        $("#ConfirmarDatos").dialog("open");
    }
});

/*Evalua la opción si es Persona Fisica o moral*/
function Contribuyente(value){
    contribuyten = "";
    $("#RazonUser").hide();
    $("#NombreUser").hide();
    $("#PaternoUser").hide();
    $("#MaternoUser").hide();
    $("#CURPUser").hide();
    $("#NombreUsuario").val("");
    $("#APaternoUsuario").val("");
    $("#AMaternoUsuario").val("");
    $("#CURPUsuario").val("");
    $("#RazonUsuario").val("");
                
                
                 
    if(value == "Moral"){
        $("#RazonUser").show();
        contribuyente = "Moral";
    }else{
        $("#NombreUser").show();
        $("#PaternoUser").show();
        $("#MaternoUser").show();
        $("#CURPUser").show();
        contribuyente = "Fisica";
    }
}
            
function SubirKEY(){
    $("#dialogPrivadaUsuario").dialog("open");
}

function SubirCSD(){
    var archivo = $("#archivoCER").val();
    $.ajax({
        url: "Perfil",
        type: "POST",
        data: "Archivo=CSD&archivo="+archivo,
        success: function(data){
            alert(data);
        }
    });
}
         
                       
/*Busca si existe el rfc*/
function validarRFC(){
    var rfc = $("#UsuarioRFC").val().toUpperCase();
    var aux = rfc.split('-');
    rfc = aux[0]+aux[1]+aux[2];
    $("#ErrorRFCUsuario").text("");
                
    $.ajax({
        type: "POST",
        url: "Registrar",
        data: "usuario=validar&rfc="+rfc,
        success: function(data){
            $("#ErrorRFCUsuario").text(data);
        }
    });
}
    
jQuery(function(){
    $("#UsuarioRFC").mask("aaaa-999999-aaa");
    $("#CURPUsuario").mask("aaaa999999aaaaaa99");
});


function ModificarPassword(){
    $("#errorModificarPwd").text("");
    $("#pwdModificarPerfil").val("");
    $("#pwd2ModificarPerfil").val("");
    $("#guardarCambiosModificarPerfil").show();
    $("#ConfirmarModificacion").show();
    $("#MensajeConfirmarModificacion").text("");
    $( "#dialogPwdPerfil" ).dialog( "open" );
}
            
function ModificarDireccion(){
    $("#errorModificarColonia").text("");
    $("#codigoModificarPerfil").val("");
    $("#estadoModificarPerfil").val("");
    $("#localidadModificarPerfil").html("");
    $("#municipioModificarPerfil").html("");
    $("#guardarCambiosModificarPerfil").show();
    $("#ConfirmarModificacion").show();
    $("#MensajeConfirmarModificacion").text("");
    $("#dialogDireccionPerfil").dialog("open");
}
            
function BuscarCP(){
    $("#errorModificarColonia").text("");
    var codigo = $("#codigoModificarPerfil").val();
    obtenerEstado(codigo,"errorModificarColonia","estadoModificarPerfil","municipioModificarPerfil","localidadModificarPerfil","codigoModificarPerfil","../");
                
}

function BuscarCPRegistro(){
    $("#ErrorCodigoPostalUsuario").text("");
    var codigo = $("#codigoPostal").val();
    obtenerEstado(codigo,"ErrorCodigoPostalUsuario","estado","municipio","localidad","codigoPostal","");
}


function EditarModificacion(elemnt){
    $("#"+elemnt).removeAttr("readonly");
    $("#"+elemnt).focus();
    $("#ConfirmarModificacion").show();
    $("#MensajeConfirmarModificacion").text("");
}

/*Permite ingresar solo números*/
function OnlyNumber(value,elemnt){
    if( isNaN(value) ){
        elemnt.value = "";
    }
}

function obtenerEstado(codigo,error,estado,municipio,localidad,campo,url){
    
    $.ajax({
        type:"POST",
        url: url+"ObtenerEstado",
        data:"codigoPostal="+codigo,
        success: function(datos){
            $("#"+estado).val(datos);
            if(datos == ""){
                $("#"+error).text("El código postal no existe");
                $("#"+campo).val("");
                $("#"+campo).focus();
                return false;
            }
           
        }
    });
                
    $.ajax({
        type:"POST",
        url:url+"ObtenerMunicipio",
        data:"codigoPostal="+codigo,
        success: function(data){
            $("#"+municipio).html(data);
        }    
    });
                
    $.ajax({
        type:"POST",
        url:url+"ObtenerLocalidad",
        data:"codigoPostal="+codigo,
        success: function(data){
            $("#"+localidad).html(data);
        }    
    });
    
}
