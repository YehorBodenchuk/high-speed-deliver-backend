package org.tpr.auth.service.facade;

import org.tpr.auth.controller.dto.parcel.ParcelDto;
import org.tpr.auth.controller.dto.user.UserLoginDto;
import org.tpr.auth.controller.dto.user.UserRegisterDto;
import org.tpr.auth.controller.dto.user.UserUpdateDto;
import org.tpr.auth.controller.dto.user.UserWithTokenDto;

import java.util.List;

public interface UserFacade {

    // Register, generate new tokens and notify user
    UserWithTokenDto register(UserRegisterDto request);

    // Authenticate user and generate tokens
    UserWithTokenDto authenticate(UserLoginDto request);

    // Update user and generate tokens
    UserWithTokenDto update(String email, UserUpdateDto request);

    // Delete user and generate tokens
    UserWithTokenDto delete(String email);

    // Get all user's parcels
    List<ParcelDto> getUserParcels(String email);
}
