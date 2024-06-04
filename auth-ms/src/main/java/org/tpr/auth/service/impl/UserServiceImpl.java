package org.tpr.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tpr.auth.controller.dto.user.UserRegisterDto;
import org.tpr.auth.controller.dto.user.UserUpdateDto;
import org.tpr.auth.controller.exception.UserNotFoundException;
import org.tpr.auth.model.User;
import org.tpr.auth.model.enums.UserRole;
import org.tpr.auth.repositorie.UserRepository;
import org.tpr.auth.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> {
            log.error(String.format("User with email: %s not found!", username));
            return new UserNotFoundException(username);
        });
    }

    @Override
    public User register(UserRegisterDto request) {
        User user = User.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .roles(List.of(UserRole.USER))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);

        log.info(String.format("New user with email %s was successfully registered.", user.getEmail()));

        return user;
    }

    @Override
    public User login(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        log.info(String.format("User with email %s was successfully login.", user.getEmail()));

        return user;
    }

    @Override
    public User delete(String email) {
        User user = (User) loadUserByUsername(email);
        user.setArchiveDate(new Date());

        user = userRepository.save(user);
        log.info(String.format("Account with email: %s was successfully archived", user.getEmail()));

        return user;
    }

    @Override
    public User update(String email, UserUpdateDto request) {
        User user = (User) loadUserByUsername(email);

        if (Objects.nonNull(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }

        if (Objects.nonNull(request.getLastName())) {
            user.setLastName(request.getLastName());
        }

        if (Objects.nonNull(request.getEmail())) {
            user.setEmail(request.getEmail());
        }

        if (Objects.nonNull(request.getPhone())) {
            user.setPhone(request.getPhone());
        }

        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user = userRepository.save(user);
        log.info(String.format("User with email: %s was successfully updated", email));

        return user;
    }
}
