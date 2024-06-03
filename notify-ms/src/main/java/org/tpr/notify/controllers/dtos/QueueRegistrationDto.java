package org.tpr.notify.controllers.dtos;

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
