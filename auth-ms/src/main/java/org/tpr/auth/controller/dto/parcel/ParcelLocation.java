package org.tpr.auth.controller.dto.parcel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ParcelLocation {

    private String city;

    private String house;

    private String street;

    private String country;
}
