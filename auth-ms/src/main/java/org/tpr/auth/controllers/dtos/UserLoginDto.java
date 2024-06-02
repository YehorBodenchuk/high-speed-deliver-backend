package org.tpr.auth.controllers.dtos;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserLoginDto {

    @Email(message = "Email or password is invalid! Try again!")
    private String email;

    private String password;
}
