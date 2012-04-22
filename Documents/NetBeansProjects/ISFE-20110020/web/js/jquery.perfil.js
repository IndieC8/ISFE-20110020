/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function EditarModificacion(elemnt){
    $("#"+elemnt).removeAttr("readonly");
    $("#"+elemnt).focus();
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