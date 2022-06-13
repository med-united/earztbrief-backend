package health.medunited.controller;

import health.medunited.config.MailConfig;
import io.smallrye.config.SmallRyeConfig;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import health.medunited.config.MailConfiguration;
import health.medunited.model.EmailRequest;
import health.medunited.model.PharmacyRequest;
import health.medunited.service.EmailService;
import health.medunited.service.PdfService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.http.ContentType;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@QuarkusTest
@TestHTTPEndpoint(EmailController.class)
class EmailControllerTest {

    @InjectMock
    EmailService emailService;

    @InjectMock
    PdfService pdfService;

    @Inject
    MailConfig mailConfig;

    @Test
    void testSuccessfullSendingToDoctor() {

        EmailRequest emailRequest = new EmailRequest("testName", "simone.stifano@incentergy.de",
                "I would like to receive a medication",
                "An earztbrief in xml");

        Mockito.doNothing().when(emailService).sendToDoctor(emailRequest.getContactemail(), emailRequest);

        Mockito.doReturn(setUpPdfDocument()).when(pdfService).generatePdfFile();

        given()
                .contentType(ContentType.JSON)
                .body(emailRequest)
                .when()
                .post("/earztbrief")
                .then()
                .statusCode(204);
    }

    @Test
    void testSuccessfullNotificationSent() {

        PharmacyRequest pharmacyRequest = new PharmacyRequest("simone.stifano@incentergy.de");

        given()
                .header("Content-Type", "application/json")
                .body(pharmacyRequest)
                .when()
                .post("/notifyPharmacy")
                .then()
                .statusCode(204);
    }

    private InputStream setUpPdfDocument() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
