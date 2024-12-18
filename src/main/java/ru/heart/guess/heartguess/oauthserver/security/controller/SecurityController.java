package ru.heart.guess.heartguess.oauthserver.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/security", produces = "application/json")
public class SecurityController {

    private static final String ACCESS_DENIED_MESSAGE = "Unknown path, return to previous page";

    @GetMapping("/wrongRole")
    public ResponseEntity<String> wrongRoleError() {
        return new ResponseEntity<>(ACCESS_DENIED_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
