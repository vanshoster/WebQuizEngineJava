package engine.service;

import engine.model.Quiz;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    //Add new quiz to quizzes
    public void add(Quiz quiz) {
        quizRepository.save(quiz);
    }

    //Get quiz by ID
    public Quiz getQuizByID(Long id) {
        return quizRepository.getQuizById(id);
    }

    //Get quizzes size
    public long getQuizListSize() {
        return quizRepository.count();
    }

    //Get quizzes
    public Page<Quiz> getQuizList(Pageable pageable) {
        return quizRepository.getAllByOrderByIdAsc(pageable);
    }

    //Is quiz with id present in quizzes?
    public boolean existsQuizById(Long id) {
        return quizRepository.existsQuizById(id);
    }

    //Delete quiz by id. Return true if quiz is present
    public boolean deleteQuizById(Long id) {
        long isDeleted = quizRepository.deleteQuizById(id);
        return isDeleted > 0;
    }
}
