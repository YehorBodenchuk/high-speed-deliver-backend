package org.tpr.auth.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tpr.auth.controllers.dtos.TokenDto;
import org.tpr.auth.controllers.dtos.UserLoginDto;
import org.tpr.auth.controllers.dtos.UserRegisterDto;
import org.tpr.auth.services.UserFacade;
import org.tpr.auth.services.UserService;

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
        registrationProducer.sendMessage(request.getEmail());

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
