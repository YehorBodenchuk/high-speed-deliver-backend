package org.tpr.parcel.services.consumer;

@FunctionalInterface
public interface ConsumerStrategy<T, R> {

    T receiveMessage(R message);
}
