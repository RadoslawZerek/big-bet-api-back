package com.radoslawzerek.bigbetbackend.api.footballdata.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FootballDataApiConfig {

    @Value("${footballdata.api.endpoint.prod}")
    private String footballDataApiEndpoint;

    @Value("${footballdata.app.apiHeader}")
    private String footballDataApiHeader;

    @Value("${footballdata.app.apiToken}")
    private String footballDataApiToken;
}
