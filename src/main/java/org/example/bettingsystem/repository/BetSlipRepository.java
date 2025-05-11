package org.example.bettingsystem.repository;

import org.example.bettingsystem.model.BetSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetSlipRepository extends JpaRepository<BetSlip, String> {
    int countByEventId(String eventId);
}
