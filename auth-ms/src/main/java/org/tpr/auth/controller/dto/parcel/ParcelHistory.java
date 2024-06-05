package org.tpr.auth.controller.dto.parcel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ParcelHistory {

    private Double longitude;

    private Double latitude;

    private String status;
}
