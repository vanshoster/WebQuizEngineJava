package engine.controller;

import engine.model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.util.Arrays.asList;

@RestController
public class QuizController {
    Quiz mockQuiz = new Quiz("The Java Logo", "What is depicted on the Java logo?",
            new ArrayList<>(asList("Robot","Tea leaf","Cup of coffee","Bug")));

    record AnswerStatus(Boolean success, String feedback) {
    }

    AnswerStatus wrongAnswer = new AnswerStatus(false, "Wrong answer! Please, try again.");
    AnswerStatus correctAnswer = new AnswerStatus(true, "Congratulations, you're right!");

    @GetMapping("/api/quiz")
    ResponseEntity<Quiz> getQuiz() {
        return new ResponseEntity<>(mockQuiz,HttpStatus.OK);
    }

    @PostMapping("/api/quiz")
    ResponseEntity<AnswerStatus> checkAnswer(@RequestParam(name = "answer") int answer) {
        if (answer == 2) {
            return new ResponseEntity<>(correctAnswer, HttpStatus.OK);
        }
        return new ResponseEntity<>(wrongAnswer, HttpStatus.OK);
    }
}
