package subsistemaAutomatico;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase que se encarga del envio de las facturas en su formato XML
 * al usuario
 * @author Trabajo Terminal 20110020 Implementación del Servicio de Facturación Electrónica acorde a la reforma de enero de 2011
 */
public class EnvioMail
{
    final String correo = "isfe20110020@gmail.com";
    final String contraseña = "isfe20110020";
    final String servidorSMTP = "smtp.gmail.com";
    final String puertoEnvio = "465";
    String mailReceptor = null;
    String asunto = null;
    String cuerpo = null;
    /**
     * Método encargado de realizar el envió del correo electrónico al Usuario 
     * con la factura eletrónica generada en formato xml
     * @param mailReceptor parametro encargado de ser el mail del receptor
     * en este caso del usuario de ISFE
     * @param asunto parametro encrgado de mostrar al receptor del mail el
     * asunto del mensaje
     * @param cuerpo es el cuerpo del mensaje al destinatario del mail
     * @param xml es el archivo adjunto, concretamente la factura electronica
     * que se enviara al usuario
     * @throws MessagingException excepcion que maneja algun problema al
     * realizar el envio del correo
     */
    public EnvioMail(String mailReceptor, String asunto, String cuerpo,File xml) throws MessagingException {
        this.mailReceptor = mailReceptor;
        this.asunto = asunto;
        this.cuerpo = cuerpo;

        Properties props = new Properties();
        props.put("mail.smtp.user", correo);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puertoEnvio);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", puertoEnvio);
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();

        // Se compone la parte del texto
        BodyPart texto = new MimeBodyPart();
        texto.setText(cuerpo);


        // Se compone el adjunto con la imagen
        BodyPart adjunto = new MimeBodyPart();
        Object archivoAdjunto = xml;
        adjunto.setDataHandler(new DataHandler(archivoAdjunto,"xml"));
        //adjunto.setDataHandler(new DataHandler(new FileDataSource("")));
        adjunto.setFileName("Factura Electronica");

        // Una MultiParte para agrupar texto e imagen.
        MimeMultipart multiParte = new MimeMultipart();
        multiParte.addBodyPart(texto);
        multiParte.addBodyPart(adjunto);

        try {
            Authenticator auth = new EnvioMail.autentificadorSMTP();
            Session session = Session.getInstance(props, auth);
            // session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setSubject(asunto);
            msg.setContent(multiParte);
            msg.setFrom(new InternetAddress(correo));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    mailReceptor));
            Transport.send(msg);
        } catch (Exception mex) {
        }
    }
    /**
     * Clase encargada de la autentificación del SMTP para el envió de correos 
     * en ISFE
     */
    private class autentificadorSMTP extends javax.mail.Authenticator {
        /**
         * Método encargado de la autentificación para el envió y recepción para
         * el uso de SMTP para envió de correos electrónicos
         * @return 
         */
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(correo, contraseña);
        }
    }
}