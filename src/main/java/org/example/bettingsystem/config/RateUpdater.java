package org.example.bettingsystem.config;

import org.example.bettingsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RateUpdater {

    private final EventService eventService;

    @Autowired
    public RateUpdater(EventService eventService) {
        this.eventService = eventService;
    }

    @Scheduled(fixedRateString = "${rate.update.interval}")
    public void updateRate() {
        eventService.randomizeRates();
    }
}
