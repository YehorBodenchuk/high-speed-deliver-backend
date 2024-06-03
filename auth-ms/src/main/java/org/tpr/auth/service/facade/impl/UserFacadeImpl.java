package org.tpr.auth.service.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tpr.auth.controller.converters.impl.UserDtoConverter;
import org.tpr.auth.controller.dtos.*;
import org.tpr.auth.model.User;
import org.tpr.auth.service.facade.UserFacade;
import org.tpr.auth.service.UserService;
import org.tpr.auth.service.factory.impl.JwtRefreshTokenFactory;
import org.tpr.auth.service.factory.impl.JwtTokenFactory;
import org.tpr.auth.service.producer.impl.RegistrationProducer;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFacadeImpl implements UserFacade {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final RegistrationProducer registrationProducer;

    private final JwtTokenFactory jwtTokenFactory;

    private final JwtRefreshTokenFactory jwtRefreshTokenFactory;

    private final UserDtoConverter userDtoConverter;

    @Override
    public UserWithTokenDto register(UserRegisterDto request) {
        log.info(String.format("Trying to register new user with email: %s", request.getEmail()));
        User user = userService.register(request);

        log.info(String.format("Send broker message to notify user with email: %s", request.getEmail()));
        QueueRegistrationDto registrationDto = QueueRegistrationDto.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .build();
        registrationProducer.sendMessage(registrationDto);

        return UserWithTokenDto.builder()
                .user(userDtoConverter.convertToUserDto(user))
                .tokens(generateAllTokens(user))
                .build();
    }

    @Override
    public UserWithTokenDto authenticate(UserLoginDto request) {
        log.info(String.format("Trying to authenticate user with email: %s", request.getEmail()));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        User user = userService.login(authentication);

        return UserWithTokenDto.builder()
                .user(userDtoConverter.convertToUserDto(user))
                .tokens(generateAllTokens(user))
                .build();
    }

    @Override
    public UserWithTokenDto update(String email, UserUpdateDto request) {
        log.info(String.format("Updating user with email: %s", email));
        User user = userService.update(email, request);

        return UserWithTokenDto.builder()
                .user(userDtoConverter.convertToUserDto(user))
                .tokens(generateAllTokens(user))
                .build();
    }

    @Override
    public UserWithTokenDto delete(String email) {
        log.info(String.format("Archiving account with email: %s", email));
        User user = userService.delete(email);

        return UserWithTokenDto.builder()
                .user(userDtoConverter.convertToUserDto(user))
                .tokens(generateAllTokens(user))
                .build();
    }

    private TokenDto generateAllTokens(User user) {
        log.info(String.format("Generating tokens for user with email: %s", user.getEmail()));
        String accessToken = jwtTokenFactory.generateToken(user);
        String refreshToken = jwtRefreshTokenFactory.generateToken(user);
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpire(jwtTokenFactory.extractExpiration(accessToken).getTime())
                .refreshTokenExpire(jwtRefreshTokenFactory.extractExpiration(refreshToken).getTime())
                .build();
    }
}
