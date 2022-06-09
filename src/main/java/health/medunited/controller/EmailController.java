package health.medunited.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import health.medunited.model.PharmacyRequest;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import health.medunited.model.EmailRequest;
import health.medunited.service.EmailService;

@Path("/sendEmail")
public class EmailController {

    @ConfigProperty(name = "mail.smtp.host")
    String smtpHostServer;
    @ConfigProperty(name = "mail.smtp.user")
    String smtpUser;
    @ConfigProperty(name = "mail.smtp.password")
    String smtpPassword;

    @Inject
    EmailService emailService;

    @POST
    @Path("/earztbrief")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendERezeptToKIMAddress(EmailRequest emailRequest) {

        String toKimAddress = emailRequest.getContactemail();

        emailService.sendToDoctor(toKimAddress, emailRequest);
    }

    @POST
    @Path("/notifyPharmacy")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendEmailToPharmacy(PharmacyRequest pharmacyRequest) {

        String toKimAddress = pharmacyRequest.getPharmacyEmail();

        emailService.notify(toKimAddress);
    }
}
