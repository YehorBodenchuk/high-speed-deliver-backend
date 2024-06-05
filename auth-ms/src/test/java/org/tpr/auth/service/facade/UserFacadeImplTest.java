package org.tpr.auth.service.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.tpr.auth.controller.converter.impl.UserDtoConverter;
import org.tpr.auth.controller.dto.rabbitmq.QueueRegistrationDto;
import org.tpr.auth.controller.dto.user.UserLoginDto;
import org.tpr.auth.controller.dto.user.UserRegisterDto;
import org.tpr.auth.controller.dto.user.UserUpdateDto;
import org.tpr.auth.controller.dto.user.UserWithTokenDto;
import org.tpr.auth.model.User;
import org.tpr.auth.service.UserService;
import org.tpr.auth.service.facade.impl.UserFacadeImpl;
import org.tpr.auth.service.factory.impl.JwtRefreshTokenFactory;
import org.tpr.auth.service.factory.impl.JwtTokenFactory;
import org.tpr.auth.service.producer.impl.RegistrationProducer;
import org.tpr.auth.util.DataUtil;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@ExtendWith({MockitoExtension.class})
class UserFacadeImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private RegistrationProducer registrationProducer;

    @Mock
    private JwtTokenFactory jwtTokenFactory;

    @Mock
    private JwtRefreshTokenFactory jwtRefreshTokenFactory;

    @Spy
    @SuppressWarnings("unused")
    private UserDtoConverter userDtoConverter;

    @InjectMocks
    private UserFacadeImpl userFacade;

    private final String ACCESS_TOKEN = "access_token";

    private final String REFRESH_TOKEN = "refresh_token";

    @BeforeEach
    void setupToken() {
        BDDMockito.given(jwtTokenFactory.generateToken(any(User.class))).willReturn(ACCESS_TOKEN);
        BDDMockito.given(jwtRefreshTokenFactory.generateToken(any(User.class))).willReturn(REFRESH_TOKEN);
        BDDMockito.given(jwtTokenFactory.extractExpiration(ACCESS_TOKEN)).willReturn(new Date());
        BDDMockito.given(jwtRefreshTokenFactory.extractExpiration(REFRESH_TOKEN)).willReturn(new Date());
    }

    @Test
    @DisplayName("Test register new user and send notify and generate new tokens")
    void givenUserNotPersisted_whenRegister_thenUserWithTokensReturn() {
        UserRegisterDto notPersistedUser = DataUtil.getUserNotRegistered();
        User persistedUser = DataUtil.getUserPersisted();
        BDDMockito.given(userService.register(any(UserRegisterDto.class))).willReturn(persistedUser);
        doNothing().when(registrationProducer).sendMessage(any(QueueRegistrationDto.class));

        UserWithTokenDto obtainedDto = userFacade.register(notPersistedUser);

        Assertions.assertEquals(ACCESS_TOKEN, obtainedDto.getTokens().getAccessToken());
        Assertions.assertEquals(REFRESH_TOKEN, obtainedDto.getTokens().getRefreshToken());
        Assertions.assertEquals(persistedUser.getEmail(), obtainedDto.getUser().getEmail());
        Mockito.verify(registrationProducer).sendMessage(any(QueueRegistrationDto.class));
    }

    @Test
    @DisplayName("Test authenticate user and generate new tokens")
    void givenUserPersisted_whenAuthenticate_thenUserWithTokensReturn() {
        User persistedUser = DataUtil.getUserPersisted();
        UserLoginDto notAuthenticatedUser = DataUtil.getUserNotAuthenticated();
        BDDMockito.given(userService.login(any(Authentication.class))).willReturn(persistedUser);
        BDDMockito.given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willReturn(mock(Authentication.class));

        UserWithTokenDto obtainedDto = userFacade.authenticate(notAuthenticatedUser);

        Assertions.assertEquals(ACCESS_TOKEN, obtainedDto.getTokens().getAccessToken());
        Assertions.assertEquals(REFRESH_TOKEN, obtainedDto.getTokens().getRefreshToken());
        Assertions.assertEquals(persistedUser.getEmail(), obtainedDto.getUser().getEmail());
        Mockito.verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Test update user and generate new tokens")
    void givenUserPersisted_whenUpdate_thenUpdatedUserWithTokensReturn() {
        User persistedUser = DataUtil.getUserPersisted();
        User updatedUser = DataUtil.getUserUpdated();
        UserUpdateDto notUpdatedUser = DataUtil.getUserNotUpdated();
        BDDMockito.given(userService.update(anyString(), any(UserUpdateDto.class))).willReturn(updatedUser);

        UserWithTokenDto obtainedDto = userFacade.update(persistedUser.getEmail(), notUpdatedUser);

        Assertions.assertEquals(ACCESS_TOKEN, obtainedDto.getTokens().getAccessToken());
        Assertions.assertEquals(REFRESH_TOKEN, obtainedDto.getTokens().getRefreshToken());
        Assertions.assertEquals(updatedUser.getEmail(), obtainedDto.getUser().getEmail());
        Assertions.assertNotEquals(persistedUser.getEmail(), obtainedDto.getUser().getEmail());
    }
    
    @Test
    @DisplayName("Test delete user and generate new tokens")
    void givenUserPersisted_whenDelete_thenDeletedUserWithTokensReturn() {
        User persistedUser = DataUtil.getUserPersisted();
        User deletedUser = DataUtil.getUserDeleted();
        BDDMockito.given(userService.delete(persistedUser.getEmail())).willReturn(deletedUser);

        UserWithTokenDto obtainedDto = userFacade.delete(persistedUser.getEmail());

        Assertions.assertEquals(ACCESS_TOKEN, obtainedDto.getTokens().getAccessToken());
        Assertions.assertEquals(REFRESH_TOKEN, obtainedDto.getTokens().getRefreshToken());
        Assertions.assertEquals(deletedUser.getEmail(), obtainedDto.getUser().getEmail());
    }
}
