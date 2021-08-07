package com.radoslawzerek.bigbetbackend.api;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class LeaguesMap {
    private final Map<String, String> leagues;

    public LeaguesMap() {
        leagues = new HashMap<>();
        leagues.put("Serie A - Italy", "soccer_italy_serie_a");
        leagues.put("La Liga - Spain", "soccer_spain_la_liga");
        leagues.put("English Premier League", "soccer_epl");
    }
}
