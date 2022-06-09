package health.medunited.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.enterprise.context.ApplicationScoped;
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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import health.medunited.model.EmailRequest;

@ApplicationScoped
public class EmailService {

    private static final Logger log = Logger.getLogger(EmailService.class.getName());

    @ConfigProperty(name = "mail.smtp.host")
    String smtpHostServer;
    @ConfigProperty(name = "mail.smtp.user")
    String smtpUser;
    @ConfigProperty(name = "mail.smtp.password")
    String smtpPassword;

    public void sendToDoctorFromTo(String fromKimAddress, String toKimAddress, EmailRequest emailRequest) {
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

            // https://fachportal.gematik.de/toolkit/dienstkennung-kim-kom-le
            msg.addHeader("X-KIM-Dienstkennung", "Arztbrief;VHitG-Versand;V1.2");

            msg.setFrom(new InternetAddress(fromKimAddress));

            msg.setReplyTo(InternetAddress.parse(fromKimAddress, false));

            msg.setSubject("Rezeptanforderung als eArztbrief", "UTF-8");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(emailRequest.getContactmessage(), "utf-8");

            MimeBodyPart attachment = new MimeBodyPart();
            attachment.setContent(emailRequest.getAttachment(), "application/xml");

            MimeBodyPart pdf = new MimeBodyPart();
            ByteArrayDataSource ds = new ByteArrayDataSource(generatePdfFile().readAllBytes(), "application/pdf");
            pdf.setDataHandler(new DataHandler(ds));

            Multipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(textPart);
            multiPart.addBodyPart(attachment);
            multiPart.addBodyPart(pdf);
            msg.setContent(multiPart);

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toKimAddress, false));
            log.info("Message is ready");
            Transport.send(msg);

            log.info("E-Mail sent successfully to: " + toKimAddress);
        } catch (Exception e) {
            log.log(Level.WARNING, "Error during sending E-Prescription", e);
        }
    }

    public void notify(String pharmacyEmail) {
        System.out.println(pharmacyEmail);
    }

    private InputStream generatePdfFile() {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDFont font = PDType1Font.HELVETICA_BOLD;

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount(100, 700);
            contentStream.drawString("Rezeptanforderung");
            contentStream.endText();

            contentStream.close();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            document.close();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            return in;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
