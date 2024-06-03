package org.tpr.auth.service.producer;

public interface ProducerStrategy {

    void sendMessage(Object message);
}
