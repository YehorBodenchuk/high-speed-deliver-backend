package org.tpr.auth.controller.converters;

import org.springframework.stereotype.Component;
import org.tpr.auth.controller.dtos.UserDto;
import org.tpr.auth.model.User;

@Component
public class UserDtoConverter {

    public UserDto convertToUserDto(User source) {
        return UserDto.builder()
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
