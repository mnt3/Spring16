package lt.itakademija.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lt.itakademija.model.EventRegistration;
import lt.itakademija.model.RegisteredEvent;
import lt.itakademija.model.RegisteredEventUpdate;
import lt.itakademija.repository.SecurityEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@Api(value = "events")
@RequestMapping(value = "/webapi/events")
public class SecurityServiceController {
    @Autowired
    private final SecurityEventsRepository repository;

    public SecurityServiceController(final SecurityEventsRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Get events", notes = "Returns events.")
    @RequestMapping(method = RequestMethod.GET)
    public List<RegisteredEvent> getRegisteredEvents() {

       return repository.getEvents();
       // throw new UnsupportedOperationException("not implemented");
    }
    @ApiOperation(value = "Get users", notes = "Returns registered users.")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RegisteredEvent createEvent(@RequestBody @Valid final EventRegistration registrationData) {
       return repository.create(registrationData);

        //throw new UnsupportedOperationException("not implemented");
    }
    @ApiOperation(value = "Delete event",
            notes = "you delete event")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public RegisteredEvent deleteEvent(@ApiParam(value = "Id", required = true)@PathVariable final Long id) {
        return repository.delete(id);
        //throw new UnsupportedOperationException("not implemented");
    }
    @ApiOperation(value = "Updated event",
            notes = "you update event")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public RegisteredEvent updateEvent(@PathVariable final Long id,@Valid @RequestBody final RegisteredEventUpdate updateData) {
        return repository.update(id,updateData);
        //throw new UnsupportedOperationException("not implemented");
    }

}
