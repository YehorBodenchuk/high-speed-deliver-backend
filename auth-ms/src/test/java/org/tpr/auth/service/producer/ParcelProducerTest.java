package org.tpr.auth.service.producer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.tpr.auth.controller.dto.parcel.ParcelDto;
import org.tpr.auth.controller.dto.rabbitmq.QueueParcelDto;
import org.tpr.auth.service.producer.impl.ParcelProducer;
import org.tpr.auth.util.RabbitUtil;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class ParcelProducerTest extends AbstractProducerTest {

    @InjectMocks
    private ParcelProducer parcelProducer;


    @Override
    protected Object getTargetObject() {
        return parcelProducer;
    }

    @Test
    @DisplayName("Test send message in parcel queue and receive response")
    void givenMessage_whenSendMessage_thenResponseReturn() {
        QueueParcelDto message = RabbitUtil.getQueueParcelMessage();
        List<ParcelDto> response = RabbitUtil.getQueueParcelResponse();
        BDDMockito.given(rabbitTemplate.convertSendAndReceiveAsType(anyString(), anyString(), any(QueueParcelDto.class), any())).willReturn(response);

        List<ParcelDto> obtainedParcels = parcelProducer.sendMessage(message);

        Assertions.assertEquals(response.size(), obtainedParcels.size());
        Mockito.verify(rabbitTemplate).convertSendAndReceiveAsType(EXCHANGE_NAME, KEY_NAME, message, new ParameterizedTypeReference<List<ParcelDto>>(){});
    }
}
