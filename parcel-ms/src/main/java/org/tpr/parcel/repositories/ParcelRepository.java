package org.tpr.parcel.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tpr.parcel.modals.Parcel;

import java.util.List;

public interface ParcelRepository extends MongoRepository<Parcel, String> {

    List<Parcel> findAllByArchiveDateIsEmpty();
}
