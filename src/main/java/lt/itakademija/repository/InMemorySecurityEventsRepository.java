package lt.itakademija.repository;

import lt.itakademija.model.EventRegistration;
import lt.itakademija.model.RegisteredEvent;
import lt.itakademija.model.RegisteredEventUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * In-memory security events repository. Internally, it uses {@link SequenceNumberGenerator} and {@link DateProvider}.
 *
 * Created by mariusg on 2016.12.19.
 */
@Repository
public final class InMemorySecurityEventsRepository implements SecurityEventsRepository {

    @Autowired
    private final SequenceNumberGenerator sequenceGenerator;
    @Autowired
    private final DateProvider dateProvider;

    private List<RegisteredEvent> eventList=new ArrayList<>();

    public InMemorySecurityEventsRepository(SequenceNumberGenerator sequenceGenerator, DateProvider dateProvider) {
        this.sequenceGenerator = sequenceGenerator;
        this.dateProvider = dateProvider;
    }





    /*
     *  Notes for implementation:
     *  
     *  This method must use SequenceNumberGenerator#getNext() for generating ID;
     *  This method must use DateProvider#getCurrentDate() for getting dates;
     */
    @Override
    public RegisteredEvent create(EventRegistration eventRegistration) {

        RegisteredEvent eventas= new RegisteredEvent(sequenceGenerator.getNext(),dateProvider.getCurrentDate(),eventRegistration.getSeverityLevel(),eventRegistration.getLocation(),eventRegistration.getDescription());
        this.eventList.add(eventas);
        return eventas;}


    @Override
    public List<RegisteredEvent> getEvents() {
return this.eventList;

      //  throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public RegisteredEvent delete(Long id) {


        for(RegisteredEvent eventukas:eventList){
            if(eventukas.getId()==id){
                 eventList.remove(eventukas);
                return eventukas;
            }
        }

        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public RegisteredEvent update(Long id, RegisteredEventUpdate registeredEventUpdate) {





        for(RegisteredEvent eventukas:eventList){
            if(eventukas.getId()==id){
                eventList.remove(eventukas);
                RegisteredEvent eventas= new RegisteredEvent(sequenceGenerator.getNext(),dateProvider.getCurrentDate(),registeredEventUpdate.getSeverityLevel(),eventukas.getLocation(),eventukas.getDescription());
                eventList.add(eventas);
                return eventas;
            }
        }

        throw new UnsupportedOperationException("not implemented");
    }

}
