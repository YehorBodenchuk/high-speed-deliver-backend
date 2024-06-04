package org.tpr.parcel.modal;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tpr.parcel.modal.enums.ParcelMark;
import org.tpr.parcel.modal.enums.ParcelStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parcel {

    private String id;

    private PersonInfo sender;

    private PersonInfo recipient;

    private ParcelStatus status;

    private ParcelMark mark;

    private Double weight;

    private String postIndex;

    private Date createDate;

    private Date lastUpdateDate;

    private String lastUpdateUser;

    private Date archiveDate;

    private Location destination;

    private List<ParcelHistory> history = new ArrayList<>();
}
