package health.medunited.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;

import health.medunited.model.EmailRequest;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

@ApplicationScoped
public class EmailService {

    private static final Logger log = Logger.getLogger(EmailService.class.getName());

    @Inject
    PdfService pdfService;

    @Inject
    Mailer mailer;

    public void sendToDoctor(String toKimAddress, EmailRequest emailRequest) {

        try {
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.setContent(emailRequest.getAttachment(), "text/xml");

            MimeBodyPart pdf = new MimeBodyPart();
            ByteArrayDataSource ds = new ByteArrayDataSource(pdfService.generatePdfFile().readAllBytes(),
                    "application/pdf");
            pdf.setDataHandler(new DataHandler(ds));

            mailer.send(Mail.withText(toKimAddress, "Rezeptanforderung als eArztbrief",
                            emailRequest.getContactmessage())
                    .addAttachment("xmlattach",
                            attachment.getInputStream().readAllBytes(), "text/xml")
                    .addAttachment("pdfattach",
                            pdf.getInputStream().readAllBytes(), "application/pdf")

            );
            log.info("E-Mail sent successfully to: " + toKimAddress);
        } catch (Exception e) {
            log.log(Level.WARNING, "Error during sending E-Prescription", e);
        }
    }

    public void notify(String toKimAddress) {

        mailer.send(Mail.withText(toKimAddress, "Anforderung Mitteilung", "Mitteilung!"));

    }

}
