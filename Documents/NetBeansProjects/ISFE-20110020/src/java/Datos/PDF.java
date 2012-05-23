package Datos;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/**
 * Clase que representa y genera el Formato PDF para la factura electrónica
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class PDF extends Formato{
    static {
        System.setProperty("javax.xml.transform.TransformerFactory","net.sf.saxon.TransformerFactoryImpl");
    }
    /**
     * Método encargado de generar el archivo PDF de la factura electrónica
     * @param xml archivo en formato xml de la factura electrónica
     * @param xsl archivo encargado de formatear el xml al formato PDF
     * @return archivo PDF de la factura electrónica
     * @throws FileNotFoundException
     * @throws FOPException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws IOException 
     */
    public static File generarArchivoPDF(File xml,String path,String nombre) throws FileNotFoundException, FOPException, TransformerConfigurationException, TransformerException, IOException{
        FopFactory fopFactory = FopFactory.newInstance();
        File fPDF=new File(path+nombre);
        OutputStream out = new java.io.BufferedOutputStream(new java.io.FileOutputStream(fPDF));
        try{
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = factory.newTransformer(new StreamSource(new File(path+"resources/xslt/"+"PDF.xsl")));
            Source src = new StreamSource(xml);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        }finally{
            out.close();
        }
        return fPDF;
    }
    /**public File generarArchivoPDF(File xml, File xsl) throws FileNotFoundException, FOPException, TransformerConfigurationException, TransformerException, IOException{
        FopFactory fopFactory = FopFactory.newInstance();
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        foUserAgent.getRendererOptions().put("encryption-params", new PDFEncryptionParams(null, null, true, true, false, false));
                
        File pdf = File.createTempFile(xml.getName(), ".pdf");
        File pdfSigned = File.createTempFile(xml.getName() + "_signed", ".pdf");
        OutputStream out = new java.io.BufferedOutputStream(new java.io.FileOutputStream(pdf));
                
        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = factory.newTransformer(new StreamSource(xsl));
            transformer.setParameter("versionParam", "2.0");
            Source src = new StreamSource(xml);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } finally {
            out.close();
        }        
        
        try {
            File f = new File(xsl.getParent() + "/../certificado/keystore.ks");
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(new FileInputStream(f), "Mexican_1199".toCharArray());
            String alias = ks.aliases().nextElement();
            PrivateKey key = (PrivateKey) ks.getKey(alias, "Mexican_1199".toCharArray());
            java.security.cert.Certificate[] chain = ks.getCertificateChain(alias);

            PdfReader reader = new PdfReader(new FileInputStream(pdf));
            FileOutputStream fout = new FileOutputStream(pdfSigned);
            PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
            PdfSignatureAppearance sap = stp.getSignatureAppearance();
            sap.setCrypto(key, chain, null, PdfSignatureAppearance.SELF_SIGNED);
            sap.setReason("CFDI emitido con ISFE");
            sap.setLocation("México");
            stp.close();
        } catch (Exception e) {
            System.out.println("->Ex: " + e);
        }
        
        return pdfSigned;
    }*/
    /**
     * Método encargado de visualizar el PDF generado en un JSP o un Servlet
     * @param pdf Archivo pdf a visualizar en el navegador
     * @param response del jsp
     * @param request del jsp
     * @throws IOException Si hay errores de entrada/salida
     */
    public static byte[] visualizarPDF(File pdf,HttpServletResponse response,HttpServletRequest request){
        InputStream in = null;
        byte[] datos=null;
        try {
            in = new FileInputStream(pdf);
            datos=new byte[in.available()];
            in.read(datos);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=\""+pdf.getName()+"\";");
            response.sendRedirect(pdf.getName());
            in.close();
            pdf.deleteOnExit();
            return datos;
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
}
