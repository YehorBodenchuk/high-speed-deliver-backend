package org.tpr.auth.controller.dto.user;

import jakarta.validation.constraints.Email;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDto {

    @Email(message = "Email or password is invalid! Try again!")
    private String email;

    private String password;
}
