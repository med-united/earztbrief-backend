package health.medunited.controller;

import health.medunited.model.LetterRequest;
import health.medunited.model.PharmacyRequest;
import health.medunited.service.EmailService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
class EmailControllerTest {

    private static final String TODOCTOR = "doctor@testmail.test";

    private static final String TOPHARMACY = "pharmacy@testmail.test";

    @Inject
    EmailService emailService;

    @Inject
    MockMailbox mailbox;

    @Test
    void testEarztbriefSending() {

        ArrayList<String> attachment = new ArrayList<>();
        attachment.add("XML file");
        ArrayList<String> datamatrices = new ArrayList<>();
        datamatrices.add("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");
        LetterRequest request = new LetterRequest("Test Name", TODOCTOR, "Message", attachment, datamatrices);

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/sendEmail/earztbrief")
                .then()
                .statusCode(204);

        List<Mail> sent = mailbox.getMessagesSentTo(TODOCTOR);
        assertThat("Sent messages doesn't match", sent.size() == 1);
        Mail actual = sent.get(0);
        assertThat("Wrong subject", actual.getSubject().equals("Rezeptanforderung als eArztbrief"));
    }

    @Test
    void testPharmacyNotifier() {

        PharmacyRequest request = new PharmacyRequest(TOPHARMACY, "patient", "doctor", 123, "active", null);

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/sendEmail/notifyPharmacy")
                .then()
                .statusCode(204);

        List<Mail> sent = mailbox.getMessagesSentTo(TOPHARMACY);
        assertThat("Sent messages doesn't match", sent.size() == 1);
        Mail actual = sent.get(0);
        assertThat("Wrong subject", actual.getSubject().equals("Anforderung Mitteilung"));
    }
}
