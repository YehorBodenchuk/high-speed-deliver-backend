package org.tpr.parcel.service.producer;

@FunctionalInterface
public interface ProducerStrategy<T> {

    void sendMessage(T message);
}
