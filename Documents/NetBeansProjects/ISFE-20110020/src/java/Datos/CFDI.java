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
 * Clase anidada encargada de generar el espacio de nombres del cfdi del xml
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
                    .setAttribute("subTotal", XML.codificarNumero(factura.getSubTotal()) + "")
                    .setAttribute("total", XML.codificarNumero(factura.getTotal()) + "")
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
        emisorXML.addContent(obtenerDireccionFiscal());
        emisorXML.addContent(generarExpedidoEn());
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
            receptorXML.addContent(generarDomicilio());
            return receptorXML;
        }
        private Element generarDomicilio() {
            Element domicilio = new Element("Domicilio", cfdi);
            generarDireccionFiscal(domicilio, receptor.getDireccion());
            return domicilio;
        }
        /**
         * Genera el elemento del xml con los datos de los impuestos del cfdi
         * @return elemento con los impuestos
         */
        public Element generarImpuestos(){
            return new Element("Impuestos", cfdi)
                    .addContent(generarTraslados());
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
         * Genera el elemento delxml con el domicilio fiscal del emisor dentro del cfdi
         * @return elemento del domicilio fiscal del emisor
         */
        public Element generarDomicilioFiscalEmisor(){
            Element domicilioFiscal = new Element("DomicilioFiscal", cfdi);
            generarDireccionFiscal(domicilioFiscal, emisor.getDireccion());
            return domicilioFiscal;
        }
        /**
         * Genera el elemento del xml con los datos del lugar de expedición dentro del cfdi
         * @return elemeto del lugar de expedición.
         */
        public Element generarExpedidoEn(){
            Element expedidoEn = new Element("ExpedidoEn", cfdi);
            generarDireccionFiscal(expedidoEn, factura.getExpedidoEn());
            return expedidoEn;
        }
        /**
         * Genera el elemento con los datos del domicilio de receptor dentro del
         * espacio de nombre del cfdi
         * @return elemento con el domicilio fiscal del receptor
         */
        public Element generarDomicilioReceptor(){
            Element domicilio = new Element("Domicilio", cfdi);
            generarDireccionFiscal(domicilio, receptor.getDireccion());
            return domicilio;
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
            if (concepto.getNoIdentificacion() != null) {
		concepto_el.setAttribute("noIdentificacion", XML.codificarCadena(concepto.getNoIdentificacion()));
            }
            return concepto_el.setAttribute("descripcion", XML.codificarCadena(concepto.getDescripcion()))
                .setAttribute("valorUnitario", XML.codificarNumero(concepto.getValorUnitario()) + "")
		.setAttribute("importe", XML.codificarNumero(concepto.getImporte()) + "");
        }
        /**
         * Genera el elemento del xml dentro del cfdi con la información de los 
         * traslados de la factura
         * @return elemento con los traslados
         */
        public Element generarTraslados(){
            return new Element("Traslados", cfdi)
                        .addContent(generarTraslado());
        }
        /**
         * Método que genera el traslado de la factura electrónica
         * @return El traslado generado a apartir del IVA
         */
        private Element generarTraslado() {
            return new Element("Traslado", cfdi)
                    .setAttribute("impuesto", "IVA")
                    .setAttribute("tasa", XML.codificarNumero(factura.getTasa()) + "")
                    .setAttribute("importe", XML.codificarNumero(factura.getImporteTasa()) + "");
	}
        /**
         * Método que genera la ubicación o dirección fiscal para los emisores, receptores y el lugar de expedición
         * @param element Nodo del árbol del cual se desea generar la dirección Fiscal (ubicación)
         * @param DireccionFiscal 
         */
        private void generarDireccionFiscal(Element element, Direccion DireccionFiscal) {
		element.setAttribute("calle", XML.codificarCadena(DireccionFiscal.getCalle()));
		if (DireccionFiscal.getNoExterior() != null && !DireccionFiscal.getNoExterior().isEmpty()) {
			element.setAttribute("noExterior", XML.codificarCadena(DireccionFiscal.getNoExterior()));
		}
		if (DireccionFiscal.getNoInterior() != null && !DireccionFiscal.getNoInterior().isEmpty()) {
			element.setAttribute("noInterior", XML.codificarCadena(DireccionFiscal.getNoInterior()));
		}
		if (DireccionFiscal.getColonia() != null && !DireccionFiscal.getColonia().isEmpty()) {
			element.setAttribute("colonia", XML.codificarCadena(DireccionFiscal.getColonia()));
		}
		if (DireccionFiscal.getLocalidad() != null && !DireccionFiscal.getLocalidad().isEmpty()) {
			element.setAttribute("localidad", XML.codificarCadena(DireccionFiscal.getLocalidad()));
		}
		if (DireccionFiscal.getReferencia() != null && !DireccionFiscal.getReferencia().isEmpty()) {
			element.setAttribute("referencia", XML.codificarCadena(DireccionFiscal.getReferencia()));
		}
		element.setAttribute("municipio", XML.codificarCadena(DireccionFiscal.getMunicipio()));
		element.setAttribute("estado", XML.codificarCadena(DireccionFiscal.getEstado()));
		element.setAttribute("pais", XML.codificarCadena("MÉXICO"));
		element.setAttribute("codigoPostal", DireccionFiscal.getCodigoPostal());
	}
        /**
         * Método encargado de generar el elemento del árbol del CFDI que maneja
         * la información del domicilio/dirección fiscal del emisor
         * @return La direccion/domicilio fiscal del emisor
         */
        private Element obtenerDireccionFiscal() {
            Element domicilioFiscal = new Element("DomicilioFiscal", cfdi);
            generarDireccionFiscal(domicilioFiscal, emisor.getDireccion());
            return domicilioFiscal;
        }
    }
