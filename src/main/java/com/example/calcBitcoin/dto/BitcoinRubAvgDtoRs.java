package com.example.calcBitcoin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BitcoinRubAvgDtoRs {
    private BigDecimal avgRate;
}
