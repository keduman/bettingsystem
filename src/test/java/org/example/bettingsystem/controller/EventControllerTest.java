package org.example.bettingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bettingsystem.dto.EventDto;
import org.example.bettingsystem.model.Event;
import org.example.bettingsystem.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateEvent() throws Exception {
        EventDto req = new EventDto();
        req.leagueName = "Premier League";
        req.homeTeam = "Liverpool";
        req.awayTeam = "Arsenal";
        req.homeWinRate = 1.75;
        req.drawRate = 3.10;
        req.awayWinRate = 2.40;
        req.startTime = LocalDateTime.now().plusHours(2);

        Event mockEvent = new Event();
        mockEvent.setId("d6e8314e-53dc-4817-ab40-31a2f0cabe24");
        mockEvent.setLeagueName(req.leagueName);
        mockEvent.setHomeTeam(req.homeTeam);
        mockEvent.setAwayTeam(req.awayTeam);
        mockEvent.setHomeWinRate(req.homeWinRate);
        mockEvent.setDrawRate(req.drawRate);
        mockEvent.setAwayWinRate(req.awayWinRate);
        mockEvent.setStartTime(req.startTime);

        Mockito.when(eventService.createEvent(Mockito.any())).thenReturn(mockEvent);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.homeTeam").value("Liverpool"));
    }

    @Test
    void testListEvents() throws Exception {
        Event e1 = new Event();
        e1.setId("d6e8314e-53dc-4817-ab40-31a2f0cabe24");
        e1.setHomeTeam("Team A");
        e1.setAwayTeam("Team B");

        Event e2 = new Event();
        e2.setId("de20739c-5747-4b78-aa50-e118c5c31836");
        e2.setHomeTeam("Team C");
        e2.setAwayTeam("Team D");

        Mockito.when(eventService.getAllEvents()).thenReturn(List.of(e1, e2));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

}