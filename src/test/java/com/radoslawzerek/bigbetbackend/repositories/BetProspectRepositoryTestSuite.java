package com.radoslawzerek.bigbetbackend.repositories;

import com.radoslawzerek.bigbetbackend.entity.BetProspect;
import com.radoslawzerek.bigbetbackend.repository.BetProspectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BetProspectRepositoryTestSuite {

    @Autowired
    BetProspectRepository repository;

    @Test
    public void testSaveAndFindById() {
        //Given
        Integer sport_key = 2014;
        List<String> teams = new ArrayList<>();
        teams.add("Elche CF");
        teams.add("Cádiz CF");
        ZonedDateTime commence_time = ZonedDateTime.parse("2020-11-28T13:00:00Z");
        List<BigDecimal> h2h = new ArrayList<>();
        h2h.add(new BigDecimal("3.56"));
        h2h.add(new BigDecimal("3.94"));
        h2h.add(new BigDecimal("4.18"));

        BetProspect betProspect = new BetProspect(sport_key, teams, commence_time, h2h);

        //When
        repository.save(betProspect);
        Long id = betProspect.getId();

        Optional<BetProspect> optionalBetProspect = repository.findById(id);

        //Then
        assertTrue(optionalBetProspect.isPresent());
        System.out.println(optionalBetProspect.get().getH2h());
        System.out.println(optionalBetProspect.get().getTeams());

        //Clean Up
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            //do nothing
        }
    }
}