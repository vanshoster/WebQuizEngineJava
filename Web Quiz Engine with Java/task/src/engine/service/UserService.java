package engine.service;

import engine.model.User;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //Crete new user
    public void add(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        userRepository.save(user);
    }

    //Get user by email ignore case
    public User getUserByEmail(String email) {
        User user = userRepository.getUsersByEmailIgnoreCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    //Is user exists in users?
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmailIgnoreCase(email);
    }
}
