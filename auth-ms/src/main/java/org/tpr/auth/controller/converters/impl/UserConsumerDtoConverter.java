package org.tpr.auth.controller.converters.impl;

import org.springframework.stereotype.Component;
import org.tpr.auth.controller.dto.user.UserConsumerDto;
import org.tpr.auth.model.User;

@Component
public class UserConsumerDtoConverter {

    public UserConsumerDto toDto(User user) {
        return UserConsumerDto.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(Enum::toString).toList())
                .build();
    }
}
