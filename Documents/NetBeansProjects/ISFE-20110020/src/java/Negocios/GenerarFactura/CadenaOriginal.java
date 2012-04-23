/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocios.GenerarFactura;

import org.jdom.Document;
import org.jdom.transform.XSLTransformException;
import org.jdom.transform.XSLTransformer;

/**
 * Clase encargada de generar la cadena original de la factura electrónica 
 * @author Raul Hernandez
 */
public class CadenaOriginal {
    /**
     * Método que tranforma el xml en base al xslt prporcionado por el SAT
     * @param xslt para transformar el xml con los lineamientos del SAT
     * @param xml a transformar
     * @return Cadena Original o Cadena del Timbre del XML
     */
    public static String generarCadenaOriginal(String xslt, Document xml) {
        try {
            XSLTransformer transformer;
            transformer = new XSLTransformer(xslt);
            return transformer.transform(xml).getRootElement().getText();
        } catch (XSLTransformException ex) {
            System.err.println("ERROR: No se ha podido transformar el documento y el XSLT");
            return null;
        }
    }
}
