package org.tpr.auth.controller.dto.parcel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ParcelPerson {

    private String email;

    private String phone;
}
