package com.radoslawzerek.bigbetbackend.apis.theoddsapi.facade;

import com.radoslawzerek.bigbetbackend.api.LeaguesMap;
import com.radoslawzerek.bigbetbackend.api.theoddsapi.TheOddsApiClient;
import com.radoslawzerek.bigbetbackend.api.theoddsapi.facade.BetProspectFacade;
import com.radoslawzerek.bigbetbackend.dto.BetProspectDto;
import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import com.radoslawzerek.bigbetbackend.mapper.OddsBetProspectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BetProspectFacadeTestSuite {

    @InjectMocks
    private BetProspectFacade betProspectFacade;

    @Mock
    private TheOddsApiClient oddsApiClient;

    @Mock
    private OddsBetProspectMapper mapper;

    @Mock
    private LeaguesMap leaguesMap;

    @Mock
    private Map<String, String> mockMap;


    @Test
    public void testGetCurrentBetProspectDtoList() {

        //Given
        Long userId = 1L;
        String league = "La Liga - Spain";
        List<String> leagues = Collections.singletonList(league);

        BetProspectsRequestDto request = new BetProspectsRequestDto(1L, userId, leagues, LocalDateTime.now());

        Integer sport_key = 2014;
        List<String> teams = new ArrayList<>();
        teams.add("Elche CF");
        teams.add("Cádiz CF");
        ZonedDateTime commence_time = ZonedDateTime.parse("2020-11-28T13:00:00Z");
        List<BigDecimal> h2h = new ArrayList<>();
        h2h.add(new BigDecimal("3.56"));
        h2h.add(new BigDecimal("3.94"));
        h2h.add(new BigDecimal("4.18"));

        BetProspectDto betProspectDto = new BetProspectDto(1L, sport_key, teams, commence_time, h2h);
        List<BetProspectDto> betProspectDtoList = Collections.singletonList(betProspectDto);

        when(leaguesMap.getLeagues()).thenReturn(mockMap);
        when(mockMap.get(league)).thenReturn("Test league");
        when(oddsApiClient.getCurrentOddsProspectFrom("Test league")).thenReturn(new ArrayList<>());
        when(mapper.mapFromOddsToBetProspectDtoList(anyList())).thenReturn(betProspectDtoList);

        //When
        List<BetProspectDto> retrievedList = betProspectFacade.getCurrentBetProspectDtoList(request);

        //Then
        assertEquals(1, retrievedList.size());
        assertEquals(betProspectDto, retrievedList.get(0));
    }
}