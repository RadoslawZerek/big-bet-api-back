package com.radoslawzerek.bigbetbackend.api.footballdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullTime {

    @JsonProperty("homeTeam")
    private Integer homeTeamGoals;

    @JsonProperty("awayTeam")
    private Integer awayTeamGoals;
}
