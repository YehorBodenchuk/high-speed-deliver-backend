package org.tpr.parcel.config.filter;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrincipals {

    private String firstName;

    private String lastName;

    private String email;

    private List<String> roles;
}
