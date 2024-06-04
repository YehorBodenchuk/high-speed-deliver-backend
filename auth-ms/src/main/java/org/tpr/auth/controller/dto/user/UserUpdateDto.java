package org.tpr.auth.controller.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {

    @Size(min = 3, message = "User's first name must be at least 3 characters!")
    private String firstName;

    @Size(min = 3, message = "User's last name must be at least 3 characters!")
    private String lastName;

    @Email(message = "Request email is not valid!")
    private String email;

    @Pattern(regexp = "^\\+380\\d{9}$", message = "Phone number must be 13 characters long and start with +380")
    private String phone;

    private String password;
}
