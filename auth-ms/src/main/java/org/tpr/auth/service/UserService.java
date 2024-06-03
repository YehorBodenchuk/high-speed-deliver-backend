package org.tpr.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.tpr.auth.controller.dtos.TokenDto;
import org.tpr.auth.controller.dtos.UserRegisterDto;

public interface UserService extends UserDetailsService {

    TokenDto register(UserRegisterDto request);

    TokenDto login(Authentication authentication);
}
