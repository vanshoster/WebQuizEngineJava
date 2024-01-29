package engine.repository;

import engine.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserById(Long Id);
    User getUsersByEmailIgnoreCase(String email);
    boolean existsUserByEmailIgnoreCase(String email);
}
