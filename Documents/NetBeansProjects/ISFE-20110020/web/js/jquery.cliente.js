/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 *Busqueda del cliente por su RFC
 */
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
 *Permite ingresar solo números
 */
function OnlyNumber(value,elemnt){
    if( isNaN(value) ){
        elemnt.value = "";
    }
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
 *Llenar estado, municipio y colonia
 */
function obtenerEstadoCliente(){
    var codigoPostal= $("#codigoPostalCliente").val();
    $("#ErrorCodigoPostalCliente").text("");
                                
    $.ajax({
        type:"POST",
        url:"../ObtenerEstado",
        data:"codigoPostal="+codigoPostal,
        success: function(datos){
            $("#estadoCliente").val(datos);
                        
            if(datos == ""){
                $("#ErrorCodigoPostalCliente").text("El código postal no existe");
                $("#codigoPostalCliente").val("");
                return false;
            }
           
        }
    });
                
    $.ajax({
        type:"POST",
        url:"../ObtenerMunicipio",
        data:"codigoPostal="+codigoPostal,
        success: function(data){
            $("#municipioCliente").html(data);
        }    
    });
                
    $.ajax({
        type:"POST",
        url:"../ObtenerLocalidad",
        data:"codigoPostal="+codigoPostal,
        success: function(data){
            $("#localidadCliente").html(data);
        }    
    });
                
    $("#referenciaCliente").focus();
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

