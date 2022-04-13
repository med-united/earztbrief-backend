package health.medunited;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import health.medunited.model.EmailRequest;

@Path("/sendEmail")
public class EmailController {
    private static Logger log = Logger.getLogger(EmailController.class.getName());

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public void sendERezeptToKIMAddress(EmailRequest emailRequest) {

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
            // set message headers
            msg.addHeader("X-KIM-Dienstkennung", "eRezept;Zuweisung;V1.0");

            msg.setFrom(new InternetAddress(fromKimAddress));

            msg.setReplyTo(InternetAddress.parse(fromKimAddress, false));

            msg.setSubject("E-Rezept direkte Zuweisung", "UTF-8");

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

    public InputStream generatePdfFile() {
        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Create a new font object selecting one of the PDF base fonts
        PDFont font = PDType1Font.HELVETICA_BOLD;

        try (// Start a new content stream which will "hold" the to be created content
            PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Define a text content stream using the selected font, moving the cursor and
            // drawing the text "Hello World"
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount(100, 700);
            contentStream.drawString("Hello World");
            contentStream.endText();

            // Make sure that the content stream is closed:
            contentStream.close();
            // Save the results and ensure that the document is properly closed:
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            document.close();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            return in;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}