package org.example.bettingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
/**
 * Entity for Event.
 * This class is used to represent an event in the database.
 * It contains the necessary information to create an event.
 */
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String leagueName;
    private String homeTeam;
    private String awayTeam;
    private double homeWinRate;
    private double drawRate;
    private double awayWinRate;
    private LocalDateTime startTime;
}
