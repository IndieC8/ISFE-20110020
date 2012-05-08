/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
            
                        
/*
             *Activar el dialogo de Modificar password
             */
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
                //alert("ACEPTANDO");
                $("#ColoniaModificarPerfil").val($("#localidadModificarPerfil option:selected").html());
                $("#MunicipioModificarPerfil").val($("#municipioModificarPerfil option:selected").html());
                            
                $( this ).dialog("close");
                                        
            },
            Cancel: function() {
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
        width: 350,
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
            Cancel: function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });
});

function ModificarPassword(){
    $("#errorModificarPwd").text("");
    $("#pwdModificarPerfil").val("");
    $("#pwd2ModificarPerfil").val("");
    $("#guardarCambiosModificarPerfil").show();
    $( "#dialogPwdPerfil" ).dialog( "open" );
}
            
function ModificarDireccion(){
    $("#errorModificarColonia").text("");
    $("#codigoModificarPerfil").val("");
    $("#estadoModificarPerfil").val("");
    $("#localidadModificarPerfil").html("");
    $("#municipioModificarPerfil").html("");
    $("#guardarCambiosModificarPerfil").show();
    $("#dialogDireccionPerfil").dialog("open");
}
            
function BuscarCP(){
    $("#errorModificarColonia").text("");
    var codigo = $("#codigoModificarPerfil").val();
    obtenerEstado(codigo,"errorModificarColonia","estadoModificarPerfil","municipioModificarPerfil","localidadModificarPerfil","codigoModificarPerfil");
                
}


function EditarModificacion(elemnt){
    $("#"+elemnt).removeAttr("readonly");
    $("#"+elemnt).focus();
    
    $("#ConfirmarModificacion").show();
}

/*Permite ingresar solo números*/
function OnlyNumber(value,elemnt){
    if( isNaN(value) ){
        elemnt.value = "";
    }
}

function obtenerEstado(codigo,error,estado,municipio,localidad,campo){
    
    $.ajax({
        type:"POST",
        url:"../ObtenerEstado",
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
        url:"../ObtenerMunicipio",
        data:"codigoPostal="+codigo,
        success: function(data){
            $("#"+municipio).html(data);
        }    
    });
                
    $.ajax({
        type:"POST",
        url:"../ObtenerLocalidad",
        data:"codigoPostal="+codigo,
        success: function(data){
            $("#"+localidad).html(data);
        }    
    });
    
}

function ConfirmarModificacion(){
    $("#tabs-2");
}