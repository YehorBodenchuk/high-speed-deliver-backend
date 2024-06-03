package org.tpr.auth.controller.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class QueueRegistrationDto {

    private String email;

    private String firstName;
}
