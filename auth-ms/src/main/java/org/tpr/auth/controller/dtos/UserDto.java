package org.tpr.auth.controller.dtos;

import lombok.*;
import org.tpr.auth.model.enums.UserRole;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String firstName;

    private String lastName;

    private String email;

    private List<UserRole> roles;
}
