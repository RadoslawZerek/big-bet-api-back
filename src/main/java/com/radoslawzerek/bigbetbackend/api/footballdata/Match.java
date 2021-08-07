package com.radoslawzerek.bigbetbackend.api.footballdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
    private ZonedDateTime utcDate;
    private String status;
    private Score score;
    private Team homeTeam;
    private Team awayTeam;
}
