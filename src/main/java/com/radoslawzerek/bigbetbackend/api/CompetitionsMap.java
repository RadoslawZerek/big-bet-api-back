package com.radoslawzerek.bigbetbackend.api;

import com.radoslawzerek.bigbetbackend.api.theoddsapi.Competition;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class CompetitionsMap {
    private final Map<String, Competition> competitions;

    public CompetitionsMap() {
        competitions = new HashMap<>();
        competitions.put("soccer_epl", new Competition(2021, "English Premier League"));
        competitions.put("soccer_spain_la_liga", new Competition(2014, "La Liga - Spain"));
        competitions.put("soccer_italy_serie_a", new Competition(2019, "Serie A - Italy"));
    }
}
