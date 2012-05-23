/*Variables Globales*/
var col;
var Total = 0;
var Iva = 0;
var Sub = 0;
var id = 0;

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

});

$(function() {
    // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    
    $("#dialogCancelar").dialog({
        autoOpen: false,
        height: 205,
        width: 330,
        modal: true,
        buttons: {
            "Aceptar": function() {
                $.ajax({
                    url: "../Mensuales",
                    type: "POST",
                    data: "Factura=Cancelar&idFolio="+id,
                    success: function(){
                        BuscarCancelacion();
                    }
                });
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
    
    $("#confirmacionFactura").dialog({
        autoOpen: false,
        height: 400,
        width: 650,
        modal: true,
        buttons: {
            "Aceptar": function() {
                var cantidad = $("#tbDetalleCantidad_0").val();
                var nombre = $("#tbDetalleNombre_0").val();
                var unitario = $("#tbDetalleImporte_0").val();
                var totalProducto = $("#tbDetalleTotal_0").val();
                var descripcion = $("#tbDetalleDescripcion_0").val();
                GenerarXML(col,cantidad,nombre,unitario,totalProducto,descripcion,Sub,Iva,Total);
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
            
function addComas(nStr,elemnt){
    nStr = Math.round(nStr*100)/100;
    nStr += '';
    x = nStr.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    nStr =  x1 + x2;

    elemnt.value = nStr;

}
            
function ValidarSuma(){
                
    var cantidad = $("#cantidadFactura").val();
    var descuento = $("#descuentoFactura").val();

                
    if(cantidad == "0"){
        $("#errorCantidadFactura").text("Indica la cantidad del producto");
    }

    if(Importe != ""){
        var Total = Importe * cantidad;
        descuento = descuento / 100;
        descuento = Total * descuento;
        Total = Total - descuento;
        var iva =  Total * .16;
        var granTotal = Total + iva;
        addComas(Total,document.getElementById("importeTotal"));
        addComas(Importe,document.getElementById("valorUnitario"));
        addComas(iva,document.getElementById("IVATotal"));
        addComas(granTotal,document.getElementById("GranTotal"));
                    
        SubTotal = Total;
        Iva = iva;
        ImporteTotal = granTotal;

    }

}

var Importe = 0;
var SubTotal = 0;
var Iva = 0;
var ImporteTotal = 0;

function OnlyNumber(value,elemnt){
    if( isNaN(value) ){
        elemnt.value = "";
    }

    if(elemnt.id == "valorUnitario"){
        Importe = value;
    }
}

function BorrarError(id){
    if(id == "unidadProducto"){
        $("#errorUnidadProducto").text("");
    }else if(id == "nombreProducto"){
        $("#errorNombreProducto").text("");
    }else if(id == "descripcionProducto"){
        $("#errorDescripcionProducto").text("");
    }else if(id == "valorUnitario"){
        $("#errorValorProducto").text("");
    }else{
        $("#errorCantidadFactura").text("");
        $("#mensajeConfirmacion").text("");
        ValidarSuma();
    }
}

function borrarProducto(){
    $("#cantidadFactura").val("0");
    $("#nombreProducto").val("");
    $("#descripcionProducto").val("");
    $("#unidadProducto").val("0");
    $("#valorUnitario").val("");
    $("#importeTotal").val("");
    $("#IVATotal").val("");
    $("#GranTotal").val("");
    $("#descuentoFactura").val("0");
    $("#idProductoFactura").val("0");
    Importe = 0;
    return false;
}

function agregarFila(){
    var oId = parseInt($("#cant_campos").val() );
    $("#cant_campos").val( oId + 1);

    var cantidad = $("#cantidadFactura").val();
    var nombre = $("#nombreProducto").val().toUpperCase();
    var descripcion = $("#descripcionProducto").val().toUpperCase();
    var unidad = $("#unidadProducto").val();
    var unitario = $("#valorUnitario").val();
    var descuento = $("#descuentoFactura").val();
    descuento = descuento / 100;
    var importe = $("#importeTotal").val();
    var iva = $("#IVATotal").val();
    var total = $("#GranTotal").val();

                
    var strHtml1 = "<td class=\"TablaTitulo\">" + cantidad + "<input type=\"hidden\" id=\"tbDetalleCantidad_" + oId + "\" value=\" " + cantidad + "\"/></td>";
    var strHtml2 = "<td class=\"TablaTitulo\">" + nombre + "<input type=\"hidden\" id=\"tbDetalleNombre_" + oId + "\" value=\" " + nombre + "\"/></td>";
    var strHtml3 = "<td class=\"TablaTitulo\"> $ " + unitario + "<input type=\"hidden\" id=\"tbDetalleImporte_" + oId + "\" value=\" " + Importe + "\"/></td>";
    var strHtml4 = "<td class=\"TablaTitulo\"> $ " + importe + "<input type=\"hidden\" id=\"tbDetalleSubtotal_" + oId + "\" value=\" " + SubTotal + "\"/></td>";
    var strHtml5 = "<td class=\"TablaTitulo\"> $ " + iva + "<input type=\"hidden\" id=\"tbDetalleIVA_" + oId + "\" value=\" " + Iva + "\"/></td>";
    var strHtml6 = "<td class=\"TablaTitulo\"> $ " + total + "<input type=\"hidden\" id=\"tbDetalleTotal_" + oId + "\" value=\" " + ImporteTotal + "\"/></td>";

    var strHtml7 = '<td class=\"TablaTitulo\"><img src=\"../images/formularios/delete.png\" width=\"16\" height=\"16\" alt=\"Eliminar\" style=\"cursor:pointer\" onclick=\"eliminarFila('+ oId +')\" />';
    strHtml7 += '<input type="hidden" id="hdnIdCampos_' + oId +'" name="hdnIdCampos[]" value="' + oId + '" />';
    strHtml7 += '<input type="hidden" id="tbDetalleDescripcion_'+ oId + '" value = "'+ descripcion + '"/>';
    strHtml7 += '<input type="hidden" id="tbDetalleUnidad_'+ oId + '" value = "'+ unidad + '"/>';
    strHtml7 += '<input type="hidden" id="tbDetalleDescuento_'+ oId + '" value = "'+ descuento + '"/></td>';

    var strHtmlTr = "<tr id='rowDetalle_" + oId + "'></tr>";
    var strHtmlFinal = strHtml1 + strHtml2 +strHtml3 + strHtml4 + strHtml5 + strHtml6 + strHtml7;

    $("#tbDetalleProducto").append(strHtmlTr);

    $("#rowDetalle_" + oId).html(strHtmlFinal);

}

/** Eliminar Productos que no deseo **/
function eliminarFila(oId){
    $("#rowDetalle_" + oId).remove();
    return false;
}

function AgregarProducto(){
    var cantidad = $("#cantidadFactura").val();
    var nombre = $("#nombreProducto").val().toUpperCase();
    var descripcion = $("#descripcionProducto").val().toUpperCase();
    var unidad = $("#unidadProducto").val();
    var valor = $("#valorUnitario").val();
    var subTotal = $("#importeTotal").val();
    var iva = $("#IVATotal").val();
    var Total = $("#GranTotal").val();
    var descuento = $("#descuentoFactura").val();
                
    if(descuento == ""){
        descuento  = 0;   
    }

    if( cantidad == "0"){
        $("#errorCantidadFactura").text("Indica la cantidad del producto");
        return false;
    }else if( unidad == "0"){
        $("#errorUnidadProducto").text("Indica la unidad del producto");
        return false;
    }else if( nombre == ""){
        $("#errorNombreProducto").text("Indica el nombre del producto");
        return false;
    }else if(descripcion == ""){
        $("#errorDescripcionProducto").text("Indica la descripción de tu producto");
        return false;
    }else if(valor == ""){
        $("#errorValorProducto").text("Indica el valor unitario");
        return false;
    }else{
        agregarFila();
        if($("#idProductoFactura").val() == "0"){
            NuevoProducto(nombre,descripcion,unidad,Importe);
        }
        $.ajax({
            url: "../Producto", 
            type: "POST",
            data: "Producto=Actualizar&nombreProducto="+nombre+"&descripcion="+descripcion+"&unidad="+unidad+"&valorUnitario="+Importe+"&idProducto="+$("#idProductoFactura").val()
        });
        borrarProducto();
        $("#mensajeConfirmacion").text("Producto Agregado");
        $("#confirmacion").show();

    }
    return false;
}

function fill(nombreProducto,descripcionProducto,unidadProducto,valorUnitario,id) {
                 
    if(nombreProducto != undefined){
        $('#nombreProducto').val(nombreProducto);
        $('#descripcionProducto').val(descripcionProducto);
        $('#unidadProducto').val(unidadProducto);
        $("#valorUnitario").val(valorUnitario);
        Importe = valorUnitario;
        ValidarSuma();
        $("#idProductoFactura").val(id);
    }else{
        $('#descripcionProducto').val("");
        $('#unidadProducto').val("");
        $("#valorUnitario").val("");
        Importe = 0;
    }
                
    /*Validar que existe el importe*/
    if(Importe == undefined || Importe == 0){
        $("#importeTotal").val("");
        $("#valorUnitario").val("");
        $("#IVATotal").val("");
        $("#GranTotal").val("");
    }
                
    setTimeout("$('#suggestions').hide();", 200);    
}

function GenerarFactura(){
    
    col = parseInt($("#cant_campos").val() );
    Total = 0;
    Iva = 0;
    Sub = 0;
    
    for(i=0; i<col;i++){
        Total = Total + parseFloat($("#tbDetalleTotal_"+i).val());
        Iva = Iva + parseFloat($("#tbDetalleIVA_"+i).val());
        Sub = Sub + parseFloat($("#tbDetalleSubtotal_"+i).val());
    }
    
       
    addComas(Total,document.getElementById("auxConfirmacion"));
    $("#confirmarTotalFactura").text("$ "+$("#auxConfirmacion").val());
    $("#auxConfirmacion").val("");
    
    addComas(Iva,document.getElementById("auxConfirmacion"));
    $("#confirmarIVAFactura").text("$ "+$("#auxConfirmacion").val());
    $("#auxConfirmacion").val("");
    
    addComas(Sub,document.getElementById("auxConfirmacion"));
    $("#confirmarSubTotalFactura").text("$ "+$("#auxConfirmacion").val());
        
    $("#confirmacionFactura").dialog("open"); 
}

$(function() {
    $( "#datepicker" ).datepicker();
                
    $( "#format" ).change(function() {
        $( "#datepicker" ).datepicker( "option", "dateFormat", $( this ).val() );
    });
                
    $("#cancelar").datepicker();
    $( "#format" ).change(function() {
        $( "#cancelar" ).datepicker( "option", "dateFormat", $( this ).val() );
    });
    
});
            
function Cancelar(idFolio){
    var nombre =  $("#Nombre_"+idFolio).text();
    $("#instruccionCancelacion").text(nombre+".xml");
    id = idFolio;
    $("#dialogCancelar").dialog("open");
}
            
function Limpiar(){
    $("#ErrorFechaElaboracion").text("");
    $("#ErrorFechaCancelar").text("");
}

function Buscar(){
    $("#ErrorFechaElaboracion").text("");
    var fecha = $("#datepicker").val();
    if(fecha != ""){
        var aux = fecha.split("/");
        fecha = aux[2]+aux[1]+aux[0];
        AJAXBuscar(fecha);
    }else{
        $("#ErrorFechaElaboracion").text("Ingresa la fecha de elaboración");
    }
}

function BuscarCancelacion(){
    $("#ErrorFechaCancelar").text("");
                
    var Fecha = new Date();
    var fecha = $("#cancelar").val();
    if(fecha != ""){
        var aux =  fecha.split("/");
                
        if(aux[1].length == 2){
            var x = aux[1].split("");
            var mes = x[1];
        }
                
        if( (Fecha.getMonth() + 1) != mes ){
            $("#ErrorFechaCancelar").text("Solo días de este mes!");
        }else{
            fecha = aux[2]+aux[1]+aux[0];
            AJAXCancelar();
        }
    }else{
        $("#ErrorFechaCancelar").text("Ingresa la fecha de Elaboración!");
    }
}
