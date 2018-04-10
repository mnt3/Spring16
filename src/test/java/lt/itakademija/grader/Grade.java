package lt.itakademija.grader;

import java.math.BigDecimal;

public class Grade {

    private final Integer total;
    private final Integer score;
    private final BigDecimal averageGrade;
    private final Integer grade;

    public Grade(Integer total, Integer score, BigDecimal averageGrade, Integer grade) {
        this.total = total;
        this.score = score;
        this.averageGrade = averageGrade;
        this.grade = grade;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getScore() {
        return score;
    }

    public BigDecimal getAverageGrade() {
        return averageGrade;
    }

    public Integer getGrade() {
        return grade;
    }
}