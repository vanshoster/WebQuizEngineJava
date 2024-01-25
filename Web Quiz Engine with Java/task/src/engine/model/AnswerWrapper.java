package engine.model;


import javax.validation.constraints.Min;
import java.util.Set;

public class AnswerWrapper {
    private Set<@Min(value = 0) Integer> answer;

    public Set<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(Set<Integer> answer) {
        this.answer = answer;
    }
}

