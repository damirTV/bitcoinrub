package com.example.calcBitcoin.controller;

import com.example.calcBitcoin.dto.BitcoinRubAvgDtoRs;
import com.example.calcBitcoin.dto.BitcoinRubDtoRs;
import com.example.calcBitcoin.service.BitcoinRubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rate")
@RequiredArgsConstructor
public class BitcoinRubController {
    private final BitcoinRubService bitcoinRubService;

    @GetMapping("/now")
    public BitcoinRubDtoRs convertBitcoinToRub() {
        return bitcoinRubService.convertBitcoinToRub();
    }

    @GetMapping("/history")
    public List<BitcoinRubDtoRs> getAll() {
        return bitcoinRubService.getAll();
    }

    @GetMapping("/average")
    public BitcoinRubAvgDtoRs getAverage() {
        return bitcoinRubService.getAvgRate();
    }
}
