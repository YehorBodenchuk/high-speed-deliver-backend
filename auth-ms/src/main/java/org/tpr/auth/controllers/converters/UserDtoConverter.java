package org.tpr.auth.controllers.converters;

import org.springframework.stereotype.Component;
import org.tpr.auth.controllers.dtos.UserDto;
import org.tpr.auth.models.User;

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
