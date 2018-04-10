package lt.itakademija.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mariusg on 2016.12.19.
 */
@Component
public class SimpleDateProvider implements DateProvider {



    private Date data = new Date();

    public SimpleDateProvider() {
    }



    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public Date getCurrentDate() {
if (data != null){
    return data;
}
else{
        throw new UnsupportedOperationException("not implemented");
    }}

}
