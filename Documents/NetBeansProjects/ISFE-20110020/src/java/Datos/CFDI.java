/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.util.ArrayList;
import java.util.Date;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 * Clase encargada de generar el espacio de nombres del cfdi del xml
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class CFDI{
    private Document xml;
    private Usuario emisor;
    private Factura factura;
    private Contribuyente receptor;
    private ArrayList<Concepto> conceptos;
    private final static String version = "3.0";
    private final static Namespace cfdi = Namespace.getNamespace("cfdi", "http://www.sat.gob.mx/cfd/3");
    private final static Namespace xsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    private final static String schemaLocation = "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv3.xsd";
    /**
     * Constructtor del CFDI
     * @param f factura electrónica
     */
    public CFDI(Factura f) {
        xml = new Document();
        this.factura = f;
        emisor = f.getEmisor();
        receptor = f.getReceptor();
        conceptos = f.getConceptos();
    }
    /**
     * Se encarga de generar el espcacio de nombre del cfdi
     * @return espacio de nombres cfdi con los datos.
     */
    public Document generarCFDI(){
        xml.setRootElement(generarComprobante());
        return xml;
    }
    /**
     * Método encargado de generar el árbol del espacio de nombres para el CFDI
     * @return árbol del CFDI para el XML
     */
    private Element generarComprobante() {
        Element comprobante = new Element("Comprobante", cfdi);
        comprobante.addNamespaceDeclaration(xsi);
        comprobante.setAttribute("schemaLocation", schemaLocation, xsi)
                    .setAttribute("version", version);
        if (factura.getFolio() != null) {
                comprobante.setAttribute("folio", factura.getFolio().getNoFolio() + "");
        }
        comprobante.setAttribute("fecha", XML.formatearFecha(new Date()))
                    .setAttribute("sello", "selloCFD")
                    .setAttribute("formaDePago", factura.getFormaDePago())
                    .setAttribute("noCertificado", emisor.getCSD().getNoCertificado())
                    .setAttribute("certificado", XML.codificarBase64(emisor.getCSD().getArchivoCSD()))
                    .setAttribute("subTotal", XML.codificarNumero(factura.getSubTotal()) + "");
                if(factura.getDescuento()!=0)
                    comprobante.setAttribute("descuento", XML.codificarNumero(factura.getDescuento()) + "");
                comprobante.setAttribute("total", XML.codificarNumero(factura.getTotal()) + "")
                    .setAttribute("metodoDePago", factura.getMetodoDePago())
                    .setAttribute("tipoDeComprobante", factura.getTipoDeComprobante())
                    .addContent(generarEmisor())
                    .addContent(generarReceptor())
                    .addContent(generarConceptos())
                    .addContent(generarImpuestos())
                    .addContent(generarComplemento());
         return comprobante;
    }
    /**
     * Genera el elemento del xml con los datos del emisor (usuario) del cfdi
     * @return elemento del emisor
     */
    public Element generarEmisor(){
        Element emisorXML = new Element("Emisor", cfdi);
        emisorXML.setAttribute("rfc", emisor.getRFC());
        emisorXML.setAttribute("nombre", XML.codificarCadena(emisor.getNombre()));
        //DOMICILIO DEL EMISOR
        Element domicilioFiscal = new Element("DomicilioFiscal", cfdi);
        domicilioFiscal.setAttribute("calle", XML.codificarCadena(emisor.getDireccion().getCalle()));
        if (emisor.getDireccion().getNoExterior() != null) {
		domicilioFiscal.setAttribute("noExterior", XML.codificarCadena(emisor.getDireccion().getNoExterior()));
	}
	if (emisor.getDireccion().getNoInterior() != null) {
		domicilioFiscal.setAttribute("noInterior", XML.codificarCadena(emisor.getDireccion().getNoInterior()));
	}
	if (emisor.getDireccion().getColonia() != null) {
		domicilioFiscal.setAttribute("colonia", XML.codificarCadena(emisor.getDireccion().getColonia()));
	}
	if (emisor.getDireccion().getLocalidad() != null) {
		domicilioFiscal.setAttribute("localidad", XML.codificarCadena(emisor.getDireccion().getLocalidad()));
	}
	if (emisor.getDireccion().getReferencia() != null) {
		domicilioFiscal.setAttribute("referencia", XML.codificarCadena(emisor.getDireccion().getReferencia()));
	}
	domicilioFiscal.setAttribute("municipio", XML.codificarCadena(emisor.getDireccion().getMunicipio()));
	domicilioFiscal.setAttribute("estado", XML.codificarCadena(emisor.getDireccion().getEstado()));
	domicilioFiscal.setAttribute("pais", XML.codificarCadena("MÉXICO"));
	domicilioFiscal.setAttribute("codigoPostal", emisor.getDireccion().getCodigoPostal());
        emisorXML.addContent(domicilioFiscal);
        //DOMICILIO DEL LUGAR DE EXPEDICION
        Element expedidoEn = new Element("ExpedidoEn", cfdi);
        expedidoEn.setAttribute("calle", XML.codificarCadena(factura.getExpedidoEn().getCalle()));
        if (factura.getExpedidoEn().getNoExterior() != null) {
		expedidoEn.setAttribute("noExterior", XML.codificarCadena(factura.getExpedidoEn().getNoExterior()));
	}
	if (factura.getExpedidoEn().getNoInterior() != null) {
		expedidoEn.setAttribute("noInterior", XML.codificarCadena(factura.getExpedidoEn().getNoInterior()));
	}
	if (factura.getExpedidoEn().getColonia() != null) {
		expedidoEn.setAttribute("colonia", XML.codificarCadena(factura.getExpedidoEn().getColonia()));
	}
	if (factura.getExpedidoEn().getLocalidad() != null) {
		expedidoEn.setAttribute("localidad", XML.codificarCadena(factura.getExpedidoEn().getLocalidad()));
	}
	if (factura.getExpedidoEn().getReferencia() != null) {
		expedidoEn.setAttribute("referencia", XML.codificarCadena(factura.getExpedidoEn().getReferencia()));
	}
	expedidoEn.setAttribute("municipio", XML.codificarCadena(factura.getExpedidoEn().getMunicipio()));
	expedidoEn.setAttribute("estado", XML.codificarCadena(factura.getExpedidoEn().getEstado()));
	expedidoEn.setAttribute("pais", XML.codificarCadena("MÉXICO"));
	expedidoEn.setAttribute("codigoPostal", factura.getExpedidoEn().getCodigoPostal());
        emisorXML.addContent(expedidoEn);
        return emisorXML;
    }
    /**
     * Genera el elemento del xml con los datos del receptor (cliente) del cfdi
     * @return elemento del receptor
     */
    public Element generarReceptor(){
        Element receptorXML = new Element("Receptor", cfdi);
        receptorXML.setAttribute("rfc", receptor.getRFC());
        receptorXML.setAttribute("nombre", XML.codificarCadena(receptor.getNombre()));
        //DOMICILIO DEL RECEPTOR
        Element domicilio = new Element("Domicilio", cfdi);
        domicilio.setAttribute("calle", XML.codificarCadena(receptor.getDireccion().getCalle()));
        if (receptor.getDireccion().getNoExterior() != null) {
		domicilio.setAttribute("noExterior", XML.codificarCadena(receptor.getDireccion().getNoExterior()));
        }
        if (receptor.getDireccion().getNoInterior() != null) {
		domicilio.setAttribute("noInterior", XML.codificarCadena(receptor.getDireccion().getNoInterior()));
        }
        if (receptor.getDireccion().getColonia() != null) {
		domicilio.setAttribute("colonia", XML.codificarCadena(receptor.getDireccion().getColonia()));
        }
        if (receptor.getDireccion().getLocalidad() != null) {
		domicilio.setAttribute("localidad", XML.codificarCadena(receptor.getDireccion().getLocalidad()));
        }
        if (receptor.getDireccion().getReferencia() != null) {
		domicilio.setAttribute("referencia", XML.codificarCadena(receptor.getDireccion().getReferencia()));
        }
        domicilio.setAttribute("municipio", XML.codificarCadena(receptor.getDireccion().getMunicipio()));
        domicilio.setAttribute("estado", XML.codificarCadena(receptor.getDireccion().getEstado()));
        domicilio.setAttribute("pais", XML.codificarCadena("MÉXICO"));
        domicilio.setAttribute("codigoPostal", receptor.getDireccion().getCodigoPostal());
        receptorXML.addContent(domicilio);
        return receptorXML;
        }
        /**
         * Genera el elemento del xml con los datos de los impuestos del cfdi
         * @return elemento con los impuestos
         */
        public Element generarImpuestos(){
            Element impuestos=new Element("Impuestos", cfdi);
            //TRASLADO
            Element traslado=new Element("Traslado", cfdi)
                    .setAttribute("impuesto", "IVA")
                    .setAttribute("tasa", XML.codificarNumero(factura.getTasa()) + "")
                    .setAttribute("importe", XML.codificarNumero(factura.getImporteTasa()) + "");
            //TRASLADOS
            Element traslados=new Element("Traslados", cfdi);
            traslados.addContent(traslado);
            //IMPUESTOS
            impuestos.addContent(traslados);
            return impuestos;
        }
        /**
         * Genera el complemento del xml del cfdi
         * @return elemento con el complemento
         */
        public Element generarComplemento(){
            Element complemento = new Element("Complemento", cfdi);
            return complemento;
        }
        /**
         * Genera un xml con todos los datos ya previamente establecidos pero
         * agregando el sello de la firma digital del emisor (usuario)
         * @param sello de la firma digital
         * @return xml sellado
         */
        public Document agregarSello(String sello){
            xml.getRootElement().setAttribute("sello", sello);
            return xml;
        }
        /**
         * Genera un nuevo xml con los datos del xml generado pero agregando el
         * timbre fiscal digital al mismo.
         * @param timbre espacio de nombre con los datos del timbre fiscal digital
         * @return xml con el timbre fiscal digital.
         */
        public Document agregarTimbre(Content timbre) {
            xml.getRootElement().getChild("Complemento", cfdi).addContent(timbre);
            return xml;
	}
        /**
         * Genera el elemeto con los datos de los conceptos dentro del cfdi
         * @return elemento con los conceptos
         */
        public Element generarConceptos(){
            Element conceptosXML = new Element("Conceptos", cfdi);
            for (int i = 0; i < conceptos.size(); i++) {
                conceptosXML.addContent(generarConcepto(conceptos.get(i)));
            }
            return conceptosXML;
        }
        /**
         * Genera el elemento de cada concepto del cfdi
         * @param concepto de la factura
         * @return elemeto con el concepto
         */
        public Element generarConcepto(Concepto concepto){
            Element concepto_el = new Element("Concepto", cfdi)
                .setAttribute("cantidad", concepto.getCantidad() + "");
            if (concepto.getUnidad() != null) {
		concepto_el.setAttribute("unidad", XML.codificarCadena(concepto.getUnidad()));
            }
            return concepto_el.setAttribute("descripcion", XML.codificarCadena(concepto.getDescripcion()))
                .setAttribute("valorUnitario", XML.codificarNumero(concepto.getValorUnitario()) + "")
		.setAttribute("importe", XML.codificarNumero(concepto.getImporte()) + "");
        }
    }
