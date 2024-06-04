package org.tpr.auth.controller.dto.rabbitmq;

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
