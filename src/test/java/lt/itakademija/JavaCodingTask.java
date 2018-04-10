package lt.itakademija;

import lt.itakademija.grader.Grader;
import lt.itakademija.matchers.RegisteredEventMatcher;
import lt.itakademija.model.EventRegistration;
import lt.itakademija.model.RegisteredEvent;
import lt.itakademija.model.RegisteredEventUpdate;
import lt.itakademija.model.SeverityLevel;
import lt.itakademija.repository.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static lt.itakademija.model.SeverityLevel.HIGH;
import static lt.itakademija.model.SeverityLevel.LOW;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.*;

/**
 * Created by mariusg on 2016.12.19.
 */
public final class JavaCodingTask {

    @Test
    public void sequenceNumberGeneratorWorksCorrectly() {
        Grader.graded(1, () -> {
            // Setup
            SequenceNumberGenerator sut = new SimpleSequenceNumberGenerator();

            // Exercise & verify
            for (int i = 1; i <= 10; i++) {
                long id = sut.getNext();
                Assert.assertThat(id, is((long) i));
            }
        });
    }

    @Test
    public void securityEventsRepositoryCreatesEventCorrectly() {
        Grader.graded(1, () -> {
            // Setup
            final long expectedId = 1L;
            SequenceNumberGenerator seqGen = mock(SequenceNumberGenerator.class);
            when(seqGen.getNext()).thenReturn(expectedId);

            DateProvider dateProvider = mock(DateProvider.class);
            when(dateProvider.getCurrentDate()).thenReturn(new Date());
            SecurityEventsRepository sut = new InMemorySecurityEventsRepository(seqGen, dateProvider);

            // Exercise
            EventRegistration eventRegistration = createEventRegistration(HIGH, "Test", "Test");
            RegisteredEvent createdEvent = sut.create(eventRegistration);

            // Verify
            verify(seqGen, times(1)).getNext();
            verify(dateProvider, times(1)).getCurrentDate();
            Assert.assertThat(sut.getEvents().size(), is(1));
            Assert.assertThat(createdEvent, new RegisteredEventMatcher(new RegisteredEvent(expectedId,
                    dateProvider.getCurrentDate(),
                    eventRegistration.getSeverityLevel(),
                    eventRegistration.getLocation(),
                    eventRegistration.getDescription())));
        });
    }

    @Test
    public void securityEventsRepositoryDeletesEventCorrectly() {
        Grader.graded(1, () -> {
            // Setup
            final long expectedId = 2L;
            SequenceNumberGenerator seqGen = mock(SequenceNumberGenerator.class);
            when(seqGen.getNext()).thenReturn(expectedId);

            DateProvider dateProvider = mock(DateProvider.class);
            when(dateProvider.getCurrentDate()).thenReturn(new Date());
            SecurityEventsRepository sut = new InMemorySecurityEventsRepository(seqGen, dateProvider);

            // Exercise
            EventRegistration eventRegistration = createEventRegistration(HIGH, "Test", "Test");
            RegisteredEvent createdEvent = sut.create(eventRegistration);
            final RegisteredEvent deletedEvent = sut.delete(expectedId);

            // Verify
            Assert.assertThat(createdEvent, is(deletedEvent));
            Assert.assertThat(sut.getEvents().size(), is(0));
        });
    }

    @Test
    public void securityEventsRepositoryUpdatesEventCorrectly() {
        Grader.graded(1, () -> {
            // Setup
            final long expectedId = 3L;
            SequenceNumberGenerator seqGen = mock(SequenceNumberGenerator.class);
            when(seqGen.getNext()).thenReturn(expectedId);

            DateProvider dateProvider = mock(DateProvider.class);
            when(dateProvider.getCurrentDate()).thenReturn(new Date());
            SecurityEventsRepository sut = new InMemorySecurityEventsRepository(seqGen, dateProvider);

            // Exercise
            EventRegistration eventRegistration = createEventRegistration(HIGH, "Test", "Test");
            sut.create(eventRegistration);

            RegisteredEventUpdate eventUpdate = new RegisteredEventUpdate();
            eventUpdate.setSeverityLevel(LOW);
            RegisteredEvent updatedEvent = sut.update(expectedId, eventUpdate);

            // Verify
            Assert.assertThat(updatedEvent, new RegisteredEventMatcher(new RegisteredEvent(expectedId,
                    dateProvider.getCurrentDate(),
                    LOW,
                    eventRegistration.getLocation(),
                    eventRegistration.getDescription())));
        });
    }

    @Test
    public void securityEventsRepositoryReturnsAllRegisteredEventCorrectly() {
        Grader.graded(1, () -> {
            // Setup
            final long expectedId = 0L;
            SequenceNumberGenerator seqGen = mock(SequenceNumberGenerator.class);
            when(seqGen.getNext()).thenReturn(expectedId);

            DateProvider dateProvider = mock(DateProvider.class);
            when(dateProvider.getCurrentDate()).thenReturn(new Date());
            SecurityEventsRepository sut = new InMemorySecurityEventsRepository(seqGen, dateProvider);

            List<EventRegistration> eventRegistrations = new LinkedList<>();
            eventRegistrations.add(createEventRegistration(HIGH, "Test 1", "Test 1"));
            eventRegistrations.add(createEventRegistration(HIGH, "Test 2", "Test 2"));
            eventRegistrations.add(createEventRegistration(HIGH, "Test 3", "Test 3"));

            // Exercise
            List<RegisteredEvent> registeredEvents = new ArrayList<>(eventRegistrations.size());
            for (EventRegistration registration : eventRegistrations) {
                registeredEvents.add(sut.create(registration));
            }

            // Verify
            Assert.assertThat(sut.getEvents(), is(registeredEvents));
        });
    }

    @Test
    public void dateProviderWorksCorrectly() {
        Grader.graded(1, () -> {
            // Setup
            DateProvider sut = new SimpleDateProvider();

            // Exercise & verify
            Assert.assertThat(sut.getCurrentDate(), notNullValue());
        });
    }

    private EventRegistration createEventRegistration(SeverityLevel level, String location, String description) {
        EventRegistration registration = new EventRegistration();
        registration.setSeverityLevel(level);
        registration.setLocation(location);
        registration.setDescription(description);

        return registration;
    }

}
