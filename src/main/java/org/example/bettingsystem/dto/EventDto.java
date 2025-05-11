package org.example.bettingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * DTO for Event.
 * This class is used to transfer data between the client and server.
 * It contains the necessary information to create an event.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    public String leagueName;
    public String homeTeam;
    public String awayTeam;
    public double homeWinRate;
    public double drawRate;
    public double awayWinRate;
    public LocalDateTime startTime;
}
