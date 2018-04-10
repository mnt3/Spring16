package lt.itakademija.repository;

import org.springframework.stereotype.Repository;

/**
 * Represents sequence number generator.
 * 
 * @author mariusg
 */
@Repository
public interface SequenceNumberGenerator {

    /**
     * Returns the next number in a sequence.
     * <p>
     * First number in a sequence is 1.
     *
     * @return next sequence number.
     */
    Long getNext();

}
