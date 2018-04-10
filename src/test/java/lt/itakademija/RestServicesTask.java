package lt.itakademija;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.http.RequestEntity.put;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lt.itakademija.grader.Grader;
import lt.itakademija.model.EventRegistration;
import lt.itakademija.model.RegisteredEvent;
import lt.itakademija.model.RegisteredEventUpdate;
import lt.itakademija.model.SeverityLevel;
import lt.itakademija.util.Jsons;

/**
 * Created by mariusg on 2016.12.19.
 */
public class RestServicesTask {

    private final Logger logger = LoggerFactory.getLogger(RestServicesTask.class);

    private static TestRestTemplate restTemplate;

    private static List<EventRegistration> registrations;

    private String uri = "http://localhost:9092/spring-exam/webapi/events";

    @BeforeClass
    public static void setUp() {
        restTemplate = new TestRestTemplate(new RestTemplate());
        registrations = new LinkedList<>();

        EventRegistration registration = new EventRegistration();
        registration.setSeverityLevel(SeverityLevel.HIGH);
        registration.setLocation("Vilnius");
        registration.setDescription("Attack reported.");
        registrations.add(registration);

        registration = new EventRegistration();
        registration.setSeverityLevel(SeverityLevel.NORMAL);
        registration.setLocation("Kaunas");
        registration.setDescription("Client's bag is missing.");
        registrations.add(registration);

        registration = new EventRegistration();
        registration.setSeverityLevel(SeverityLevel.LOW);
        registration.setLocation("Panevezys");
        registration.setDescription("Jewelry store alarm.");
        registrations.add(registration);
    }
    
    @Test
    public void eventCreateOperationEnsuresThatRequiredDataIsPresent() {
    	Grader.graded(2, () -> {
    		ResponseEntity<RegisteredEvent> response = null;

    		EventRegistration eventRegistration = new EventRegistration();
    		eventRegistration.setSeverityLevel(null);
    		eventRegistration.setLocation(generateText(1));
    		eventRegistration.setDescription(generateText(1));
    		response = executeRegistrationRequest(eventRegistration);
    		Assert.assertThat("Probably some validator is missing (not null)", 
    				response.getStatusCode(), 
    				is(HttpStatus.BAD_REQUEST));
    		
    		eventRegistration = new EventRegistration();
    		eventRegistration.setSeverityLevel(SeverityLevel.HIGH);
    		eventRegistration.setLocation(null);
    		eventRegistration.setDescription(generateText(1));
    		response = executeRegistrationRequest(eventRegistration);
    		Assert.assertThat("Probably some validator is missing (not null)", 
    				response.getStatusCode(), 
    				is(HttpStatus.BAD_REQUEST));
    		
    		eventRegistration = new EventRegistration();
    		eventRegistration.setSeverityLevel(SeverityLevel.HIGH);
    		eventRegistration.setLocation(generateText(1));
    		eventRegistration.setDescription(null);
    		response = executeRegistrationRequest(eventRegistration);
    		Assert.assertThat("Probably some validator is missing (not null)", 
    				response.getStatusCode(), 
    				is(HttpStatus.BAD_REQUEST));
    		
    		eventRegistration = new EventRegistration();
    		eventRegistration.setSeverityLevel(SeverityLevel.HIGH);
    		eventRegistration.setLocation("");
    		eventRegistration.setDescription(generateText(1));
    		response = executeRegistrationRequest(eventRegistration);
    		Assert.assertThat("Probably some validator is missing (not blank)", 
    				response.getStatusCode(), 
    				is(HttpStatus.BAD_REQUEST));
    		
    		eventRegistration = new EventRegistration();
    		eventRegistration.setSeverityLevel(SeverityLevel.HIGH);
    		eventRegistration.setLocation(generateText(1));
    		eventRegistration.setDescription("");
    		response = executeRegistrationRequest(eventRegistration);
    		Assert.assertThat("Probably some validator is missing (not blank)", 
    				response.getStatusCode(), 
    				is(HttpStatus.BAD_REQUEST));
    		
    		// Too long location
    		eventRegistration = new EventRegistration();
    		eventRegistration.setSeverityLevel(SeverityLevel.HIGH);
    		eventRegistration.setLocation(generateText(101));
    		eventRegistration.setDescription(generateText(1));
    		response = executeRegistrationRequest(eventRegistration);
    		Assert.assertThat("Probably some validator is missing (max length)",
    				response.getStatusCode(), 
    				is(HttpStatus.BAD_REQUEST));
    		
    		// Too long description
    		eventRegistration = new EventRegistration();
    		eventRegistration.setSeverityLevel(SeverityLevel.HIGH);
    		eventRegistration.setLocation(generateText(1));
    		eventRegistration.setDescription(generateText(1001));
    		response = executeRegistrationRequest(eventRegistration);
    		Assert.assertThat("Probably some validator is missing (max length)",
    				response.getStatusCode(), 
    				is(HttpStatus.BAD_REQUEST));
    	});
    }
    
    private String generateText(int length) {
    	StringBuilder sb = new StringBuilder();
    	
    	for (int i = 0; i < length; i++) {
    		sb.append("x");
    	}
    	
    	return sb.toString();
    }
    
    @Test
    public void eventUpdateOperationEnsuresThatRequiredDataIsPresent() {
    	Grader.graded(2, () -> {
    		RegisteredEventUpdate eventUpdate = new RegisteredEventUpdate();
    		ResponseEntity<RegisteredEvent> response = executeEventUpdateRequest(1L, eventUpdate);
    		Assert.assertThat("Probably some validator is missing (not null)", 
    				response.getStatusCode(), 
    				is(HttpStatus.BAD_REQUEST));
    	});
    }

    @Test
    public void registersEventsThenChangesSeverityLevelForEachEventAndDeletesEachEvent() throws Exception {
        Grader.graded(5, () -> {
            // Registering events
            for (EventRegistration registration: registrations) {
                logger.info("Registering event: {}", Jsons.toJsonString(registration));
                final RegisteredEvent registeredEvent = registerEvent(registration);
                logger.info("Successfully registered event: {}", Jsons.toJsonString(registeredEvent));
            }

            // Printing registered events
            List<RegisteredEvent> registeredEvents = getRegisteredEvents();
            Assert.assertThat(registeredEvents.size(), greaterThanOrEqualTo(registrations.size()));
            for (RegisteredEvent registeredEvent: registeredEvents) {
                logger.info("Registered event: {}", Jsons.toJsonString(registeredEvent));
            }

            // Changing severity level for all registered events
            for (RegisteredEvent registeredEvent: registeredEvents) {
                SeverityLevel targetSeverityLevel = SeverityLevel.HIGH;
                logger.info("Changing severity level from {} to {} to registered event: {}",
                        registeredEvent.getSeverityLevel(),
                        targetSeverityLevel,
                        Jsons.toJsonString(registeredEvent));
                final RegisteredEvent changedRegisteredEvent = changeSeverityLevel(registeredEvent.getId(), targetSeverityLevel);
                Assert.assertThat(changedRegisteredEvent.getSeverityLevel(), is(targetSeverityLevel));
                logger.info("Successfully changed severity level to registered event: {}", Jsons.toJsonString(changedRegisteredEvent));
            }

            // Deleting registered events one by one
            for (int i = 0; i < registeredEvents.size(); i++) {
                RegisteredEvent registeredEvent = registeredEvents.get(i);
                logger.info("Deleting event with id: {}", registeredEvent.getId());
                final RegisteredEvent deletedEvent = deleteEvent(registeredEvent.getId());
                logger.info("Deleted event: {}", Jsons.toJsonString(deletedEvent));
            }
            
            Assert.assertThat(getRegisteredEvents().size(), is(0));
        });
    }

    private List<RegisteredEvent> getRegisteredEvents() throws Exception {
        RequestEntity<Void> getRequest = get(new URI(this.uri)).accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<List<RegisteredEvent>> getResponse = restTemplate.exchange(getRequest,
                new ParameterizedTypeReference<List<RegisteredEvent>>() {});

        Assert.assertThat("Wrong status code", getResponse.getStatusCode(), is(HttpStatus.OK));

        return getResponse.getBody();
    }

    private RegisteredEvent deleteEvent(Long eventId) throws Exception {
        ResponseEntity<RegisteredEvent> deleteResponse = restTemplate.exchange(this.uri + "/{id}",
                HttpMethod.DELETE,
                null,
                RegisteredEvent.class,
                eventId);

        final RegisteredEvent result = deleteResponse.getBody();

        Assert.assertThat("Wrong status code", deleteResponse.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat("Event removal operation must return removed event", result, is(not(nullValue())));

        return result;
    }

    private ResponseEntity<RegisteredEvent> executeRegistrationRequest(EventRegistration eventRegistration) throws Exception {
        RequestEntity<EventRegistration> postRequest = post(new URI(this.uri))
                .accept(MediaType.APPLICATION_JSON)
                .body(eventRegistration);
        return restTemplate.exchange(postRequest, RegisteredEvent.class);
    }

    private RegisteredEvent registerEvent(EventRegistration eventRegistration) throws Exception {
        ResponseEntity<RegisteredEvent> postResponse = executeRegistrationRequest(eventRegistration);

        final RegisteredEvent result = postResponse.getBody();

        Assert.assertThat("Wrong status code", postResponse.getStatusCode(), is(HttpStatus.CREATED));
        Assert.assertThat("Event registration operation must return registered event",
                result,
                is(not(nullValue())));

        return result;
    }

    private ResponseEntity<RegisteredEvent> executeEventUpdateRequest(Long eventId, RegisteredEventUpdate eventUpdate) throws Exception {
        RequestEntity<RegisteredEventUpdate> putRequest = put(new URI(this.uri)).body(eventUpdate);
        return restTemplate.exchange(this.uri + "/{id}",
                HttpMethod.PUT,
                putRequest,
                RegisteredEvent.class,
                eventId);
    }

    private RegisteredEvent changeSeverityLevel(Long eventId, SeverityLevel severityLevel) throws Exception {
        RegisteredEventUpdate changeSeverityLevel = new RegisteredEventUpdate();
        changeSeverityLevel.setSeverityLevel(severityLevel);

        ResponseEntity<RegisteredEvent> putResponse = executeEventUpdateRequest(eventId, changeSeverityLevel);

        final RegisteredEvent result = putResponse.getBody();

        Assert.assertThat("Wrong status code", putResponse.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat("Severity level change operation must return changed event",
                result,
                is(not(nullValue())));

        return result;
    }

}
