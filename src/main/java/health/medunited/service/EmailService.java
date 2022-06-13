package health.medunited.service;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
import javax.mail.util.ByteArrayDataSource;

import health.medunited.config.MailConfig;
import health.medunited.model.EmailRequest;

@ApplicationScoped
public class EmailService {

    private static final Logger log = Logger.getLogger(EmailService.class.getName());

    static final String FROMKIMADDRESS = "manuel.blechschmidt@incentergy.de";

    @Inject
    MailConfig mailConfig;

    @Inject
    PdfService pdfService;

    public void sendToDoctor(String toKimAddress, EmailRequest emailRequest) {
        try {

            MimeMessage msg = makeMessageEnvelope();

            // https://fachportal.gematik.de/toolkit/dienstkennung-kim-kom-le
            setMessageAttributes(msg, "Rezeptanforderung als eArztbrief", "X-KIM-Dienstkennung",
                    "Arztbrief;VHitG-Versand;V1.2");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(emailRequest.getContactmessage(), "utf-8");

            MimeBodyPart attachment = new MimeBodyPart();
            attachment.setContent(emailRequest.getAttachment(), "application/xml");

            MimeBodyPart pdf = new MimeBodyPart();
            ByteArrayDataSource ds = new ByteArrayDataSource(pdfService.generatePdfFile().readAllBytes(),
                    "application/pdf");
            pdf.setDataHandler(new DataHandler(ds));

            Multipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(textPart);
            multiPart.addBodyPart(attachment);
            multiPart.addBodyPart(pdf);
            msg.setContent(multiPart);

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toKimAddress, false));
            log.info("Message is ready");
            Transport.send(msg);

            log.info("E-Mail sent successfully to: " + toKimAddress);
        } catch (Exception e) {
            log.log(Level.WARNING, "Error during sending E-Prescription", e);
        }
    }

    public void notify(String toKimAddress) {
        try {
            MimeMessage msg = makeMessageEnvelope();

            setMessageAttributes(msg, "Anforderung Mitteilung", null, null);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Mitteilung!", "utf-8");

            Multipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(textPart);

            msg.setContent(multiPart);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toKimAddress, false));
            
            log.info("Message is ready");
            Transport.send(msg);
            log.info("E-Mail sent successfully to: " + toKimAddress);
        } catch (Exception e) {
            log.log(Level.WARNING, "Error during sending E-Prescription", e);
        }

    }

    private MimeMessage makeMessageEnvelope() {
        Properties props = new Properties();

        props.put("mail.smtp.host", mailConfig.getSmtpHost());
        props.put("mail.smtp.auth", true);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.getSmtpUser(), mailConfig.getSmtpPassword());
            }
        });

        return new MimeMessage(session);
    }

    private void setMessageAttributes(MimeMessage msg, String subject, String headerName, String headerValue) {
        try {
            if (headerName != null && headerValue != null) {
                msg.setHeader(headerName, headerValue);
            }
            msg.setFrom(new InternetAddress(FROMKIMADDRESS));
            msg.setReplyTo(InternetAddress.parse(FROMKIMADDRESS, false));
            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
        } catch (Exception e) {
            log.log(Level.WARNING, "Error during setting message attributes", e);
        }
    }

}
