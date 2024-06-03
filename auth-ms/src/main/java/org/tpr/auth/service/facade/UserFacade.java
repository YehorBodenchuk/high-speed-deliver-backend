package org.tpr.auth.service.facade;

import org.tpr.auth.controller.dtos.TokenDto;
import org.tpr.auth.controller.dtos.UserLoginDto;
import org.tpr.auth.controller.dtos.UserRegisterDto;

public interface UserFacade {

    TokenDto registerAndNotify(UserRegisterDto request);

    TokenDto authenticateAndLogin(UserLoginDto request);
}
