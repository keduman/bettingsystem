package org.example.bettingsystem.service;

import org.example.bettingsystem.dto.BetSlipDto;
import org.example.bettingsystem.model.BetSlip;
import org.example.bettingsystem.model.Event;
import org.example.bettingsystem.repository.BetSlipRepository;
import org.example.bettingsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.*;

@Service
@Transactional
public class BetService {
    private final BetSlipRepository betSlipRepository;
    private final EventRepository eventRepository;

    private static final int MAX_MULTIPLE_BETS = 5;
    private static final double MAX_INVESTMENT = 10000.0;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Value("${bet.timeoutMillis}")
    private int timeoutMillis;


    @Autowired
    public BetService(BetSlipRepository betSlipRepository, EventRepository eventRepository) {
        this.betSlipRepository = betSlipRepository;
        this.eventRepository = eventRepository;
    }

    public BetSlip placeBet(BetSlipDto betSlipDto, String customerId) {
        try {
            Future<BetSlip> future = executorService.submit(() -> placeBetInternal(betSlipDto, customerId));
            return betSlipRepository.save(future.get(timeoutMillis, TimeUnit.MILLISECONDS));
        } catch (TimeoutException e) {
            throw new RuntimeException("Bet placement timed out after " + timeoutMillis + " ms");
        } catch (Exception e) {
            throw new RuntimeException("Error placing bet: " + e.getMessage(), e);
        }
    }

    private BetSlip placeBetInternal(BetSlipDto betSlipDto, String customerId) {
        Optional<Event> optionalEvent = eventRepository.findById(betSlipDto.getEventId());
        if (optionalEvent.isEmpty()) throw new IllegalArgumentException("Event not found");

        Event event = optionalEvent.get();
        int existingBets = betSlipRepository.countByEventId(betSlipDto.getEventId());
        if (existingBets + betSlipDto.getMultiplier() > MAX_MULTIPLE_BETS) {
            throw new IllegalArgumentException("Too many bets on this event");
        }

        double totalInvestment = betSlipDto.getAmount() * betSlipDto.getMultiplier();
        if (totalInvestment > MAX_INVESTMENT) {
            throw new IllegalArgumentException("Investment exceeds maximum limit");
        }



        return createBetSlip(betSlipDto, customerId, event);
    }

    private static BetSlip createBetSlip(BetSlipDto betSlipDto, String customerId, Event event) {
        double currentRate = switch (betSlipDto.getBetOption().toUpperCase()) {
            case "HOME" -> event.getHomeWinRate();
            case "DRAW" -> event.getDrawRate();
            case "AWAY" -> event.getAwayWinRate();
            default -> throw new IllegalArgumentException("Invalid bet option");
        };

        BetSlip betSlip = new BetSlip();
        betSlip.setEventId(betSlipDto.getEventId());
        betSlip.setBetOption(betSlipDto.getBetOption());
        betSlip.setCustomerId(customerId);
        betSlip.setMultiplier(betSlipDto.getMultiplier());
        betSlip.setAmount(betSlipDto.getAmount());
        betSlip.setRateAtPlayTime(currentRate);

        return betSlip;
    }
}
