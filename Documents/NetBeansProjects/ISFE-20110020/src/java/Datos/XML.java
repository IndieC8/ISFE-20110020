package Datos;

import Integracion.ConexionSAT.SAT;
import Negocios.Cifrado.Cifrado;
import Negocios.GenerarFactura.CadenaOriginal;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Clase que representa y genera el formato XML para la factura electrónica
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
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
    public Document generarXML(Factura f,ISFE isfe,String path)throws SecurityException, UnsupportedEncodingException,IOException, NoSuchProviderException, Exception{
        Document xml=null;
        CFDI cfdi=new CFDI(f);
        xml=crearXML(f,cfdi,path);
        xml=timbrarCFDI(f,cfdi,isfe,path);
        return xml;
    }
    private Document crearXML(Factura factura,CFDI cfdi,String path) throws IOException, SecurityException, NoSuchProviderException, Exception{
        
        String cadOriginal = CadenaOriginal.generarCadenaOriginal(path+"cadOriginalCFDI_3.xslt",cfdi.generarCFDI());
        System.out.println("CO:"+cadOriginal);
        SAT sat=new SAT();
        System.out.println("Fiel:"+factura.getEmisor().getFiel().getArchivoFiel());
        System.out.println("PSWD:"+factura.getEmisor().getFiel().getPassword());
        String sello = sat.ValidarCadenaOriginal(cadOriginal,factura.getEmisor().getFiel().getArchivoFiel(),factura.getEmisor().getFiel().getPassword(),true);
        System.out.println("Sello: "+sello);
        factura.setCadenaCSD(sello);
        return cfdi.agregarSello(sello);
    }
    private Document timbrarCFDI(Factura factura,CFDI cfdi,ISFE isfe,String path) throws SecurityException, UnsupportedEncodingException, NoSuchProviderException, IOException, Exception{
        Timbre timbre=new Timbre();
        String cadTimbre = CadenaOriginal.generarCadenaOriginal(path+"cadOriginalTFD_1.xslt", timbre.agregarTimbre(factura, isfe));
        SAT sat=new SAT();
        String sello = sat.ValidarCadenaOriginal(cadTimbre,isfe.getFiel().getArchivoFiel(),isfe.getFiel().getPassword(),false);
	timbre.agregarSello(sello);
	return cfdi.agregarTimbre(timbre.obtenerTimbre());
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
    /**
     * Método encargado de generar el archivo xml 
     * @param xml arreglo de bytes del xml
     * @param nombre del archivo a generar
     * @return archivo xml generado
     * @throws IOException
     * @throws FileNotFoundException
     * @throws Exception
     */
    @SuppressWarnings("CallToThreadDumpStack")
    public static File generarArchivoXML(Document xml,String nombre,String path){
        File fXML=null;
        FileOutputStream fos=null;
        try{
            XMLOutputter out=new XMLOutputter(Format.getPrettyFormat());
            fXML=new File(path+nombre);
            fos=new FileOutputStream(fXML);
            out.output(xml, fos);
        }catch(IOException ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        finally{
            try {
                fos.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
        return fXML;
    }
    /**
     * Método encargado de visualizar el xml dentro de un JSP o Servlet
     * @param xml archivo xml de la factura electrónica
     * @param response salida de datos
     * @param request entrada de datos
     * @throws IOException
     * @throws FileNotFoundException
     * @throws Exception
     */
    public static void visualizarXML(File xml,HttpServletResponse response,HttpServletRequest request)throws IOException, FileNotFoundException, Exception{
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + xml.getName());
        byte[] byteXML=new byte[(int)xml.length()];
        FileInputStream fis=new FileInputStream(xml);
        fis.read();
        response.getOutputStream().write(byteXML);
        response.getOutputStream().close();
    }
}
