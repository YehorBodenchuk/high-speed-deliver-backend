package org.tpr.auth.controller.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String firstName;

    private String lastName;

    private String email;
}
