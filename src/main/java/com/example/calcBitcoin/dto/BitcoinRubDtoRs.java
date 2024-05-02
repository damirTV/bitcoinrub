package com.example.calcBitcoin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class BitcoinRubDtoRs {
    private String currencyName;
    private BigDecimal rate;
    private LocalDate date;
    private LocalTime time;
}
