package org.tpr.auth.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tpr.auth.repositories.UserRepository;
import org.tpr.auth.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //todo: Create own exception to handle
        return userRepository.findByEmail(username).orElseThrow(RuntimeException::new);
    }
}
