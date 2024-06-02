package org.tpr.auth.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tpr.auth.controllers.converters.UserDtoConverter;
import org.tpr.auth.controllers.dtos.TokenDto;
import org.tpr.auth.controllers.dtos.UserDto;
import org.tpr.auth.controllers.dtos.UserLoginDto;
import org.tpr.auth.controllers.dtos.UserRegisterDto;
import org.tpr.auth.models.User;
import org.tpr.auth.services.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    private final UserDtoConverter userDtoConverter;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/public/register")
    public ResponseEntity<TokenDto> register(@Valid @RequestBody UserRegisterDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @PostMapping("/public/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody UserLoginDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        return ResponseEntity.ok(userService.login(authentication));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        return ResponseEntity.ok(
                userDtoConverter.convertToUserDto(
                        (User) userService.loadUserByUsername(getPrincipal().getUsername())
                )
        );
    }

    @GetMapping("/some")
    public String some() {
        return "Aunticated!";
    }

    private User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
