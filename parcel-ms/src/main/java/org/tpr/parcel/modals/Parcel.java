package org.tpr.parcel.modals;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tpr.parcel.modals.enums.ParcelMark;
import org.tpr.parcel.modals.enums.ParcelStatus;
import org.tpr.parcel.modals.utils.Location;

import java.util.Date;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parcel {

    private String id;

    private String sender;

    private String recipient;

    private ParcelStatus status;

    private ParcelMark mark;

    private Double weight;

    private String postIndex;

    private Date createDate;

    private Date lastUpdateDate;

    private String lastUpdateUser;

    private Date archiveDate;

    private Location destination;
}
