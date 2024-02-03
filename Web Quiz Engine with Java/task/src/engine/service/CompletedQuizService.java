package engine.service;

import engine.model.CompletedQuiz;
import engine.repository.CompletedQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CompletedQuizService {
    private final CompletedQuizRepository completedQuizRepository;
    @Autowired
    CompletedQuizService(CompletedQuizRepository completedQuizRepository) {
        this.completedQuizRepository = completedQuizRepository;
    }

    //Add new quiz to quizzes
    public void add(CompletedQuiz completedQuiz) {
        completedQuizRepository.save(completedQuiz);
    }

    //Get pageable list of completed quizzes
    public Page<CompletedQuiz> getCompletedQuizList(long userId, Pageable pageable) {
        return completedQuizRepository.getByUserIdOrderByCompletedAtDesc(userId, pageable);
    }

    public List<CompletedQuiz> getCompletedQuizListTest(long userId) {
        return completedQuizRepository.getByUserIdOrderByCompletedAtDesc(userId);
    }


}
