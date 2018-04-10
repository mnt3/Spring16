package lt.itakademija.repository;

import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Date provider.
 *
 * Created by mariusg on 2016.12.19.
 *
 */
@Repository
public interface DateProvider {

    /**
     * Returns current date.
     *
     * @return current date.
     */
    Date getCurrentDate();

}
