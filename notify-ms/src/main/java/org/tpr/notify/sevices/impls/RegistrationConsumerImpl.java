package org.tpr.notify.sevices.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.tpr.notify.sevices.RegistrationConsumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationConsumerImpl implements RegistrationConsumer {

    @RabbitListener(queues = {"${rabbitmq.registrationQueue}"})
    @Override
    public void consumeMessage(String message) {
        log.info(message);
    }
}
