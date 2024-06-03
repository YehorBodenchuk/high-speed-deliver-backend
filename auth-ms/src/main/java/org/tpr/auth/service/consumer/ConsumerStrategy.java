package org.tpr.auth.service.consumer;

@FunctionalInterface
public interface ConsumerStrategy<T, R> {

    T receiveMessage(R message);
}
