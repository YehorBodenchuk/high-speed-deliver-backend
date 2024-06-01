package org.tpr.auth.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.tpr.auth.controllers.dtos.TokenDto;
import org.tpr.auth.controllers.dtos.UserRegisterDto;

public interface UserService extends UserDetailsService {

    TokenDto register(UserRegisterDto request);
}
