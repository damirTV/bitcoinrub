package com.example.calcBitcoin.repository;

import com.example.calcBitcoin.dto.BitcoinRubDtoRs;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class BitcoinRubRepository {
    private final List<BitcoinRubDtoRs> repository = new ArrayList<>();

    public BitcoinRubDtoRs add(BitcoinRubDtoRs bitcoinRubDtoRs) {
        repository.add(bitcoinRubDtoRs);
        return bitcoinRubDtoRs;
    }

    public List<BitcoinRubDtoRs> getAll() {
        return repository;
    }
}
