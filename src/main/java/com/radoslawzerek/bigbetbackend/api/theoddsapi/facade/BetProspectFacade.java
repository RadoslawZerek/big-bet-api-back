package com.radoslawzerek.bigbetbackend.api.theoddsapi.facade;

import com.radoslawzerek.bigbetbackend.api.theoddsapi.OddsApiBetProspect;
import com.radoslawzerek.bigbetbackend.api.theoddsapi.TheOddsApiClient;
import com.radoslawzerek.bigbetbackend.dto.BetProspectDto;
import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import com.radoslawzerek.bigbetbackend.api.LeaguesMap;
import com.radoslawzerek.bigbetbackend.mapper.OddsBetProspectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BetProspectFacade {

    private final TheOddsApiClient oddsApiClient;
    private final OddsBetProspectMapper mapper;
    private final LeaguesMap leaguesMap;

    @Autowired
    public BetProspectFacade(TheOddsApiClient oddsApiClient, OddsBetProspectMapper mapper,
                             LeaguesMap leaguesMap) {
        this.oddsApiClient = oddsApiClient;
        this.mapper = mapper;
        this.leaguesMap = leaguesMap;
    }

    public List<BetProspectDto> getCurrentBetProspectDtoList(BetProspectsRequestDto prospectsRequestDto) {
        List<OddsApiBetProspect> prospectList = new ArrayList<>();
        for (String league : prospectsRequestDto.getLeagues()) {
            prospectList.addAll(oddsApiClient.getCurrentOddsProspectFrom(leaguesMap.getLeagues().get(league)));
        }

        ChronoUnit seconds = ChronoUnit.SECONDS;
        prospectList = prospectList.stream()
                .filter(prospect -> seconds.between(prospect.getCommence_time(), ZonedDateTime.now()) < 0)
                .collect(Collectors.toList());
        return mapper.mapFromOddsToBetProspectDtoList(prospectList);
    }
}
