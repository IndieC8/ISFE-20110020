/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subsistemaAutomatico;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.jdom.Document;

/**
 *
 * @author kawatoto
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

    public EnvioMail(String mailReceptor, String asunto, String cuerpo, Document xml) throws MessagingException {
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

    private class autentificadorSMTP extends javax.mail.Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(correo, contraseña);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws MessagingException {
        // TODO Auto-generated method stub
        EnvioMail EnviadorMail = new EnvioMail("kawatoto.j33@gmail.com","Prueba del Envio de Correo de ISFE", "Hola lupita esta es una prueba del envio de correo desde Java para el TT, es la primera prueba en la segunda tratare de enviarlo con una adjunto. Saludos :D",null);
    }

}
