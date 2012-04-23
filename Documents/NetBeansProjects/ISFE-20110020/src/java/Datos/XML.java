package Datos;

import Negocios.Cifrado.Cifrado;
import Negocios.GenerarFactura.CadenaOriginal;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.fop.apps.FOPException;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Clase que representa y genera el formato XML para la factura electrónica
 * @author Raul Hernandez
 */
public class XML extends Formato{
    /**
     * Cosntructor vacío
     */
    public XML(){
    }
    /**
     * Método encargado de generar el xml de la factura electrónica
     * @param f factura con todos los datos necesarios para crear el XML
     * @param cadenaOriginal generada a partir de la Factura
     * @param cadenaTimbre generada previamente
     * @param isfe
     * @return el xml de la factura electrónica
     * @throws SecurityException
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws NoSuchProviderException
     */
    public Document generarXML(Factura f,ISFE isfe)throws SecurityException, UnsupportedEncodingException,IOException, NoSuchProviderException{
        /**
         * Se crea la estructura del XML correspondiente con el espacio de
         * nombre cfdi (CertificadoFiscalDigitalporInternet) con los datos
         * contenidos en la factura
         */
        CFDI cfdi=new CFDI(f);
        Document xml=cfdi.generarCFDI();
        /**
         * Se crea la cadena original en base al xlst proporcionado por el SAT
         */
        String cadenaOriginal=CadenaOriginal.generarCadenaOriginal("cadOriginalCFDI_3.xslt",xml);
        /**
         * Se obtiene la llave privada de la FIEL del usuario (emisor) que esta
         * cifrada con el algoritmo RSA
         */
        PrivateKey llaveFIEL=Cifrado.getLlavePrivada(f.getFiel().getArchivoFiel(), f.getFiel().getPassword());
        /**
         * Se crea el sello del CFDI con la llave privada y la cadena original
         * de la factura electrónica y se agrega al xml
         */
        String sello=Cifrado.firmar(llaveFIEL, cadenaOriginal.getBytes("UTF-8"));
        Cifrado.eliminarLlavePrivada(llaveFIEL);
        cfdi.agregarSello(sello);
        /**
         * Se crea la extructura del XML que corresponde al espacio de nombres
         * tfd (TimbreFiscalDigital)
         */
        Timbre timbre=new Timbre();
        /**
         * Se crea la cadena del Timbre en base al xlst proporcionado por el SAT
         */
        String cadenaTimbre=CadenaOriginal.generarCadenaOriginal("cadOriginalTFD_1.xslt",timbre.agregarTimbre(sello,isfe.getCSD().getNoCertificado(),f.getFolio().getUUID()));
        /**
         * Se obtiene la llave del ISFE como PAC para certificar la factura
         */
        PrivateKey llaveISFE=Cifrado.getLlavePrivada(isfe.getFiel().getArchivoFiel(), isfe.getFiel().getPassword());
        /**
         * Se crea el Sello del SAT con la llave privada del ISFE (PAC) y la
         * cadena del timbre fiscal y se agrega al timbre
         */
        String selloSAT=Cifrado.firmar(llaveISFE, cadenaTimbre.getBytes("UTF-8"));
        Cifrado.eliminarLlavePrivada(llaveISFE);
        timbre.agregarSello(selloSAT);
        xml=cfdi.agregarTimbre(timbre.obtenerTimbre());
        return xml;
    }
    /**
    * Método encargado de formatear la Fecha recibida a formato
    * yyyy-MM-dd'T'HH:mm:ss
    * @param fecha con un formato por default
    * @return fecha formateada
    */
    public static String formatearFecha(Date fecha){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(fecha);
    }
    /**
     * Método encargado de codificar a Base64 una cadena de caracteres
     * @param datos a codificar
     * @return datos codificados a Base64
     */
    public static String codificarBase64(String datos){
        return codificarBase64(datos.getBytes());
    }
    /**
     * Método encargado de codificar a Base64 un arreglo de bytes
     * @param datos a codificar
     * @return datos codificados a Base64
     */
    public static String codificarBase64(byte[] datos){
        return new String(Base64.encodeBase64(datos));
    }
    /**
     * Método encargado de codificar un numero
     * @param numero a codificar
     * @return número codificado
     */
    public static double codificarNumero(double numero){
        return new BigDecimal(numero).setScale(6,RoundingMode.HALF_EVEN).doubleValue();
    }
    /**
     * Método encargado de codificar una cadena de caracteres
     * @param cadena a codificar
     * @return cadena codificada
     */
    public static String codificarCadena(String cadena){
        return cadena.replaceAll("&", "&amp;")
                .replaceAll("\"", "&quot;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("'", "&apos;");
    }
    public class CFDI{
        private Document docXML;
        private ArrayList<Concepto> conceptos;
        private Usuario emisor;
        private Contribuyente receptor;
        private Factura factura;
        private Direccion expedidoEn;
        private String version="3.0";
        private Namespace cfdi=Namespace.getNamespace("cfdi", "http://www.sat.gob.mx/cfd/3");
        private Namespace xsi=Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        private String Esquema="http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv3.xsd";
        public CFDI(Factura f){
            docXML=new Document();
            factura=f;
            emisor=f.getEmisor();
            receptor=f.getReceptor();
            conceptos=f.getConceptos();
            expedidoEn=f.getExpedidoEn();
        }
        public Document generarCFDI(){
            Element comprobante=new Element("Comprobante",cfdi);
            comprobante.addNamespaceDeclaration(xsi);
            comprobante.setAttribute("schemaLocation", Esquema, xsi).setAttribute("version",version);
            if(factura.getFolio()!=null && factura.getFolio().getUsado()==false)
                comprobante.setAttribute("folio", Long.toString(factura.getFolio().getNoFolio()));
            comprobante.setAttribute("fecha", XML.formatearFecha(factura.getFecha()))
                    .setAttribute("sello","selloCFD")
                    .setAttribute("formaDePago", factura.getFormaDePago())
                    .setAttribute("noCertificado", emisor.getCSD().getNoCertificado())
                    .setAttribute("certificado",XML.codificarBase64(emisor.getCSD().getArchivoCSD()))
                    .setAttribute("subTotal", XML.codificarNumero(factura.getSubTotal())+"")
                    .setAttribute("total", XML.codificarNumero(factura.getTotal())+"")
                    .setAttribute("metodoDepago", factura.getMetodoDePago())
                    .setAttribute("tipoDeComprobante", factura.getTipoDeComprobante())
                    .addContent(generarEmisor())
                    .addContent(generarReceptor())
                    .addContent(generarConceptos())
                    .addContent(generarImpuestos())
                    .addContent(generarComplemento());
            return docXML;
        }
        public Element generarEmisor(){
            Element Emisor=new Element("Emisor",cfdi);
            Emisor.setAttribute("rfc", emisor.getRFC());
            Emisor.setAttribute("nombre", XML.codificarCadena(emisor.getNombre()));
            Emisor.addContent(generarDomicilioFiscalEmisor());
            Emisor.addContent(generarExpedidoEn());
            return Emisor;
        }
        public Element generarReceptor(){
            Element Receptor=new Element("Receptor",cfdi);
            Receptor.setAttribute("rfc", receptor.getRFC());
            Receptor.setAttribute("nombre", XML.codificarCadena(receptor.getNombre()));
            Receptor.addContent(generarDomicilioReceptor());
            return Receptor;
        }
        public Element generarImpuestos(){
            Element Impuestos=new Element("Impuestos",cfdi);
            Impuestos.addContent(generarTraslados());
            return Impuestos;
        }
        public Element generarComplemento(){
            Element Complemento=new Element("Complemento",cfdi);
            return Complemento;
        }
        public Document agregarSello(String sello){
            docXML.getRootElement().setAttribute("sello",sello);
            return docXML;
        }
        public Document agregarTimbre(Content timbre) {
		docXML.getRootElement().getChild("Complemento", cfdi).addContent(timbre);
		return docXML;
	}
        public Element generarDomicilioFiscalEmisor(){
            Direccion direccion=emisor.getDireccion();
            Element domicilioFiscal=new Element("Domicilio",cfdi);
            domicilioFiscal.setAttribute("calle", XML.codificarCadena(direccion.getCalle()));
            if(direccion.getNoExterior()!=null)
                domicilioFiscal.setAttribute("noExterior", XML.codificarCadena(direccion.getNoExterior()));
            if(direccion.getNoInterior()!=null)
                domicilioFiscal.setAttribute("noInterior", XML.codificarCadena(direccion.getNoInterior()));
            if(direccion.getColonia()!=null)
                domicilioFiscal.setAttribute("colonia", XML.codificarCadena(direccion.getColonia()));
            if(direccion.getLocalidad()!=null)
                domicilioFiscal.setAttribute("localidad", XML.codificarCadena(direccion.getLocalidad()));
            if(direccion.getReferencia()!=null)
                domicilioFiscal.setAttribute("referencia", XML.codificarCadena(direccion.getReferencia()));
            domicilioFiscal.setAttribute("municipio", XML.codificarCadena(direccion.getMunicipio()));
            domicilioFiscal.setAttribute("estado", XML.codificarCadena(direccion.getEstado()));
            domicilioFiscal.setAttribute("pais", XML.codificarCadena("México"));
            domicilioFiscal.setAttribute("codigoPostal", XML.codificarCadena(direccion.getCodigoPostal()));
            return domicilioFiscal;
        }
        public Element generarExpedidoEn(){
            Element ExpedidoEn=new Element("ExpedidoEn",cfdi);
            ExpedidoEn.setAttribute("calle", XML.codificarCadena(expedidoEn.getCalle()));
            if(expedidoEn.getNoExterior()!=null)
                ExpedidoEn.setAttribute("noExterior", XML.codificarCadena(expedidoEn.getNoExterior()));
            if(expedidoEn.getNoInterior()!=null)
                ExpedidoEn.setAttribute("noInterior", XML.codificarCadena(expedidoEn.getNoInterior()));
            if(expedidoEn.getColonia()!=null)
                ExpedidoEn.setAttribute("colonia", XML.codificarCadena(expedidoEn.getColonia()));
            if(expedidoEn.getLocalidad()!=null)
                ExpedidoEn.setAttribute("localidad", XML.codificarCadena(expedidoEn.getLocalidad()));
            if(expedidoEn.getReferencia()!=null)
                ExpedidoEn.setAttribute("referencia", XML.codificarCadena(expedidoEn.getReferencia()));
            ExpedidoEn.setAttribute("municipio", XML.codificarCadena(expedidoEn.getMunicipio()));
            ExpedidoEn.setAttribute("estado", XML.codificarCadena(expedidoEn.getEstado()));
            ExpedidoEn.setAttribute("pais", XML.codificarCadena("México"));
            ExpedidoEn.setAttribute("codigoPostal", XML.codificarCadena(expedidoEn.getCodigoPostal()));
            return ExpedidoEn;
        }
        public Element generarDomicilioReceptor(){
            Direccion direccion=receptor.getDireccion();
            Element domicilio=new Element("Domicilio",cfdi);
            domicilio.setAttribute("calle", XML.codificarCadena(direccion.getCalle()));
            if(direccion.getNoExterior()!=null)
                domicilio.setAttribute("noExterior", XML.codificarCadena(direccion.getNoExterior()));
            if(direccion.getNoInterior()!=null)
                domicilio.setAttribute("noInterior", XML.codificarCadena(direccion.getNoInterior()));
            if(direccion.getColonia()!=null)
                domicilio.setAttribute("colonia", XML.codificarCadena(direccion.getColonia()));
            if(direccion.getLocalidad()!=null)
                domicilio.setAttribute("localidad", XML.codificarCadena(direccion.getLocalidad()));
            if(direccion.getReferencia()!=null)
                domicilio.setAttribute("referencia", XML.codificarCadena(direccion.getReferencia()));
            domicilio.setAttribute("municipio", XML.codificarCadena(direccion.getMunicipio()));
            domicilio.setAttribute("estado", XML.codificarCadena(direccion.getEstado()));
            domicilio.setAttribute("pais", XML.codificarCadena("México"));
            domicilio.setAttribute("codigoPostal", XML.codificarCadena(direccion.getCodigoPostal()));
            return domicilio;
        }
        public Element generarConceptos(){
            Element Conceptos=new Element("Conceptos",cfdi);
            for(int i=0; i<conceptos.size();i++){
                Conceptos.addContent(generarConcepto(conceptos.get(i)));
            }
            return Conceptos;
        }
        public Element generarConcepto(Concepto concepto){
            Element C=new Element("Concepto",cfdi)
                    .setAttribute("cantidad", concepto.getCantidad()+"");
            if(concepto.getUnidad()!=null)
                C.setAttribute("unidad", XML.codificarCadena(concepto.getUnidad()));
            if(concepto.getNoIdentificacion()!=null)
                C.setAttribute("noIdentificacion", XML.codificarCadena(concepto.getNoIdentificacion()));
            C.setAttribute("descripcion", XML.codificarCadena(concepto.getDescripcion()));
            C.setAttribute("valorUnitario", XML.codificarNumero(concepto.getValorUnitario())+"");
            C.setAttribute("importe", XML.codificarNumero(concepto.getImporte())+"");
            return C;
        }
        public Element generarTraslados(){
            Element Traslados=new Element("Traslados",cfdi);
            Element Traslado=new Element("Traslado",cfdi);
            Traslado.setAttribute("tasa", XML.codificarNumero(0.16)+"");
            Traslado.setAttribute("importe", factura.getImporteTasa()+"");
            Traslado.setAttribute("impuesto", "IVA");
            Traslados.addContent(Traslado);
            return Traslados;
        }
    }
    /**
     * Clase que se encarga de generar el timbrado del CFDI
     */
    public class Timbre{
        private Document timbre;
        private String version="1.0";
        Namespace xsi=Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
        Namespace tfd=Namespace.getNamespace("tfd", "http://www.sat.gob.mx/TimbreFiscalDigital");
        private String Esquema="http://www.sat.gob.mx/TimbreFiscalDigital TimbreFiscalDigital.xsd";
        /**
        * Constructor vacío
        */
        public Timbre(){
            timbre=new Document();
            Element elementTFD=new Element("TimbreFiscalDigital",tfd);
            elementTFD.addNamespaceDeclaration(tfd);
            elementTFD.setAttribute("schemaLocation",Esquema,xsi).setAttribute("version",version);
            timbre.setRootElement(elementTFD);
        }
        public Document agregarTimbre(String selloCFD,String noCertificadoSAT,long UUID){
            timbre.getRootElement()
                    .setAttribute("selloCFD",selloCFD)
                    .setAttribute("FechaTimbrado", formatearFecha(new Date()))
                    .setAttribute("UUID", Long.toString(UUID))
                    .setAttribute("noCertificadoSAT", noCertificadoSAT);
            return timbre;
        }
        public Element agregarSello(String selloSAT){
            return timbre.getRootElement().setAttribute("selloSAT",selloSAT);
        }
        public Content obtenerTimbre(){
            return timbre.getRootElement().detach();
        }
    }
    /**
     *
     * @param xml
     * @param nombre
     * @return
     */
    public static byte[] convertirXMLaBytes(Document xml,String nombre){
        byte[] bytesXML=null;
        FileInputStream fis=null;
        FileOutputStream fos=null;
        try{
            XMLOutputter out=new XMLOutputter(Format.getPrettyFormat());
            File archivo=File.createTempFile(nombre, ".xml");
            fos=new FileOutputStream(archivo);
            out.output(xml, fos);
            bytesXML=new byte[(int)archivo.length()];
            fis=new FileInputStream(archivo);
            fis.read(bytesXML);
        }catch(IOException ex){
            System.err.println(ex.getMessage());
        }finally{
            try {
                fis.close();
            } catch (Exception ex) {}
            try {
                fos.close();
            } catch (Exception ex) {}
        }
        return bytesXML;
    }
    /**
     *
     * @param xml
     * @param nombre
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     * @throws Exception
     */
    public static File generarArchivoXML(byte[] xml,String nombre)throws IOException, FileNotFoundException,Exception{
        File archivo=File.createTempFile(nombre, ".xml");
        FileOutputStream fos=new FileOutputStream(archivo);
        fos.write(xml);
        fos.close();
        return archivo;
    }
    /**
     *
     * @param xml
     * @param response
     * @param request
     * @throws IOException
     * @throws FileNotFoundException
     * @throws Exception
     */
    public static void visualizarXML(File xml,HttpServletResponse response,HttpServletRequest request)throws IOException, FileNotFoundException, Exception{
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + xml.getName());
        byte[] bytePDF=new byte[(int)xml.length()];
        FileInputStream fis=new FileInputStream(xml);
        fis.read();
        response.getOutputStream().write(bytePDF);
        response.getOutputStream().close();
    }
}
