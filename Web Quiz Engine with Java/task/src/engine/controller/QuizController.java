package engine.controller;

import engine.model.*;
import engine.service.CompletedQuizService;
import engine.service.QuizService;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;


@RestController
@Validated
public class QuizController {

    private static final String PATH = "api/quizzes";
    private final QuizService quizService;
    private final UserService userService;
    private final CompletedQuizService completedQuizService;
    @Autowired
    QuizController(QuizService quizService, UserService userService, CompletedQuizService completedQuizService) {
        this.quizService = quizService;
        this.userService = userService;
        this.completedQuizService = completedQuizService;
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

    //Get quiz list by page number
    @GetMapping(PATH)
    ResponseEntity<Page<Quiz>> getQuizList(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);

        return new ResponseEntity<>(quizService.getQuizList(pageable), HttpStatus.OK);
    }


    //Post answer index for ID quiz
    @PostMapping(PATH + "/{id}/solve")
    ResponseEntity<AnswerResponse> postAnswerForQuizWithID(@PathVariable Long id, @RequestBody @Valid AnswerWrapper answerWrapper, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!quizService.existsQuizById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Integer> answer = answerWrapper.getAnswer();
        Quiz quiz = quizService.getQuizByID(id);
        System.out.println("Correct answer is: " + quiz.getAnswer() + " for ID: " + id + " your answer is: " + answer);
        if (answer.equals(quiz.getAnswer())) {
            CompletedQuiz completedQuiz = new CompletedQuiz(OffsetDateTime.now());
            completedQuiz.setQuiz(quiz);
            User user = userService.getUserByEmail(userDetails.getUsername());
            completedQuiz.setUser(user);
            completedQuizService.add(completedQuiz);
            userService.addCompletedQuiz(completedQuiz, user);
            return new ResponseEntity<>(AnswerResponse.CORRECT, HttpStatus.OK);
        }
        return new ResponseEntity<>(AnswerResponse.WRONG, HttpStatus.OK);
    }

    //Get completed quiz list by page number
    @GetMapping(PATH + "/completed")
    ResponseEntity<Page<CompletedQuiz>> getCompletedQuizList(@RequestParam int page, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Pageable pageable = PageRequest.of(page , 10);
        User user = userService.getUserByEmail(userDetails.getUsername());
        return new ResponseEntity<>(completedQuizService.getCompletedQuizList(user.getId(), pageable), HttpStatus.OK);
    }

}
