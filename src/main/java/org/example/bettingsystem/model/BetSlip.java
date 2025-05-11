package org.example.bettingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
/**
 * Entity for BetSlip.
 * This class is used to represent a bet slip in the database.
 * It contains the necessary information to create a bet slip.
 */
public class BetSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String eventId;
    private String betOption;
    private String customerId;
    private int multiplier;
    private double amount;
    private double rateAtPlayTime;
}
