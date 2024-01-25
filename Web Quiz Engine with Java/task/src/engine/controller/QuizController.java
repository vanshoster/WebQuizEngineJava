package engine.controller;

import engine.model.AnswerResponse;
import engine.model.AnswerWrapper;
import engine.model.Quiz;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

@RestController
@Validated
public class QuizController {

    private static final String PATH = "api/quizzes";
    private final QuizService quizService;
    @Autowired
    QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    //Create new quiz
    @PostMapping(PATH)
    ResponseEntity<Quiz> postNewQuiz(@RequestBody @Valid Quiz quiz) {
        quizService.add(quiz);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    //Get quiz by ID
    @GetMapping(PATH + "/{id}")
    ResponseEntity<Quiz> getQuizByID(@PathVariable int id) {
        if (quizService.quizIsPresent(id)) {
            return new ResponseEntity<>(quizService.getQuizByID(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Get quiz list
    @GetMapping(PATH)
    ResponseEntity<ArrayList<Quiz>> getQuizList() {
        return new ResponseEntity<>(quizService.getQuizList(), HttpStatus.OK);
    }

    //Post answer index for ID quiz
    @PostMapping(PATH + "/{id}/solve")
    ResponseEntity<AnswerResponse> postAnswerForQuizWithID(@PathVariable int id, @RequestBody @Valid AnswerWrapper answerWrapper) {
        if (!quizService.quizIsPresent(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Integer> answer = answerWrapper.getAnswer();
        System.out.println("Correct answer is: " + quizService.getQuizByID(id).getAnswer() + " for ID: " + id + " your answer is: " + answer);
        if (answer.equals(quizService.getQuizByID(id).getAnswer())) {
            return new ResponseEntity<>(AnswerResponse.CORRECT, HttpStatus.OK);
        }
        return new ResponseEntity<>(AnswerResponse.WRONG, HttpStatus.OK);
    }
}
