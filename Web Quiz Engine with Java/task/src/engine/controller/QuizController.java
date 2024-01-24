package engine.controller;

import engine.model.AnswerResponse;
import engine.model.Quiz;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class QuizController {
    private final QuizService quizService;
    @Autowired
    QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    //Create new quiz
    @PostMapping("/api/quizzes")
    ResponseEntity<Quiz> postNewQuiz(@RequestBody Quiz quiz) {
        quizService.add(quiz);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    //Get quiz by ID
    @GetMapping("/api/quizzes/{id}")
    ResponseEntity<Quiz> getQuizByID(@PathVariable int id) {
        if (id <= quizService.getQuizListSize()) {
            return new ResponseEntity<>(quizService.getQuizByID(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Get quiz list
    @GetMapping("/api/quizzes")
    ResponseEntity<ArrayList<Quiz>> getQuizList() {
        return new ResponseEntity<>(quizService.getQuizList(), HttpStatus.OK);
    }

    //Post answer index for ID quiz
    @PostMapping("/api/quizzes/{id}/solve")
    ResponseEntity<AnswerResponse> postAnswerForQuizWithID(@PathVariable int id, @RequestParam(name = "answer") int index) {
        System.out.println("Correct answer is: " + quizService.getQuizByID(id).getAnswer() + " for ID: " + id + " your answer is: " + index);
        if (index == quizService.getQuizByID(id).getAnswer()) {
            return new ResponseEntity<>(AnswerResponse.CORRECT, HttpStatus.OK);
        }
        return new ResponseEntity<>(AnswerResponse.WRONG, HttpStatus.OK);
    }
}
