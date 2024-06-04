package org.tpr.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tpr.auth.controller.converter.impl.UserDtoConverter;
import org.tpr.auth.controller.dto.parcel.ParcelDto;
import org.tpr.auth.controller.dto.user.*;
import org.tpr.auth.model.User;
import org.tpr.auth.service.facade.UserFacade;
import org.tpr.auth.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserFacade userFacade;

    private final UserDtoConverter userDtoConverter;

    private final UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<UserWithTokenDto> register(@Valid @RequestBody UserRegisterDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userFacade.register(request));
    }

    @PostMapping("/public/login")
    public ResponseEntity<UserWithTokenDto> login(@Valid @RequestBody UserLoginDto request) {
        return ResponseEntity.ok(userFacade.authenticate(request));
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/me/update")
    public ResponseEntity<UserWithTokenDto> updateMe(@Valid @RequestBody UserUpdateDto request) {
        return ResponseEntity.ok(userFacade.update(getPrincipal().getEmail(), request));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/me/delete")
    public ResponseEntity<UserWithTokenDto> deleteMe() {
        return ResponseEntity.ok(userFacade.delete(getPrincipal().getEmail()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/my/parcels")
    public ResponseEntity<Collection<ParcelDto>> myParcels() {
        return ResponseEntity.ok(userFacade.getUserParcels(getPrincipal().getEmail()));
    }

    private User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
