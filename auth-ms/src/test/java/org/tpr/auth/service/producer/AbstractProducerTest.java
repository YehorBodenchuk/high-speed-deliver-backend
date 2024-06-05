package org.tpr.auth.service.producer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith({MockitoExtension.class})

public abstract class AbstractProducerTest {

    @Mock
    protected RabbitTemplate rabbitTemplate;

    protected final String EXCHANGE_NAME = "exchange";

    protected final String KEY_NAME = "key";

    protected abstract Object getTargetObject();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(getTargetObject(), "exchange", EXCHANGE_NAME);
        ReflectionTestUtils.setField(getTargetObject(), "key", KEY_NAME);
    }
}
