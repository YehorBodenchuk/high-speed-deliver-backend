package org.tpr.auth.controller.dto.parcel;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ParcelDto {

    private String id;

    private ParcelPerson sender;

    private ParcelPerson recipient;

    private String status;

    private String mark;

    private Double weight;

    private String postIndex;

    private Date createDate;

    private Date lastUpdateDate;

    private ParcelLocation destination;

    private List<ParcelHistory> history;
}
