package servidorSAT.validarCadenaOriginal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase encargada de la Validacion de las cadenas originales a partir de expresiones regulares
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 *
 */
public class ValidaCadenas
{
	//aqui iran los campos de la cadena original
	String version="(2\\.0|3\\.0)";//r
	String serie="[a-zA-Z]+";
	String folio="[0-9]+";//r
	String fecha="20[0-9]{2}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}";//r
	String numAprobacion="[0-9]+";//r
	String anioAprobacion="20[0-9]{2}";//r
	String tipoComprobante="(INGRESO|EGRESO|TRASLADO|ingreso|egreso|traslado)";//r
	String formaPago="[a-zA-Z_0-9\\s]+";//r
	String condicionesPago="[a-zA-Z_0-9\\s]+";
	String subtotal="[0-9]+\\.[0-9]+";//r
	String descuento="[0-9]+\\.[0-9]+";
	String total="[0-9]+\\.[0-9]+";//r
	String metodoPago="[a-zA-Z_0-9\\s]+";
	String lugarExpedicion="[a-zA-Z_0-9\\s]+";
	String numCuentaDePago="[a-zA-Z_0-9\\s]+";
	String tipoCambio="[a-zA-Z_0-9\\s]+";
	String moneda="[a-zA-Z_0-9\\s]+";
	String folioFiscalOrigen="[0-9]+";
	String serieFolioFiscalOrigen="[0-9]+\\.[0-9]+";
	String fechaFolioFiscalOrigen="20[0-9]{2}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}";
	String montoComprobanteExpedido="[0-9]+\\.[0-9]+";
	String rfcEmisor="[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?";//r
	String nombreEmisor="[a-zA-Z_0-9\\s]+";//r
	String calleEmisor="[a-zA-Z_0-9\\s]+";//r
	String numExteriorEmisor="[0-9]+";
	String numInteriorEmisor="[a-zA-Z_0-9\\s]+";
	String coloniaEmisor="[a-zA-Z_0-9\\s]+";
	String localidadEmisor="[a-zA-Z_0-9\\s]+";
	String referenciaEmisor="[a-zA-Z_0-9\\s]+";
	String municipioEmisor="[a-zA-Z_0-9\\s]+";//r
	String estadoEmisor="[a-zA-Z_0-9\\s]+";//r
	String paisEmisor="[a-zA-Z_0-9\\s]+";//r
	String cpEmisor="[0-9]+";
	String calleComprobante="[a-zA-Z_0-9\\s]+";
	String numExteriorComprobante="[0-9]+";
	String numInteriorComprobante="[a-zA-Z_0-9\\s]+";
	String coloniaComprobante="[a-zA-Z_0-9\\s]+";
	String localidadComprobante="[a-zA-Z_0-9\\s]+";
	String referenciaComprobante="[a-zA-Z_0-9\\s]+";
	String municipioComprobante="[a-zA-Z_0-9\\s]+";
	String estadoComprobante="[a-zA-Z_0-9\\s]+";
	String paisComprobante="[a-zA-Z_0-9\\s]+";//r
	String cpComprobante="[0-9]+";
	String regimenFiscal="[a-zA-Z_0-9\\s]+";
	String rfcReceptor="[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?";//r
	String nombreReceptor="[a-zA-Z_0-9\\s]+";
	String calleReceptor="[a-zA-Z_0-9\\s]+";
	String numExteriorReceptor="[0-9]+";
	String numInteriorReceptor="[a-zA-Z_0-9\\s]+";
	String coloniaReceptor="[a-zA-Z_0-9\\s]+";
	String localidadReceptor="[a-zA-Z_0-9\\s]+";
	String referenciaReceptor="[a-zA-Z_0-9\\s]+";
	String municipioReceptor="[a-zA-Z_0-9\\s]+";
	String estadoReceptor="[a-zA-Z_0-9\\s]+";
	String paisReceptor="[a-zA-Z_0-9\\s]+";//r
	String cpReceptor="[0-9]+";
	//Estos pueden ir repetidos por cada concepto
	String cantidad="[0-9]+";//r
	String unidadMedida="[a-zA-Z_0-9\\s]+";
	String numIdentifiacion="[0-9]+";
	String descripcion="[a-zA-Z_0-9\\s]+";//r
	String valorUnitario="[0-9]+\\.[0-9]+";//r
	String importe="[0-9]+\\.[0-9]+";//r
	//Este sera para el concepto
	String concepto="(["+cantidad+"\\|"+descripcion+"\\|"+valorUnitario+"\\|"+importe+"]+)";
	//
	String numDocumentoAduanero="[a-zA-Z_0-9]+";
	String fechaExpedicionDocumentoAduanero="20[0-9]{2}-[0-9]{2}-[0-9]{2}";
	String aduana="[a-zA-Z_0-9]+";
	String numeroPredial="[a-zA-Z_0-9]+";
	//Esto se repetira para cada impuesto retenido
	String tipoImpuestoRetenido="[a-zA-Z_0-9\\s]+";
	String importeRetenido="[a-zA-Z_0-9]+";
	String totalImpuestosRetenidos="[a-zA-Z_0-9]+";
	//Esto se repetira para cada imupuesto trasladado
	String tipoImpuestoTrasladado="[a-zA-Z_0-9\\s]+";
	String tasa="[a-zA-Z_0-9]+";
	String importeTrasladado="[a-zA-Z_0-9]+";
	String totalImpuestosTrasladados="[a-zA-Z_0-9]+";
	//termian los campos de la cadena original

	public String regex="\\|\\|"+version+"\\|"+folio+"\\|"+fecha+"\\|"+numAprobacion+"\\|"+anioAprobacion+"\\|"+tipoComprobante+"\\|"+formaPago+"\\|"+subtotal+"\\|"+descuento+"\\|"+total+"\\|"+rfcEmisor+"\\|"+nombreEmisor+"\\|"+calleEmisor+"\\|"+municipioEmisor+"\\|"+estadoEmisor+"\\|"+paisEmisor+"\\|"+paisComprobante+"\\|"+rfcReceptor+"\\|"+nombreReceptor+"\\|"+calleReceptor+"\\|"+municipioReceptor+"\\|"+estadoReceptor+"\\|"+paisReceptor+"\\|"+concepto+"\\|\\|";

	Pattern patron = Pattern.compile(regex);
	Matcher match;

	@SuppressWarnings("unused")
	private Object cadenaOriginal;

	/**
	 * Metodo encargado de analizar la cadena original para su validacion
	 * @param cadenaOriginal parametro que se encarga de recibir la cadena original a analizar
	 * @return regresa un valor true en caso de que la cadena original coincida con la expresion
	 * regular establecida para la validacion de cadena original, regresara un valor false en caso
	 * contrario
	 */
	public boolean validaCadenaOriginal(Object cadenaOriginal)
	{
            this.cadenaOriginal=cadenaOriginal;
            cadenaOriginal.toString();
            System.out.println("Esta es la cadena original a analizar: \n"+cadenaOriginal);
            System.out.println("Esta es la expresion regular: \n"+Pattern.compile(regex));
            match=patron.matcher((CharSequence) cadenaOriginal);
            //Aqui comprobamos con la expesion regular la cadena Original
            if(match.matches())
            {
		return true;
            }
            else
            {
		return false;
            }
	}
}