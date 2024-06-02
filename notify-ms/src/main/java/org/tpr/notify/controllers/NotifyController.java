package org.tpr.notify.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/notify")
public class NotifyController {

    @GetMapping("/message")
    public String sendMessage() {
        log.info("Notified");

        return "Hello, World!";
    }
}
