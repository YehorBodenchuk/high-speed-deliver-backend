package org.tpr.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.tpr.auth.controller.dtos.*;
import org.tpr.auth.model.User;

public interface UserService extends UserDetailsService {

    User register(UserRegisterDto request);

    User login(Authentication authentication);

    User delete(String email);

    User update(String email, UserUpdateDto request);
}
