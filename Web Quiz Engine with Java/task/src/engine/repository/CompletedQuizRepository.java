package engine.repository;

import engine.model.CompletedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompletedQuizRepository extends PagingAndSortingRepository<CompletedQuiz, Long>, CrudRepository<CompletedQuiz, Long> {
    Page<CompletedQuiz> getByUserIdOrderByCompletedAtDesc(long userId, Pageable pageable);
    List<CompletedQuiz> getByUserIdOrderByCompletedAtDesc(long userId);
}
