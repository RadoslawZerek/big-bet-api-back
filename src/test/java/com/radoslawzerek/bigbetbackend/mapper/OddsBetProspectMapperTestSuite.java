package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.api.CompetitionsMap;
import com.radoslawzerek.bigbetbackend.api.theoddsapi.ApiOdds;
import com.radoslawzerek.bigbetbackend.api.theoddsapi.OddsApiBetProspect;
import com.radoslawzerek.bigbetbackend.api.theoddsapi.Site;
import com.radoslawzerek.bigbetbackend.dto.BetProspectDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OddsBetProspectMapperTestSuite {

    private static final String HOME_TEAM = "Barcelona";
    private static final String AWAY_TEAM = "Real Madrid";
    private static final String LA_LIGA = "soccer_spain_la_liga";

    @Autowired
    OddsBetProspectMapper oddsBetProspectMapper;

    @Autowired
    CompetitionsMap competitionsMap;

    @Test
    public void mapToBetProspectDto() {
        //Given
        List<Double> h2h1 = new ArrayList<>();
        h2h1.add(5.45);
        h2h1.add(3.58);
        h2h1.add(4.24);
        ApiOdds apiOdds1 = new ApiOdds(h2h1);
        Site site1 = new Site(apiOdds1);

        List<Double> h2h2 = new ArrayList<>();
        h2h2.add(5.65);
        h2h2.add(3.18);
        h2h2.add(4.44);
        ApiOdds apiOdds2 = new ApiOdds(h2h2);
        Site site2 = new Site(apiOdds2);

        List<Site> sites = new ArrayList<>();
        sites.add(site1);
        sites.add(site2);

        List<String> teams = new ArrayList<>();
        teams.add(AWAY_TEAM);
        teams.add(HOME_TEAM);

        ZonedDateTime commenceTime = ZonedDateTime.parse("2020-11-28T18:30:50Z");

        OddsApiBetProspect apiBetProspect = new OddsApiBetProspect(
                LA_LIGA, teams, HOME_TEAM, commenceTime, sites);

        //When
        BetProspectDto betProspectDto = oddsBetProspectMapper.mapFromOddsToBetProspectDto(apiBetProspect);

        //Then
        assertEquals(competitionsMap.getCompetitions().get(LA_LIGA).getId(), betProspectDto.getSport_key());
        assertEquals(HOME_TEAM, betProspectDto.getTeams().get(0));
        assertEquals(AWAY_TEAM, betProspectDto.getTeams().get(1));
        assertEquals(new BigDecimal("4.34"), betProspectDto.getH2h().get(0));
        assertEquals(new BigDecimal("3.38"), betProspectDto.getH2h().get(1));
        assertEquals(new BigDecimal("5.55"), betProspectDto.getH2h().get(2));
        assertEquals(commenceTime, betProspectDto.getCommence_time());

    }
}