package com.radoslawzerek.bigbetbackend.api.theoddsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OddsApiBetProspect {

    private String sport_key;
    private List<String> teams;
    private String home_team;
    private ZonedDateTime commence_time;
    private List<Site> sites;
}
