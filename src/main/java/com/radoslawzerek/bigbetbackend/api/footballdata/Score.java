package com.radoslawzerek.bigbetbackend.api.footballdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.radoslawzerek.bigbetbackend.enums.Winner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Score {
    private Winner winner;
    private FullTime fullTime;
}
