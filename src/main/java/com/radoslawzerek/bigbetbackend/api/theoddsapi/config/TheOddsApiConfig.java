package com.radoslawzerek.bigbetbackend.api.theoddsapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TheOddsApiConfig {
    @Value("${theoddsapi.api.endpoint.prod}")
    private String theOddApiEndpoint;

    @Value("${theoddsapi.app.apiKey}")
    private String theOddApiKey;

    private final String ELPKey = "soccer_epl";
    private final String LaLigaKey = "soccer_spain_la_liga";
    private final String SerieAKey = "soccer_italy_serie_a";
}
