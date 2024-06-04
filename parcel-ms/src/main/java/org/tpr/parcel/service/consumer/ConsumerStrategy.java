package org.tpr.parcel.service.consumer;

@FunctionalInterface
public interface ConsumerStrategy<T, R> {

    T receiveMessage(R message);
}
