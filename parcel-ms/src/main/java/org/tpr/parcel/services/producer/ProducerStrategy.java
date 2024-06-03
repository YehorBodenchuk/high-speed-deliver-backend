package org.tpr.parcel.services.producer;

@FunctionalInterface
public interface ProducerStrategy<T, R> {

    T sendMessage(R message);
}
