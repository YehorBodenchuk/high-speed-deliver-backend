package org.tpr.auth.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tpr.auth.controllers.dtos.TokenDto;
import org.tpr.auth.controllers.dtos.UserDto;
import org.tpr.auth.controllers.dtos.UserRegisterDto;
import org.tpr.auth.models.User;
import org.tpr.auth.models.enums.UserRole;
import org.tpr.auth.repositories.UserRepository;
import org.tpr.auth.services.UserService;
import org.tpr.auth.utils.factories.impls.JwtRefreshTokenFactory;
import org.tpr.auth.utils.factories.impls.JwtTokenFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenFactory jwtTokenFactory;

    private final JwtRefreshTokenFactory jwtRefreshTokenFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //todo: Create own exception to handle
        return userRepository.findByEmail(username).orElseThrow(RuntimeException::new);
    }

    @Override
    public TokenDto register(UserRegisterDto request) {
        User user = User.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .roles(List.of(UserRole.USER))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);

        log.info(String.format("New user with email %s was successfully registered.", user.getEmail()));

        String token = jwtTokenFactory.generateToken(user);
        String refreshToken = jwtRefreshTokenFactory.generateToken(user);

        return TokenDto.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .accessTokenExpire(jwtTokenFactory.extractExpiration(token).getTime())
                .refreshTokenExpire(jwtRefreshTokenFactory.extractExpiration(refreshToken).getTime())
                .build();
    }

    @Override
    public UserDto getMe() {
        return null;
    }
}
