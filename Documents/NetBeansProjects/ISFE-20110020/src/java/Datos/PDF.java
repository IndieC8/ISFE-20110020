package Datos;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.*;
import org.apache.fop.pdf.PDFEncryptionParams;

/**
 * Clase que representa y genera el Formato PDF para la factura electrónica
 * @author Raul Hernandez
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
    public File generarArchivoPDF(File xml, File xsl) throws FileNotFoundException, FOPException, TransformerConfigurationException, TransformerException, IOException{
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
    }
    /**
     * Método encargado de visualizar el PDF generado en un JSP o un Servlet
     * @param xml archivo xml de la factura electrónica
     * @param response salida hacia el JSP
     * @param request entrada del JSP
     * @throws IOException
     * @throws FileNotFoundException
     * @throws FOPException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws Exception 
     */
    public static void visualizarPDF(File xml,HttpServletResponse response,HttpServletRequest request)throws IOException, FileNotFoundException, FOPException, TransformerConfigurationException,TransformerException, Exception{
        PDF pdf=new PDF();
        String path=request.getSession().getServletContext().getRealPath("/");
        File archivoPDF=pdf.generarArchivoPDF(xml, new File(path+"resurces/xslt/PDF.xsl"));
        response.setContentType("application/pdf");
        byte[] bytePDF=new byte[(int)archivoPDF.length()];
        FileInputStream fis=new FileInputStream(archivoPDF);
        fis.read();
        response.getOutputStream().write(bytePDF);
        response.getOutputStream().close();
    }
}
