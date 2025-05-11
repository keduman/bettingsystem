package org.example.bettingsystem.repository;

import org.example.bettingsystem.model.BetSlip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BetSlipRepositoryTest {

    @Autowired
    private BetSlipRepository betSlipRepository;

    @Test
    void testSaveAndFindAll() {
        BetSlip betSlip = new BetSlip();

        betSlip.setEventId("e95e76b0-ad9c-4126-b27f-6d9310e21539");
        betSlip.setBetOption("HOME");
        betSlip.setMultiplier(2);
        betSlip.setAmount(100.0);

        betSlipRepository.save(betSlip);

        List<BetSlip> events = betSlipRepository.findAll();
        assertThat(events).hasSize(1);
        assertThat(events.getFirst().getBetOption()).isEqualTo("HOME");
    }

}