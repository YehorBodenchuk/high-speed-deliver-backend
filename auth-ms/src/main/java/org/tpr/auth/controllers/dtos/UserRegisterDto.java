package org.tpr.auth.controllers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterDto {

    @NotBlank(message = "User's first name is mandatory!")
    @Size(min = 3, message = "User's first name must be at least 3 characters!")
    private String firstName;

    @NotBlank(message = "User's last name is mandatory!")
    @Size(min = 3, message = "User's last name must be at least 3 characters!")
    private String lastName;

    @Size(min = 3, message = "User's middle name must be at least 3 characters!")
    private String middleName;

    @Pattern(regexp = "^\\+380\\d{9}$", message = "Phone number must be 13 characters long and start with +380")
    private String phone;

    @NotBlank(message = "User's email is mandatory!")
    @Email(message = "Request email is not valid!")
    private String email;

    @NotBlank(message = "User's password is mandatory!")
    private String password;

    @NotBlank(message = "Confirm password is mandatory!")
    private String confirmPassword;
}
