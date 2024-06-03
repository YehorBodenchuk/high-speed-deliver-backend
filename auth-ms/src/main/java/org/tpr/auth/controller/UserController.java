package org.tpr.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tpr.auth.controller.converters.UserDtoConverter;
import org.tpr.auth.controller.dtos.TokenDto;
import org.tpr.auth.controller.dtos.UserDto;
import org.tpr.auth.controller.dtos.UserLoginDto;
import org.tpr.auth.controller.dtos.UserRegisterDto;
import org.tpr.auth.model.User;
import org.tpr.auth.service.facade.UserFacade;
import org.tpr.auth.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserFacade userFacade;

    private final UserDtoConverter userDtoConverter;

    private final UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<TokenDto> register(@Valid @RequestBody UserRegisterDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userFacade.registerAndNotify(request));
    }

    @PostMapping("/public/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody UserLoginDto request) {
        return ResponseEntity.ok(userFacade.authenticateAndLogin(request));
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

    private User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
