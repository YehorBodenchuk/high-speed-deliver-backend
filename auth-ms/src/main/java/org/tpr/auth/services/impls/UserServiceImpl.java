package org.tpr.auth.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tpr.auth.controllers.dtos.UserRegisterDto;
import org.tpr.auth.models.User;
import org.tpr.auth.models.enums.UserRole;
import org.tpr.auth.repositories.UserRepository;
import org.tpr.auth.services.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //todo: Create own exception to handle
        return userRepository.findByEmail(username).orElseThrow(RuntimeException::new);
    }

    @Override
    public String register(UserRegisterDto request) {
        User user = User.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .roles(List.of(UserRole.USER))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return "It's ok!";
    }
}
