package com.example.calcBitcoin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "integration.freecurrency")
@Getter
@Configuration
@Setter
public class FreeCurrencyConfig {
    private String baseUrl;
    private String headerTokenName;
    private String token;
}
