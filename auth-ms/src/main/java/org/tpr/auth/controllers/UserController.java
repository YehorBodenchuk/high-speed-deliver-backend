package org.tpr.auth.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tpr.auth.controllers.dtos.TokenDto;
import org.tpr.auth.controllers.dtos.UserRegisterDto;
import org.tpr.auth.services.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@Valid @RequestBody UserRegisterDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }
}
