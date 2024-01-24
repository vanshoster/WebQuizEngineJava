package engine.service;

import engine.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.util.Arrays.asList;

@Service
public class QuizService {
    ArrayList<Quiz> quizList = new ArrayList<>();

    //Add new quiz to QuizList
    public void add(Quiz quiz) {
        quizList.add(quiz);
    }

    //Get quiz by ID
    public Quiz getQuizByID(int id) {
        return quizList.get(id-1);
    }

    //Get QuizList size
    public int getQuizListSize() {
        return quizList.size();
    }

    //Get QuizList
    public ArrayList<Quiz> getQuizList() {
        return quizList;
    }
}
