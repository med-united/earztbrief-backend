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

        LetterRequest request = new LetterRequest("Test Name", TODOCTOR, "Earztbrief request", "Xml text");

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
