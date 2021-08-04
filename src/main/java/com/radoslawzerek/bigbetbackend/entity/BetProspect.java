package com.radoslawzerek.bigbetbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "BET_PROSPECTS")
public class BetProspect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "SPORT_KEY")
    private Integer sport_key;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @OrderColumn
    private List<String> teams;

    @Column(name = "COMMENCE_TIME")
    private ZonedDateTime commence_time;

    @ElementCollection(targetClass = BigDecimal.class, fetch = FetchType.EAGER)
    @OrderColumn
    private List<BigDecimal> h2h;

    public BetProspect(Integer sport_key, List<String> teams, ZonedDateTime commence_time, List<BigDecimal> h2h) {
        this.sport_key = sport_key;
        this.teams = teams;
        this.commence_time = commence_time;
        this.h2h = h2h;
    }
}
