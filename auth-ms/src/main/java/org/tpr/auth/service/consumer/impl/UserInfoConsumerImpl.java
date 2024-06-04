package org.tpr.auth.service.consumer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.tpr.auth.controller.converters.impl.UserConsumerDtoConverter;
import org.tpr.auth.controller.dto.user.UserConsumerDto;
import org.tpr.auth.model.User;
import org.tpr.auth.service.UserService;
import org.tpr.auth.service.consumer.ConsumerStrategy;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoConsumerImpl implements ConsumerStrategy<UserConsumerDto, String> {

    private final UserService userService;

    private final UserConsumerDtoConverter userConsumerDtoConverter;

    @RabbitListener(queues = {"${rabbitmq.userInfoQueue}"})
    @Override
    public UserConsumerDto receiveMessage(String message) {
        log.info(String.format("Receiving data for user with email: %s", message));
        User user = (User) userService.loadUserByUsername(message);
        return userConsumerDtoConverter.toDto(user);
    }
}
