package org.tpr.parcel.modal;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {

    private String city;

    private String house;

    private String street;

    private String country;
}
