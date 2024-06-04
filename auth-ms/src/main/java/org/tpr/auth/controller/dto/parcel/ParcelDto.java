package org.tpr.auth.controller.dto.parcel;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
