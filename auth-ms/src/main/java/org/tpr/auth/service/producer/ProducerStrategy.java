package org.tpr.auth.service.producer;

@FunctionalInterface
public interface ProducerStrategy<T, R> {

    T sendMessage(R message);
}
