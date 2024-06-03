package org.tpr.notify.controllers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/notify")
public class NotifyController {

    private final JavaMailSender emailSender;

    private final TemplateEngine templateEngine;

    @GetMapping("/send/{email}")
    public String sendMessage(@PathVariable String email) throws MessagingException {
        log.info("Notified");
        log.info(email);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Welcome to High Speed Delivery powered by HellDivers");

        Map<String, Object> vars = new HashMap<>();
        vars.put("name", "John");
        vars.put("registrationId", "123-abc-def");

        Context context = new Context();
        context.setVariables(vars);

        String htmlBody = templateEngine.process("registration-template", context);

        helper.setText(htmlBody, true);
        emailSender.send(message);
        return "Hello, World!";
    }
}
