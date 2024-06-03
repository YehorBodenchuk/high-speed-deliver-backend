package org.tpr.auth.service.facade;

import org.tpr.auth.controller.dtos.*;

public interface UserFacade {

    // Register, generate new tokens and notify user
    UserWithTokenDto register(UserRegisterDto request);

    // Authenticate user and generate tokens
    UserWithTokenDto authenticate(UserLoginDto request);

    // Update user and generate tokens
    UserWithTokenDto update(String email, UserUpdateDto request);

    // Delete user and generate tokens
    UserWithTokenDto delete(String email);
}
