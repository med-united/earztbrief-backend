package health.medunited;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import health.medunited.model.EmailRequest;

@Path("/sendEmail")
public class EmailController {
    private static Logger log = Logger.getLogger(EmailController.class.getName());

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public void sendERezeptToKIMAddress(EmailRequest emailRequest ) {

        String fromKimAddress = "manuel.blechschmidt@incentergy.de"; 
        String toKimAddress = emailRequest.getContactemail();
        String smtpHostServer = "email-smtp.eu-central-1.amazonaws.com";
        String smtpUser = "AKIA3ENSQUR5EQVJDIPH";
        String smtpPassword = "BITqwRJJoxpMTa2sVgQzg7C4eidwRY795CoxjsL5b3H0";
                                    
        try {
            Properties props = new Properties();

            props.put("mail.smtp.host", smtpHostServer);
            props.put("mail.smtp.auth", true);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpUser, smtpPassword);
                }
            });
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("X-KIM-Dienstkennung", "eRezept;Zuweisung;V1.0");

            msg.setFrom(new InternetAddress(fromKimAddress));

            msg.setReplyTo(InternetAddress.parse(fromKimAddress, false));

            msg.setSubject("E-Rezept direkte Zuweisung", "UTF-8");


            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(emailRequest.getContactmessage(), "utf-8");

            MimeBodyPart erezeptTokenPart = new MimeBodyPart();
            erezeptTokenPart.setText(emailRequest.getContactmessage(), "utf8");
            
            Multipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(textPart); // <-- first
            multiPart.addBodyPart(erezeptTokenPart); // <-- second
            msg.setContent(multiPart);

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toKimAddress, false));
            log.info("Message is ready");
            Transport.send(msg);  

            log.info("E-Mail sent successfully to: "+toKimAddress);
        } catch (Exception e) {
        log.log(Level.WARNING, "Error during sending E-Prescription", e);
        }
    }
}