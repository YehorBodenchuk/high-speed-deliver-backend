package org.tpr.notify.sevices.impls;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.tpr.notify.controllers.dtos.QueueRegistrationDto;
import org.tpr.notify.sevices.RegistrationConsumer;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationConsumerImpl {

    private final JavaMailSender emailSender;

    private final TemplateEngine templateEngine;

    @RabbitListener(queues = {"${rabbitmq.registrationQueue}"})
    public void consumeMessage(QueueRegistrationDto request) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(request.getEmail());
        helper.setSubject("Welcome to High Speed Delivery powered by HellDivers");

        Map<String, Object> vars = new HashMap<>();
        vars.put("name", request.getFirstName());

        Context context = new Context();
        context.setVariables(vars);

        String htmlBody = templateEngine.process("registration-template", context);

        helper.setText(htmlBody, true);
        emailSender.send(message);
    }
}
