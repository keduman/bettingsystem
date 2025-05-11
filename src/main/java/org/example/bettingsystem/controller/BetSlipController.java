package org.example.bettingsystem.controller;

import org.example.bettingsystem.dto.BetSlipDto;
import org.example.bettingsystem.model.BetSlip;
import org.example.bettingsystem.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bets")
public class BetSlipController {

    private final BetService betService;

    @Autowired
    public BetSlipController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping
    public BetSlip createBet(@RequestBody BetSlipDto betSlip,
                             @RequestHeader("Customer-ID") String customerId) {
        return betService.placeBet(betSlip, customerId);
    }
}
