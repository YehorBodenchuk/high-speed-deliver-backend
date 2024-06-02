package org.tpr.auth.services;

public interface ProducerStrategy {

    void sendMessage(String message);
}
