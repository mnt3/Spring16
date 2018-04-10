package lt.itakademija;

import lt.itakademija.grader.Grade;
import lt.itakademija.grader.Grader;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by mariusg on 2016.12.19.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        JavaCodingTask.class,
        SpringConfigurationTask.class,
        RestServicesTask.class,
        SwaggerDocTask.class
})
public class GradedSuiteIT {

    @AfterClass
    public static void printGrades() {
        Grade g = Grader.getCurrentGrade();

        System.out.println(String.format("\n\n\n" +
                "Score:         %s/%s \n" +
                "Average grade: %s \n" +
                "Grade:         %s\n\n\n", g.getScore(), g.getTotal(), g.getAverageGrade(), g.getGrade()));
    }

}
