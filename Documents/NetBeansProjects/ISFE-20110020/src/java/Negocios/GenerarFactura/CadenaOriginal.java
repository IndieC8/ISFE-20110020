package Negocios.GenerarFactura;

import java.io.File;
import org.jdom.Document;
import org.jdom.transform.XSLTransformException;
import org.jdom.transform.XSLTransformer;

/**
 * Clase encargada de generar la cadena original de la factura electrónica 
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class CadenaOriginal {
    /**
     * Método que tranforma el xml en base al xslt proporcionado por el SAT
     * @param xslt para transformar el xml con los lineamientos del SAT
     * @param xml a transformar
     * @return Cadena Original o Cadena del Timbre del XML
     */
    public static String generarCadenaOriginal(String xslt, Document xml) {
        try {
            System.setProperty("javax.xml.transform.TransformerFactory","net.sf.saxon.TransformerFactoryImpl");   
            XSLTransformer transformer;
            File fXSLT=new File(xslt);
            transformer = new XSLTransformer(fXSLT);
            return transformer.transform(xml).getRootElement().getText();
        } catch (XSLTransformException ex) {
            System.err.println("ERROR: No se ha podido transformar el documento y el XSLT");
            return null;
        }
    }
}
