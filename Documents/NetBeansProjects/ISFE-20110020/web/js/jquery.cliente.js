/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*Variables Globales*/
var nombre ,paterno, materno, razon, rfc , mail, calle , exterior , interior , colonia , municipio , referencia;
var idClienteEliminar = "";

/*Busqueda del cliente por su RFC*/
function ModificarCliente(){
    $("#ErrorRFCMoficiarCliente").text("");
    var rfcBuscar = $("#rfcClienteModificar").val().toUpperCase();
    if(rfcBuscar == ""){
        $("#ErrorRFCMoficiarCliente").text("Debes ingresar el R.F.C");
        return false;
    }else{
                    
        $("#ResultadoBusquedaCliente").html("");
        var aux = rfcBuscar.split("-");
        rfcBuscar = aux[0]+aux[1]+aux[2];
               
                
        $.ajax({
            type: "POST",
            url: "../Cliente",
            data: "Cliente=buscar&rfc="+rfcBuscar,
            success: function(data){
                $("#ResultadoModificarCliente").html(data);
            }
        });
    }
}
            
/*
 *Limpia el formulario de ingreso del cliente
 */
function Limpiar(){
    Tipo = "";
    $("#clienteMoral").attr("checked", false);
    $("#clienteFisica").attr("checked", false);
    $("#nombreCliente").val("");
    $("#paternoCliente").val("");
    $("#maternoCliente").val("");
    $("#razonCliente").val("");
    $("#RFCCliente").val("");
    $("#mailCliente").val("");
    $("#calleCliente").val("");
    $("#exteriorCliente").val("");
    $("#interiorCliente").val("");
    $("#codigoPostalCliente").val("");
    $("#estadoCliente").val("");
    $("#localidadCliente").html("");
    $("#municipioCliente").html("");
    $("#referenciaCliente").val("");
    $("#razonClient").hide();
    $("#NombreClient").hide();
    $("#PaternoClient").hide();
    $("#MaternoClient").hide();
    return false;
}

           
/*
 *Busca si existe el rfc
 */
function validarRFC(){
    var rfc = $("#RFCCliente").val().toUpperCase();
    var aux = rfc.split('-');
    rfc = aux[0]+aux[1]+aux[2];
    $("#indicacionesCliente").text("");
                
    $.ajax({
        type: "POST",
        url: "../Cliente",
        data: "Cliente=validar&rfc="+rfc,
        success: function(data){
            $("#indicacionesCliente").text(data);
        }
    });
}
                        
/*
 *Busca los datos del cliente para que se puedan actualizar
 */
function ActualizarCliente(idCliente){
    $("#formulario_busquedaModificacionCliente").hide();
    $("#rfcClienteModificar").val("");
    $("#ResultadoBusquedaCliente").html("");
    $("#formulario_actualizacionModficarCliente").show();
                
    $.ajax({
        type: "POST",
        url: "../Cliente",
        data: "Cliente=modificar&idCliente="+idCliente,
        success: function(data){
            $("#formulario_actualizacionModficarCliente").html(data);
        }
    });
}

/*
 *Regresar al formulario original de Modificación
 */
function regresarModificacion(){
    $("#formulario_busquedaModificacionCliente").show();
    $("#formulario_actualizacionModficarCliente").remove();
}


/*Mascara de validacion de RFC del cliente*/
jQuery(function(){
    $("#RFCCliente").mask("aaa*-999999-aa*");
    $("#rfcClienteModificar").mask("aaa*-999999-aa*");
    $("#rfcClienteEliminar").mask("aaa*-999999-aa*");
});
                
                
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

               
/*Evalua la opción si es Persona Fisica o moral*/
var Tipo="";
function ContribuyenteCliente(value){
       
    $("#razonClient").hide();
    $("#NombreClient").hide();
    $("#PaternoClient").hide();
    $("#MaternoClient").hide();
    $("#nombreCliente").val("");
    $("#paternoCliente").val("");
    $("#maternoCliente").val("");
    $("#razonCliente").val("");
    $("#ConfirmacionGuardarCliente").text("");
                
    if(value == "Moral"){
        $("#razonClient").show();
        Tipo = "Moral";
    }else{
        $("#NombreClient").show();
        $("#PaternoClient").show();
        $("#MaternoClient").show();
        Tipo = "Fisica";
    }
    
    return false;
}
            
/*Activar el dialogo de Eliminar Cliente*/
$(function() {
    // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
		
    var rfc = $( "#rfc" ),
    password = $( "#password" ),
    allFields = $( [] ).add( rfc ).add( password ),
    tips = $( ".validateTips" );
                
    $("#ConfirmarDatosCliente").dialog({
        autoOpen: false,
        height: 380,
        width: 750,
        modal: true,
        buttons: {
            "Aceptar": function() {
                $.ajax({
                    type:"POST",
                    url:"../Cliente",
                    data:"Cliente=guardar&tipoPersona="+Tipo+"&Nombre="+nombre+"&Paterno="+paterno+"&Materno="+materno+"&Razon="+razon+"&RFCCliente="+
                    rfc+"&Mail="+mail+"&Calle="+calle+"&Interior="+interior+"&Exterior="+exterior+"&Colonia="+colonia+"&Municipio="+
                    municipio+"&Referencia="+referencia,
                    success: function(data){
                        $("#ConfirmacionGuardarCliente").text(data);
                        Limpiar();
                    }
                });
                $( this ).dialog( "close" );     
            },
            Cancel: function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        } 
    });
		
		
    $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Eliminar": function() {
                alert("Eliminando");
                                        
            },
            Cancel: function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });

    /*Funcion para activar el dialogo*/
    $( "#EliminarCliente" ).click(function() {
        $("#ErrorEliminarCliente").text("");
        var rfc = $("#rfcClienteEliminar").val().toUpperCase();
        if(rfc == ""){
            $("#ErrorEliminarCliente").text("Debes ingresar el R.F.C.");
        }else{
            $("#rfcErrorMensaje").text(rfc);
            var aux = rfc.split("-");
            rfc = aux[0]+aux[1]+aux[2];
                        
            $.ajax({
                type: "POST",
                url: "../Cliente",
                data: "Cliente=baja&rfc="+rfc,
                success: function(data){
                    var aux = data.split("/");
                    idClienteEliminar = aux[0];
                    $("#nombreErrorMensaje").text(aux[1]);
                }
            });
            $( "#dialog-form" ).dialog( "open" );
        }
    });
                        
                
});

function BuscarCodigo(){
    var codigoPostal= $("#codigoPostalCliente").val();
    $("#ErrorCodigoPostalCliente").text("");
    obtenerEstado(codigoPostal,"ErrorCodigoPostal","estadoCliente","municipioCliente","localidadCliente","codigoPostalCliente","../");
}