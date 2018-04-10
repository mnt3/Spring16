package lt.itakademija;

import lt.itakademija.grader.Grader;
import lt.itakademija.model.RegisteredEvent;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by mariusg on 2016.12.19.
 */
public class SwaggerDocTask {

    private String url = "http://localhost:9092/spring-exam/v2/api-docs";

    private static TestRestTemplate restTemplate;

    @BeforeClass
    public static void setUp() {
        restTemplate = new TestRestTemplate(new RestTemplate());
    }

    @Test
    public void swaggerDocIsPublished() {
        Grader.graded(5, () -> {
            ResponseEntity<Map> getResponse = restTemplate.exchange(this.url,
                    HttpMethod.GET,
                    null,
                    Map.class);
            Assert.assertThat(getResponse.getStatusCode(), is(HttpStatus.OK));
        });
    }

}
