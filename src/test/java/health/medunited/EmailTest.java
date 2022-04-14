package health.medunited;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EmailTest {

    @Test
    @Disabled
    public void testHelloEndpoint() {
        given().body(getClass().getResourceAsStream("/client-post.json")).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when().post("/sendEmail")
                .then()
                .statusCode(204);
    }

}