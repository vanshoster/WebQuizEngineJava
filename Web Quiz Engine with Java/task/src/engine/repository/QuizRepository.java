package engine.repository;

import engine.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long>,CrudRepository<Quiz, Long> {
    Quiz getQuizById(Long id);

    Boolean existsQuizById(Long id);

    long count();

//    List<Quiz> getAllByOrderByIdAsc();

    long deleteQuizById(Long id);

    Page<Quiz> getAllByOrderByIdAsc(Pageable pageable);
}
