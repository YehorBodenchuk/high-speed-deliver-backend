package org.tpr.auth.services;

import org.tpr.auth.controllers.dtos.TokenDto;
import org.tpr.auth.controllers.dtos.UserLoginDto;
import org.tpr.auth.controllers.dtos.UserRegisterDto;

public interface UserFacade {

    TokenDto registerAndNotify(UserRegisterDto request);

    TokenDto authenticateAndLogin(UserLoginDto request);
}
