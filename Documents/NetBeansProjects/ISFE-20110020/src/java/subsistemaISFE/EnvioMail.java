package subsistemaISFE;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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
    final String contrasena = "isfe20110020";
    final String servidorSMTP = "smtp.gmail.com";
    final String puertoEnvio = "587";
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
    public boolean EnvioMail(String mailReceptor, String asunto, String cuerpo, File archivo, String nameArchivo)
            throws MessagingException {
        this.mailReceptor = mailReceptor;
        this.asunto = asunto;
        this.cuerpo = cuerpo;

        try
        {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", puertoEnvio);
            props.setProperty("mail.smtp.user", correo);
            props.setProperty("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);

            // Se compone la parte del texto
            MimeBodyPart texto = new MimeBodyPart();
            texto.setText(this.cuerpo);

            // Se compone el adjunto
            MimeBodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(archivo)));
            adjunto.setFileName(nameArchivo);

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);

            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress(this.mailReceptor));
            message.setSubject(this.asunto);
            message.setContent(multiParte);

            // Se envia el correo.
            Transport t = session.getTransport("smtp");
            t.connect(correo, contrasena);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
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
            return new PasswordAuthentication(correo, contrasena);
        }
    }
}