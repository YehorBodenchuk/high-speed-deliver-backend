package org.tpr.auth.services.impls;

import org.springframework.stereotype.Service;
import org.tpr.auth.services.ProducerStrategy;

@Service
public class ParcelProducer implements ProducerStrategy {

    @Override
    public void sendMessage(String message) {

    }
}
