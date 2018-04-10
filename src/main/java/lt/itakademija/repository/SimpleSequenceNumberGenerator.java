package lt.itakademija.repository;

import com.sun.javafx.beans.IDProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mariusg on 2016.12.19.
 */
@Component
public final class SimpleSequenceNumberGenerator implements SequenceNumberGenerator {


private Long skaicius =0L;
    @Override
    public Long getNext() {

if (skaicius != null){
    skaicius++;
    return skaicius;
}
else{
        throw new UnsupportedOperationException("not implemented");
    }}

}
