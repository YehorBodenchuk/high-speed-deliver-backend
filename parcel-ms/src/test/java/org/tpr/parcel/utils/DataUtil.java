package org.tpr.parcel.utils;

import org.tpr.parcel.modals.Parcel;
import org.tpr.parcel.modals.dtos.CreateParcelDto;
import org.tpr.parcel.modals.enums.ParcelMark;
import org.tpr.parcel.modals.enums.ParcelStatus;
import org.tpr.parcel.modals.utils.Location;

import java.util.Date;

public final class DataUtil {

    public static CreateParcelDto getParcelUSATransient() {
        Location location = Location.builder()
                .country("USA")
                .city("Beverly Hills")
                .house("450n")
                .street("Crescent Dr")
                .build();

        return CreateParcelDto.builder()
                .postIndex("90210")
                .mark(ParcelMark.DEFAULT_PACKAGE)
                .sender("3a")
                .recipient("4b")
                .weight(5.2)
                .destination(location)
                .build();
    }

    public static Parcel getParcelUSAPersisted() {
        Location location = Location.builder()
                .country("USA")
                .city("Beverly Hills")
                .house("450n")
                .street("Crescent Dr")
                .build();

        return Parcel.builder()
                .id("1")
                .postIndex("90210")
                .mark(ParcelMark.DEFAULT_PACKAGE)
                .sender("3a")
                .recipient("4b")
                .weight(5.2)
                .createDate(new Date())
                .lastUpdateUser("5c")
                .status(ParcelStatus.CREATED)
                .destination(location)
                .build();
    }

    public static Parcel getParcelFrancePersisted() {
        Location location = Location.builder()
                .country("France")
                .city("Vierzon")
                .house("34")
                .street("Rue George Sand")
                .build();

        return Parcel.builder()
                .id("2")
                .postIndex("18100")
                .mark(ParcelMark.DEFAULT_PACKAGE)
                .sender("3a")
                .recipient("4b")
                .weight(4.1)
                .createDate(new Date())
                .lastUpdateUser("5c")
                .status(ParcelStatus.CREATED)
                .destination(location)
                .build();
    }

    public static Parcel getParcelJapanPersisted() {
        Location location = Location.builder()
                .country("Japan")
                .city("Aizuwakamatsu")
                .house("5-51")
                .street("Nisshinmachi")
                .build();

        return Parcel.builder()
                .id("3")
                .postIndex("965-0861")
                .mark(ParcelMark.FRAGILE_PACKAGE)
                .sender("3a")
                .recipient("4b")
                .weight(0.3)
                .createDate(new Date())
                .lastUpdateUser("5c")
                .status(ParcelStatus.CREATED)
                .destination(location)
                .build();
    }
}
