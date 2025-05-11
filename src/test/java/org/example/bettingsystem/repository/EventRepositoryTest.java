package org.example.bettingsystem.repository;

import org.example.bettingsystem.model.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void testSaveAndFindAll() {
        Event event = new Event();
        event.setLeagueName("Test League");
        event.setHomeTeam("A");
        event.setAwayTeam("B");
        event.setHomeWinRate(1.5);
        event.setDrawRate(2.0);
        event.setAwayWinRate(2.5);
        event.setStartTime(LocalDateTime.now());

        eventRepository.save(event);

        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(1);
        assertThat(events.getFirst().getLeagueName()).isEqualTo("Test League");
    }
}