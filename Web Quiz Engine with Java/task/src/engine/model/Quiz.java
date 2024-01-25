package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Quiz {
    private static int lastId = 0;
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotNull
    @Size(min = 2)
    private ArrayList<String> options;

    private Set<@Min(value = 0) Integer> answer = new HashSet<>();;

    public Quiz() {
        this.id = ++lastId;
    }

    public Quiz(String title, String text, ArrayList<String> options, Set<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void refreshLastId() {
        --lastId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    @JsonIgnore
    public Set<Integer> getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(Set<Integer> answer) {
        this.answer = answer;
    }
}
