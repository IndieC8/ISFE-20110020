/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*Variables Globales*/
var nombre ,paterno, materno, rfcCliente, razon, mail, calle , exterior , interior , colonia , municipio , referencia;
var idClienteEliminar = "";
var Actualizacion = "Nuevo";
var idClienteActualizar = "";


/*Busqueda del cliente por su RFC*/
function ModificarCliente(){
    $("#ErrorRFCMoficiarCliente").text("");
    BorrarErrorBaja();
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
                if(data == 0){
                    $("#ErrorRFCMoficiarCliente").text("No existe tu cliente");
                    $("#rfcClienteModificar").val("");
                }else{
                    $("#ResultadoModificarCliente").html(data);
                    
                }
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
            var aux = data.split("**");
            Tipo = aux[0];
            $("#formulario_actualizacionModficarCliente").html(aux[1]);
            $("#formulario_actualizacionModficarCliente").show();
            Actualizacion = "actualizar";
        }
    });
    return false;
}

/*
 *Regresar al formulario original de Modificación
 */
function regresarModificacion(){
    $("#formulario_busquedaModificacionCliente").show();
    $("#formulario_actualizacionModficarCliente").html("");
    return false;
}


/*Mascara de validacion de RFC del cliente*/
jQuery(function(){
    $("#RFCCliente").mask("aaa*-999999-aa*");
    $("#rfcClienteModificar").mask("aaa*-999999-aa*");
    $("#rfcClienteEliminar").mask("aaa*-999999-aa*");
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
    
    BorrarErrorBaja();
    return false;
}
            
/*Activar el dialogo de Eliminar Cliente*/
$(function() {
    // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
		
    $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Eliminar": function() {
                $.ajax({
                    type: "POST",
                    url: "../Cliente",
                    data: "Cliente=eliminar&idCliente="+idClienteEliminar,
                    success: function(data){
                        $("#ConfirmacionEliminarCliente").text("Tu cliente se ha eliminado!");
                        $("#rfcClienteEliminar").val("");
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
            
            if(idClienteEliminar == 'null' || idClienteEliminar == ""){
                $("#ErrorEliminarCliente").text("No existe tu cliente");
                $("#rfcClienteEliminar").val("");
            }else{
                $( "#dialog-form" ).dialog( "open" );
            }
        }
    });
                        
                
});

function BorrarErrorBaja(){
    $("#ErrorEliminarCliente").text("");
    $("#ConfirmacionEliminarCliente").text("");
    $("#ResultadoModificarCliente").text("");
    $("#ErrorRFCMoficiarCliente").text("");
    $("#ConfirmacionModificarCliente").text("");
}

function BuscarCodigo(){
    
    if(Actualizacion == "actualizar"){
        var codigo = $("#codigoClienteModificar").val();
        $("#ErrorCodigoPostalModificarCliente").text("");
        obtenerEstado(codigo,"ErrorCodigoPostalModificarCliente","EstadoClienteModificar","delegacionClienteModificar","localidadClienteModificar","codigoClienteModificar","../");
    }else{
        var codigoPostal= $("#codigoPostalCliente").val();
       
        $("#ErrorCodigoPostalCliente").text("");
        obtenerEstado(codigoPostal,"ErrorCodigoPostalCliente","estadoCliente","municipioCliente","localidadCliente","codigoPostalCliente","../");
    }
    
}