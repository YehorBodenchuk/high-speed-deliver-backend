package org.tpr.auth.controller.converters.impl;

import org.springframework.stereotype.Component;
import org.tpr.auth.controller.dto.user.UserDto;
import org.tpr.auth.model.User;

@Component
public class UserDtoConverter {

    public UserDto convertToUserDto(User source) {
        return UserDto.builder()
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .roles(source.getRoles())
                .build();
    }
}
