package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.api.CompetitionsMap;
import com.radoslawzerek.bigbetbackend.api.theoddsapi.OddsApiBetProspect;
import com.radoslawzerek.bigbetbackend.api.theoddsapi.Site;
import com.radoslawzerek.bigbetbackend.dto.BetProspectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OddsBetProspectMapper {

    @Autowired
    private final CompetitionsMap competitionsMap;

    public BetProspectDto mapFromOddsToBetProspectDto(OddsApiBetProspect oddsApiBetProspect) {
        Integer sport_key = null;
        if (competitionsMap.getCompetitions().containsKey(oddsApiBetProspect.getSport_key())) {
            sport_key = competitionsMap.getCompetitions().get(oddsApiBetProspect.getSport_key()).getId();
        }
        List<String> teams = new ArrayList<>();
        List<BigDecimal> h2h = new ArrayList<>();
        BigDecimal homeTeamWinOdd;
        BigDecimal drawOdd;
        BigDecimal awayTeamWinOdd;
        int sitesQty = 0;
        Double homeTeamWinOddSum = 0.00;
        Double drawOddSum = 0.00;
        Double awayTeamWinOddSum = 0.00;

        if (oddsApiBetProspect.getHome_team().equals(oddsApiBetProspect.getTeams().get(0))) {
            teams.add(oddsApiBetProspect.getTeams().get(0));
            teams.add(oddsApiBetProspect.getTeams().get(1));

            for (Site site : oddsApiBetProspect.getSites()) {
                homeTeamWinOddSum += site.getOdds().getH2h().get(0);
                drawOddSum += site.getOdds().getH2h().get(1);
                awayTeamWinOddSum += site.getOdds().getH2h().get(2);
                sitesQty++;
            }
            homeTeamWinOdd = new BigDecimal(homeTeamWinOddSum / sitesQty).setScale(2, RoundingMode.HALF_UP);
            drawOdd = new BigDecimal(drawOddSum / sitesQty).setScale(2, RoundingMode.HALF_UP);
            awayTeamWinOdd = new BigDecimal(awayTeamWinOddSum / sitesQty).setScale(2, RoundingMode.HALF_UP);
        } else {
            teams.add(oddsApiBetProspect.getTeams().get(1));
            teams.add(oddsApiBetProspect.getTeams().get(0));

            for (Site site : oddsApiBetProspect.getSites()) {
                homeTeamWinOddSum += site.getOdds().getH2h().get(2);
                drawOddSum += site.getOdds().getH2h().get(1);
                awayTeamWinOddSum += site.getOdds().getH2h().get(0);
                sitesQty++;
            }
            homeTeamWinOdd = new BigDecimal(homeTeamWinOddSum / sitesQty).setScale(2, RoundingMode.HALF_UP);
            drawOdd = new BigDecimal(drawOddSum / sitesQty).setScale(2, RoundingMode.HALF_UP);
            awayTeamWinOdd = new BigDecimal(awayTeamWinOddSum / sitesQty).setScale(2, RoundingMode.HALF_UP);
        }
        h2h.add(homeTeamWinOdd);
        h2h.add(drawOdd);
        h2h.add(awayTeamWinOdd);

        ZonedDateTime commence_time = oddsApiBetProspect.getCommence_time();

        BetProspectDto betProspectDto = new BetProspectDto();
        betProspectDto.setSport_key(sport_key);
        betProspectDto.setTeams(teams);
        betProspectDto.setCommence_time(commence_time);
        betProspectDto.setH2h(h2h);

        return betProspectDto;
    }

    public List<BetProspectDto> mapFromOddsToBetProspectDtoList(List<OddsApiBetProspect> oddsApiBetProspectList) {
        return oddsApiBetProspectList.stream()
                .filter(o -> o.getSites().size() > 0)
                .map(this::mapFromOddsToBetProspectDto)
                .collect(Collectors.toList());
    }
}
