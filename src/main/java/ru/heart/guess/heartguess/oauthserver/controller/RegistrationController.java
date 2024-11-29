package ru.heart.guess.heartguess.oauthserver.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.heart.guess.heartguess.oauthserver.model.NewUser;

@RestController
@RequestMapping(path = "/registration", produces = "application/json")
public class RegistrationController {

    @Autowired
    private UserDetailsManager userDetailsManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createNewUser(@Valid @RequestBody NewUser newUser) {
        if (userDetailsManager.userExists(newUser.getUsername())) {
            return new ResponseEntity<>("Пользователь с таким именем уже существует",
                    HttpStatus.CONFLICT);
        }

        UserDetails userDetails = User.builder()
                .username(newUser.getUsername())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .roles("USER")
                .build();
        userDetailsManager.createUser(userDetails);

        return new ResponseEntity<>("Регистрация прошла успешно",
                HttpStatus.CREATED);
    }
}
