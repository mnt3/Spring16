package lt.itakademija.matchers;

import lt.itakademija.model.RegisteredEvent;
import lt.itakademija.util.Jsons;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public final class RegisteredEventMatcher extends BaseMatcher<RegisteredEvent> {

    private final RegisteredEvent expectedEvent;

    public RegisteredEventMatcher(RegisteredEvent expectedEvent) {
        this.expectedEvent = expectedEvent;
    }

    @Override
    public boolean matches(Object item) {
        RegisteredEvent registeredEvent = (RegisteredEvent) item;

        return registeredEvent.equals(expectedEvent);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Registered event expected to contain these values: ");
        description.appendValue(Jsons.toJsonString(expectedEvent));
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("was: ");
        description.appendValue(Jsons.toJsonString(item));
    }

}
