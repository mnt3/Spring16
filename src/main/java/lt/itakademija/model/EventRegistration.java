package lt.itakademija.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public final class EventRegistration {

    @NotNull
    private SeverityLevel severityLevel;

    @NotNull
    @Max(100)
    private String location;

    @NotNull
    @Max(1000)
    private String description;

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(SeverityLevel severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRegistration that = (EventRegistration) o;
        return severityLevel == that.severityLevel &&
                Objects.equals(location, that.location) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(severityLevel, location, description);
    }

    @Override
    public String toString() {
        return "EventRegistration [severityLevel=" + severityLevel + ", location=" + location + ", description="
                + description + "]";
    }

}
