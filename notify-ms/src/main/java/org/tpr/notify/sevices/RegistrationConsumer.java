package org.tpr.notify.sevices;

import jakarta.mail.MessagingException;

public interface RegistrationConsumer {

    void consumeMessage(Object request) throws MessagingException;
}
