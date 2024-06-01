package org.tpr.auth.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.tpr.auth.controllers.dtos.UserRegisterDto;

public interface UserService extends UserDetailsService {

    String register(UserRegisterDto request);
}
