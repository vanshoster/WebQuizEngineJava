package engine.controller;

import engine.model.User;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
public class UserController {
    private static final String PATH = "api/register";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    //Register new user
    @PostMapping(PATH)
    ResponseEntity<HttpStatus> registerNewUser(@RequestBody @Valid User user) {
        if (userService.existsUserByEmail(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
