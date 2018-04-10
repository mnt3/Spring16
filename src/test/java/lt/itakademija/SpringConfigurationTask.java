package lt.itakademija;

import lt.itakademija.controller.SecurityServiceController;
import lt.itakademija.grader.Grader;
import lt.itakademija.repository.DateProvider;
import lt.itakademija.repository.SecurityEventsRepository;
import lt.itakademija.repository.SequenceNumberGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Created by mariusg on 2016.12.19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
public class SpringConfigurationTask {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void sequenceNumberGeneratorIsSpringComponent() {
        Grader.graded(1, () -> {
            assertSpringBeanExists(SequenceNumberGenerator.class);
        });
    }

    @Test
    public void securityEventsRepositoryIsSpringComponent() {
        Grader.graded(1, () -> {
            assertSpringBeanExists(SecurityEventsRepository.class);
        });
    }

    @Test
    public void securityServiceControllerIsSpringComponent() {
        Grader.graded(1, () -> {
            assertSpringBeanExists(SecurityServiceController.class);
        });
    }

    @Test
    public void dateTimeProviderIsSpringComponent() {
        Grader.graded(1, () -> {
            assertSpringBeanExists(DateProvider.class);
        });
    }

    private void assertSpringBeanExists(Class<?> beanClass) {
        Assert.assertThat(applicationContext.getBean(beanClass), notNullValue());
    }

}
