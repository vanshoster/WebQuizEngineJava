package engine.repository;

import engine.model.Quiz;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuizRepository extends CrudRepository<Quiz, Long> {
    Quiz getQuizById(Long id);

    Boolean existsQuizById(Long id);

    long count();

    List<Quiz> getAllByOrderByIdAsc();
}
