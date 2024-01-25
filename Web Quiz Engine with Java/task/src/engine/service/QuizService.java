package engine.service;

import engine.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class QuizService {
    Map<Integer, Quiz> quizess = new HashMap<>();

    //Add new quiz to QuizList
    public void add(Quiz quiz) {
        System.out.println("Еббучий квиз с ID: " + quiz.getId() + " будет добавлен");
        quizess.put(quiz.getId(), quiz);
    }

    //Get quiz by ID
    public Quiz getQuizByID(int id) {
        return quizess.get(id);
    }

    //Get QuizList size
    public int getQuizListSize() {
        return quizess.size();
    }

    //Get QuizList
    public ArrayList<Quiz> getQuizList() {
        return new ArrayList<>(quizess.values().stream().toList());
    }

    public boolean quizIsPresent(int id) {
        return quizess.containsKey(id);
    }
}
