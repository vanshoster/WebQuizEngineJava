package engine.repository;

import engine.model.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {
    Quiz getQuizById(Long id);

    Boolean existsQuizById(Long id);

    long count();

    List<Quiz> getAllByOrderByIdAsc();

    long deleteQuizById(Long id);
}
