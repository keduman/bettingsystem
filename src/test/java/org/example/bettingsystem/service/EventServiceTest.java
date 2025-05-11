package org.example.bettingsystem.service;

import org.example.bettingsystem.dto.EventDto;
import org.example.bettingsystem.model.Event;
import org.example.bettingsystem.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(eventRepository);
    }

    @Test
    void testCreateEvent() {
        EventDto dto = new EventDto();
        dto.setLeagueName("Premier League");
        dto.setHomeTeam("Manchester City");
        dto.setAwayTeam("Liverpool");
        dto.setHomeWinRate(1.75);
        dto.setDrawRate(3.10);
        dto.setAwayWinRate(2.25);
        dto.setStartTime(LocalDateTime.now().plusDays(1));

        Event expectedEvent = new Event();
        expectedEvent.setLeagueName(dto.getLeagueName());
        expectedEvent.setHomeTeam(dto.getHomeTeam());
        expectedEvent.setAwayTeam(dto.getAwayTeam());
        expectedEvent.setHomeWinRate(dto.getHomeWinRate());
        expectedEvent.setDrawRate(dto.getDrawRate());
        expectedEvent.setAwayWinRate(dto.getAwayWinRate());
        expectedEvent.setStartTime(dto.getStartTime());

        when(eventRepository.save(any(Event.class))).thenReturn(expectedEvent);

        Event result = eventService.createEvent(dto);

        assertNotNull(result);
        assertEquals("Manchester City", result.getHomeTeam());
        assertEquals(1.75, result.getHomeWinRate());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testGetAllEvents() {
        Event e1 = new Event();
        e1.setHomeTeam("A");
        Event e2 = new Event();
        e2.setHomeTeam("B");

        when(eventRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Event> result = eventService.getAllEvents();

        assertEquals(2, result.size());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testRandomizeRates() {
        Event e1 = new Event();
        e1.setHomeWinRate(1.50);
        e1.setDrawRate(3.00);
        e1.setAwayWinRate(2.50);

        Event e2 = new Event();
        e2.setHomeWinRate(1.80);
        e2.setDrawRate(2.90);
        e2.setAwayWinRate(2.20);

        List<Event> events = Arrays.asList(e1, e2);

        when(eventRepository.findAll()).thenReturn(events);
        when(eventRepository.saveAll(anyList())).thenReturn(events);

        eventService.randomizeRates();

        verify(eventRepository, times(1)).findAll();
        verify(eventRepository, times(1)).saveAll(events);

        assertNotEquals(1.50, e1.getHomeWinRate(), 0.001);
        assertNotEquals(2.50, e1.getAwayWinRate(), 0.001);
    }

}