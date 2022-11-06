package br.eti.freitas.basicproject.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealtCheck implements HealthIndicator {

	private final String message_key = "Service";

	@GetMapping("/api/healt")
    public Health health() {
        if (!isRunningServiceA()) {
            return Health.down().withDetail(message_key, "Not Available").build();
        }
        return Health.up().withDetail(message_key, "Available").build();
    }

    private Boolean isRunningServiceA() {
        Boolean isRunning = true;
        return isRunning;
    }

}
