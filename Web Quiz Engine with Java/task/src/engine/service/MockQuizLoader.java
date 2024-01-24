//package engine.service;
//
//import engine.model.Quiz;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//
//import static java.util.Arrays.asList;
//
//@Component
//public class MockQuizLoader {
//    private QuizService quizService;
//
//    public static final Quiz mockQuiz = new Quiz("The Java Logo", "What is depicted on the Java logo?",
//            new ArrayList<>(asList("Robot", "Tea leaf", "Cup of coffee", "Bug")), 2);
//
//    @Autowired
//    MockQuizLoader(QuizService quizService) {
//        this.quizService = quizService;
//        quizService.add(mockQuiz);
//    }
//}
