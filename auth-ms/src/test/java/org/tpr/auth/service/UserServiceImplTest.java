package org.tpr.auth.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tpr.auth.controller.dto.user.UserRegisterDto;
import org.tpr.auth.controller.dto.user.UserUpdateDto;
import org.tpr.auth.controller.exception.UserNotFoundException;
import org.tpr.auth.model.User;
import org.tpr.auth.repository.UserRepository;
import org.tpr.auth.util.DataUtil;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

@ExtendWith({MockitoExtension.class})
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Test load user by username (email)")
    void givenUserPersisted_whenLoadUserByUserName_thenUserReturned() {
        User persistedUser = DataUtil.getUserPersisted();
        BDDMockito.given(userRepository.findByEmail(anyString())).willReturn(Optional.of(persistedUser));

        UserDetails obtainedUser = userService.loadUserByUsername(persistedUser.getUsername());

        Assertions.assertInstanceOf(User.class, obtainedUser);
        Assertions.assertEquals(persistedUser.getEmail(), obtainedUser.getUsername());
    }

    @Test
    @DisplayName("Test load user by username (email) that didn't exist")
    void givenUserNotPersisted_whenLoadUserByUserName_thenUserNotFoundErrorOccur() {
        User notPersistedUser = DataUtil.getUserNotPersisted();
        BDDMockito.given(userRepository.findByEmail(notPersistedUser.getUsername())).willThrow(UserNotFoundException.class);

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.loadUserByUsername(notPersistedUser.getUsername()));
    }

    @Test
    @DisplayName("Test register new user")
    void givenUserNotPersisted_whenRegister_thenRegisteredUserReturn() {
        UserRegisterDto notPersistedUser = DataUtil.getUserNotRegistered();
        User persistedUser = DataUtil.getUserPersisted();
        BDDMockito.given(userRepository.save(any())).willReturn(persistedUser);
        BDDMockito.given(passwordEncoder.encode(anyString())).willReturn(notPersistedUser.getPassword());

        User obtainedUser = userService.register(notPersistedUser);

        Assertions.assertEquals(persistedUser.getEmail(), obtainedUser.getUsername());
        Assertions.assertNotNull(obtainedUser.getId());
    }

    @Test
    @DisplayName("Test login user")
    void givenUserPersisted_whenLogin_thenUserReturn() {
        Authentication authentication = mock(Authentication.class);
        User persistedUser = DataUtil.getUserPersisted();
        BDDMockito.given(authentication.getPrincipal()).willReturn(persistedUser);

        User obtainedUser = userService.login(authentication);

        Assertions.assertEquals(persistedUser.getId(), obtainedUser.getId());
    }

    @Test
    @DisplayName("Test delete user")
    void givenUserPersisted_whenDelete_thenDeletedUserReturn() {
        User persistedUser = DataUtil.getUserPersisted();
        User deletedUser = DataUtil.getUserDeleted();
        BDDMockito.given(userRepository.findByEmail(anyString())).willReturn(Optional.of(persistedUser));
        BDDMockito.given(userRepository.save(any(User.class))).willReturn(deletedUser);

        User obtainedUser = userService.delete(persistedUser.getEmail());

        Assertions.assertNotNull(obtainedUser.getArchiveDate());
        Assertions.assertEquals(persistedUser.getId(), deletedUser.getId());
    }

    @Test
    @DisplayName("Test update user")
    void givenUserPersisted_whenUpdate_thenUpdatedUserReturn() {
        User persistedUser = DataUtil.getUserPersisted();
        User updatedUser = DataUtil.getUserUpdated();
        UserUpdateDto notUpdatedUser = DataUtil.getUserNotUpdated();
        BDDMockito.given(userRepository.findByEmail(anyString())).willReturn(Optional.of(persistedUser));
        BDDMockito.given(userRepository.save(any(User.class))).willReturn(updatedUser);
        BDDMockito.given(passwordEncoder.encode(anyString())).willReturn(notUpdatedUser.getPassword());

        User obtainedUser = userService.update(persistedUser.getUsername(), notUpdatedUser);

        Assertions.assertEquals(updatedUser.getId(), obtainedUser.getId());
        Assertions.assertEquals(updatedUser.getUsername(), obtainedUser.getUsername());
    }
}