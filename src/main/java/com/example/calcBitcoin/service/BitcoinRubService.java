package com.example.calcBitcoin.service;
import com.example.calcBitcoin.config.CoinDeskConfig;
import com.example.calcBitcoin.config.FreeCurrencyConfig;
import com.example.calcBitcoin.dto.BitcoinRubAvgDtoRs;
import com.example.calcBitcoin.dto.BitcoinRubDtoRs;
import com.example.calcBitcoin.dto.CoinDeskDtoRs;
import com.example.calcBitcoin.dto.FreeCurrencyDtoRs;
import com.example.calcBitcoin.exception.IntegrationException;
import com.example.calcBitcoin.repository.BitcoinRubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BitcoinRubService {
    private static final String CURRENCY_NAME = "RUB";
    private static final String POSTFIX_URL_GET_RUB = "?currencies=" + CURRENCY_NAME;
    private final RestTemplate restTemplate;
    private final FreeCurrencyConfig freeCurrencyConfig;
    private final CoinDeskConfig coinDeskConfig;
    private final BitcoinRubRepository bitcoinRubRepository;

    public BitcoinRubDtoRs convertBitcoinToRub() {
        BitcoinRubDtoRs result = convertUsdToRub(convertBitcoinToUsd());
        return bitcoinRubRepository.add(result);
    }

    public List<BitcoinRubDtoRs> getAll() {
        return bitcoinRubRepository.getAll();
    }

    public BitcoinRubAvgDtoRs getAvgRate() {
        List<BitcoinRubDtoRs> bitcoinRubDtoRsList = bitcoinRubRepository.getAll();
        if (bitcoinRubDtoRsList.isEmpty()) {
            throw new RuntimeException("No rates now!");
        }
        BigDecimal sum = bitcoinRubDtoRsList
                .stream()
                .map(BitcoinRubDtoRs::getRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal average = sum.divide(BigDecimal.valueOf(bitcoinRubDtoRsList.size()), RoundingMode.HALF_UP);
        return new BitcoinRubAvgDtoRs(average);
    }

    private BigDecimal convertBitcoinToUsd () {
        RequestEntity<Void> request = RequestEntity
                .get(coinDeskConfig.getUrl()).build();
        ResponseEntity<CoinDeskDtoRs> response;
        try {
            response = restTemplate.exchange(request, CoinDeskDtoRs.class);
        } catch (Throwable throwable) {
            throw new IntegrationException("Не доступен CoinDesk");
        }
        CoinDeskDtoRs coinDeskDtoRs = response.getBody();
        assert coinDeskDtoRs != null : "CoinDesk response is null";
        return new BigDecimal(coinDeskDtoRs.getBpi().get("USD").get("rate_float"));
    }

    private BitcoinRubDtoRs convertUsdToRub(BigDecimal amountUsd) {
        RequestEntity<Void> request = RequestEntity
                .get(freeCurrencyConfig.getBaseUrl() + POSTFIX_URL_GET_RUB)
                .header(freeCurrencyConfig.getHeaderTokenName(), freeCurrencyConfig.getToken())
                .build();
        ResponseEntity<FreeCurrencyDtoRs> response;
        try {
            response = restTemplate.exchange(request, FreeCurrencyDtoRs.class);
        } catch (Throwable throwable) {
            throw new IntegrationException("Не доступен FreeCurrency");
        }
        FreeCurrencyDtoRs freeCurrencyDtoRs = response.getBody();
        assert freeCurrencyDtoRs != null : "FreeCurrency response is null";
        BigDecimal rate = freeCurrencyDtoRs.getData().get(CURRENCY_NAME);
        BigDecimal amountRub = amountUsd.multiply(rate);
        return new BitcoinRubDtoRs(CURRENCY_NAME, amountRub, LocalDate.now(), LocalTime.now());
    }
}