package org.example.bettingsystem.service;

import org.example.bettingsystem.dto.BetSlipDto;
import org.example.bettingsystem.model.Event;
import org.example.bettingsystem.repository.BetSlipRepository;
import org.example.bettingsystem.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import org.example.bettingsystem.model.BetSlip;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BetServiceTest {
    @Mock
    private BetSlipRepository betSlipRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private BetService betService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        betService = new BetService(betSlipRepository, eventRepository);

        java.lang.reflect.Field field = betService.getClass().getDeclaredField("timeoutMillis");
        field.setAccessible(true);
        field.set(betService, 2000);
    }

    @Test
    void placeBet_successful() {
        // Arrange
        BetSlipDto dto = new BetSlipDto();
        dto.setEventId("ec906411-f5aa-4137-bf6b-65f982a5abf5");
        dto.setBetOption("HOME");
        dto.setAmount(200.0);
        dto.setMultiplier(2);

        Event event = new Event();
        event.setId("ec906411-f5aa-4137-bf6b-65f982a5abf5");
        event.setHomeWinRate(1.9);
        event.setDrawRate(3.1);
        event.setAwayWinRate(2.5);

        when(eventRepository.findById("ec906411-f5aa-4137-bf6b-65f982a5abf5")).thenReturn(Optional.of(event));
        when(betSlipRepository.countByEventId("ec906411-f5aa-4137-bf6b-65f982a5abf5")).thenReturn(0);
        when(betSlipRepository.save(any(BetSlip.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BetSlip result = betService.placeBet(dto, "customer123");

        // Assert
        assertNotNull(result);
        assertEquals(1.9, result.getRateAtPlayTime());
        assertEquals("HOME", result.getBetOption());
        assertEquals("customer123", result.getCustomerId());
    }

    @Test
    void placeBet_eventNotFound() {
        BetSlipDto dto = new BetSlipDto();
        dto.setEventId("ec906411-f5aa-4137-bf6b-65f982a5abf5");
        dto.setBetOption("AWAY");
        dto.setAmount(100.0);
        dto.setMultiplier(1);

        when(eventRepository.findById("ec906411-f5aa-4137-bf6b-65f982a5abf5")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> betService.placeBet(dto, "c"));

        assertTrue(ex.getMessage().contains("Event not found"));
    }

    @Test
    void placeBet_tooManyBets() {
        BetSlipDto dto = new BetSlipDto();
        dto.setEventId("ec906411-f5aa-4137-bf6b-65f982a5abf5");
        dto.setBetOption("DRAW");
        dto.setAmount(100.0);
        dto.setMultiplier(5);

        Event event = new Event();
        event.setId("ec906411-f5aa-4137-bf6b-65f982a5abf5");
        event.setHomeWinRate(1.5);
        event.setDrawRate(2.0);
        event.setAwayWinRate(2.5);

        when(eventRepository.findById("ec906411-f5aa-4137-bf6b-65f982a5abf5")).thenReturn(Optional.of(event));
        when(betSlipRepository.countByEventId("ec906411-f5aa-4137-bf6b-65f982a5abf5")).thenReturn(5);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> betService.placeBet(dto, "c"));

        assertTrue(ex.getMessage().contains("Too many bets"));
    }

    @Test
    void placeBet_invalidBetOption() {
        BetSlipDto dto = new BetSlipDto();
        dto.setEventId("ec906411-f5aa-4137-bf6b-65f982a5abf5");
        dto.setBetOption("INVALID");
        dto.setAmount(100.0);
        dto.setMultiplier(1);

        Event event = new Event();
        event.setId("ec906411-f5aa-4137-bf6b-65f982a5abf5");
        event.setHomeWinRate(1.5);
        event.setDrawRate(2.0);
        event.setAwayWinRate(2.5);

        when(eventRepository.findById("ec906411-f5aa-4137-bf6b-65f982a5abf5")).thenReturn(Optional.of(event));
        when(betSlipRepository.countByEventId("ec906411-f5aa-4137-bf6b-65f982a5abf5")).thenReturn(0);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> betService.placeBet(dto, "c"));

        assertTrue(ex.getMessage().contains("Invalid bet option"));
    }

    @Test
    void placeBet_investmentLimitExceeded() {
        BetSlipDto dto = new BetSlipDto();
        dto.setEventId("ec906411-f5aa-4137-bf6b-65f982a5abf5");
        dto.setBetOption("AWAY");
        dto.setAmount(6000.0);
        dto.setMultiplier(2);

        Event event = new Event();
        event.setId("ec906411-f5aa-4137-bf6b-65f982a5abf5");
        event.setHomeWinRate(1.5);
        event.setDrawRate(2.0);
        event.setAwayWinRate(2.5);

        when(eventRepository.findById("ec906411-f5aa-4137-bf6b-65f982a5abf5")).thenReturn(Optional.of(event));
        when(betSlipRepository.countByEventId("ec906411-f5aa-4137-bf6b-65f982a5abf5")).thenReturn(0);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> betService.placeBet(dto, "c"));

        assertTrue(ex.getMessage().contains("Investment exceeds"));
    }
}