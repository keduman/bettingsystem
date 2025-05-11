package org.example.bettingsystem.service;


import jakarta.transaction.Transactional;
import org.example.bettingsystem.dto.EventDto;
import org.example.bettingsystem.model.Event;
import org.example.bettingsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository ;
    private final Random random = new Random();

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(EventDto eventDto) {
        Event event = new Event();

        event.setLeagueName(eventDto.getLeagueName());
        event.setHomeTeam(eventDto.getHomeTeam());
        event.setAwayTeam(eventDto.getAwayTeam());
        event.setHomeWinRate(eventDto.getHomeWinRate());
        event.setDrawRate(eventDto.getDrawRate());
        event.setAwayWinRate(eventDto.getAwayWinRate());
        event.setStartTime(eventDto.getStartTime());

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void randomizeRates() {
        List<Event> events = getAllEvents();

        events.forEach(e -> {
            e.setHomeWinRate(adjustRate(e.getHomeWinRate()));
            e.setDrawRate(adjustRate(e.getDrawRate()));
            e.setAwayWinRate(adjustRate(e.getAwayWinRate()));
        });

        eventRepository.saveAll(events);
    }

    private double adjustRate(double rate) {
        double change = (random.nextDouble() - 0.5) * 0.2;
        return Math.round((rate + rate * change) * 100.0) / 100.0;
    }


}
