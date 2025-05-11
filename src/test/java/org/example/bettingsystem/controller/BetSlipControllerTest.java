package org.example.bettingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bettingsystem.dto.BetSlipDto;
import org.example.bettingsystem.model.BetSlip;
import org.example.bettingsystem.service.BetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BetSlipController.class)
class BetSlipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BetService betService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPlaceBet() throws Exception {
        BetSlipDto req = new BetSlipDto();
        req.eventId = "95bf5491-f270-43a1-9b3a-dffa2c8bd7c5";
        req.betOption = "HOME";
        req.multiplier = 2;
        req.amount = 100;

        BetSlip mockSlip = new BetSlip();
        mockSlip.setAmount(100);

        Mockito.when(betService.placeBet(Mockito.any(), Mockito.anyString())).thenReturn(mockSlip);

        mockMvc.perform(post("/api/bets")
                        .header("Customer-ID", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100));
    }

}