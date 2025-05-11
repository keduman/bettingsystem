package org.example.bettingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO for BetSlip.
 * This class is used to transfer data between the client and server.
 * It contains the necessary information to create a bet slip.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetSlipDto {
    public String eventId;
    public String betOption;
    public int multiplier;
    public double amount;
}
