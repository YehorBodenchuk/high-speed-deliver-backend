package org.tpr.auth.controller.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWithTokenDto {

    private UserDto user;

    private TokenDto tokens;
}
