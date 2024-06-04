package org.tpr.parcel.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.tpr.parcel.modals.Parcel;

import java.util.List;

public interface ParcelRepository extends MongoRepository<Parcel, String> {

    List<Parcel> findAllByArchiveDateIsEmpty();

    @Query("{ $or: [ { 'recipient.email': ?0 }, { 'recipient.phone': ?1 } ] }")
    List<Parcel> findByRecipientEmailOrPhone(String email, String phone);
}
