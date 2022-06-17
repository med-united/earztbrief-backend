package health.medunited.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import health.medunited.model.*;

import health.medunited.service.EmailService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/sendEmail")
public class EmailController {

    @Inject
    EmailService emailService;

    @POST
    @Path("/earztbrief")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendERezeptToKIMAddress(LetterRequest letterRequest) {

        emailService.sendToDoctor(letterRequest);
    }

    @POST
    @Path("/notifyPharmacy")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendEmailToPharmacy(PharmacyRequest pharmacyRequest) {

        emailService.notify(pharmacyRequest);
    }

    @POST
    @Path("/powershell")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void sendPowershellScript(@MultipartForm PowershellRequest powershellRequest) {

        emailService.sendPowershellScript(powershellRequest);
    }

}
