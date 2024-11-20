package ru.heart.guess.heartguess.oauthserver.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createNewUser(@Valid @RequestBody NewUser newUser) {
        if (userDetailsManager.userExists(newUser.getUsername())) {
            return;
        }
        UserDetails userDetails = User.builder()
                .username(newUser.getUsername())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .roles("USER")
                .build();
        userDetailsManager.createUser(userDetails);
    }
}
