package com.example.calcBitcoin.dto;

import lombok.Data;
import java.util.Map;

@Data
public class CoinDeskDtoRs {
    private Map<String, Map<String, String>> bpi;
}
