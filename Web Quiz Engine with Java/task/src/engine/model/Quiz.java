package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Quiz {
    private static int lastId = 0;
    private int id;
    private String title;
    private String text;
    private ArrayList<String> options;
    private int answer;

    public Quiz() {
        this.id = ++lastId;
    }

    public Quiz(String title, String text, ArrayList<String> options, int answer) {
        this.id = ++lastId;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
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
    public int getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
