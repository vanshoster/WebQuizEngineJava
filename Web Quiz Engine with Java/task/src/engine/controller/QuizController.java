package engine.controller;

import engine.model.*;
import engine.service.QuizService;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@RestController
@Validated
public class QuizController {

    private static final String PATH = "api/quizzes";
    private final QuizService quizService;
    private final UserService userService;
    @Autowired
    QuizController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    //Create new quiz
    @PostMapping(PATH)
    ResponseEntity<Quiz> postNewQuiz(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid Quiz quiz) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getUserByEmail(userDetails.getUsername());
        quiz.setCreator(user);
        quizService.add(quiz);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    //Get quiz by ID
    @GetMapping(PATH + "/{id}")
    ResponseEntity<Quiz> getQuizByID(@PathVariable Long id) {
        if (quizService.existsQuizById(id)) {
            return new ResponseEntity<>(quizService.getQuizByID(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete quiz by ID
    @DeleteMapping(PATH + "/{id}")
    ResponseEntity<HttpStatus> deleteQuizById(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!quizService.existsQuizById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Quiz quiz = quizService.getQuizByID(id);
        boolean isCreator = Objects.equals(userDetails.getUsername().toLowerCase(), quiz.getCreator().getEmail());
        if (!isCreator) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        quizService.deleteQuizById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Get quiz list
    @GetMapping(PATH)
    ResponseEntity<List<Quiz>> getQuizList() {
        return new ResponseEntity<>(quizService.getQuizList(), HttpStatus.OK);
    }

    //Post answer index for ID quiz
    @PostMapping(PATH + "/{id}/solve")
    ResponseEntity<AnswerResponse> postAnswerForQuizWithID(@PathVariable Long id, @RequestBody @Valid AnswerWrapper answerWrapper) {
        if (!quizService.existsQuizById(id)) {
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
