package org.tpr.parcel.controller.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserConsumerDto {

    private String id;

    private String email;

    private String phone;

    private List<String> roles;
}
