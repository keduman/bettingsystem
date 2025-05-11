package org.example.bettingsystem.controller;

import org.example.bettingsystem.dto.EventDto;
import org.example.bettingsystem.model.Event;
import org.example.bettingsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @PostMapping
    public Event createEvent(@RequestBody EventDto event) {
        return eventService.createEvent(event);
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getAllEvents();
    }
}
