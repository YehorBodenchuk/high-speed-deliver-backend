package org.tpr.auth.service.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tpr.auth.controller.dtos.QueueRegistrationDto;
import org.tpr.auth.controller.dtos.TokenDto;
import org.tpr.auth.controller.dtos.UserLoginDto;
import org.tpr.auth.controller.dtos.UserRegisterDto;
import org.tpr.auth.service.facade.UserFacade;
import org.tpr.auth.service.UserService;
import org.tpr.auth.service.producer.impl.RegistrationProducer;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFacadeImpl implements UserFacade {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final RegistrationProducer registrationProducer;


    @Override
    public TokenDto registerAndNotify(UserRegisterDto request) {
        log.info(String.format("Trying to register new user with email: %s", request.getEmail()));
        TokenDto token = userService.register(request);

        log.info(String.format("Send broker message to notify user with email: %s", request.getEmail()));
        QueueRegistrationDto registrationDto = QueueRegistrationDto.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .build();
        registrationProducer.sendMessage(registrationDto);

        return token;
    }

    @Override
    public TokenDto authenticateAndLogin(UserLoginDto request) {
        log.info(String.format("Trying to authenticate user with email: %s", request.getEmail()));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        return userService.login(authentication);
    }
}
