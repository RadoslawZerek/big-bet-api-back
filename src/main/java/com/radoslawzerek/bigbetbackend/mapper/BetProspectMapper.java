package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.BetProspectDto;
import com.radoslawzerek.bigbetbackend.entity.BetProspect;
import org.springframework.stereotype.Component;

@Component
public class BetProspectMapper {

    public BetProspect mapToBetProspect(BetProspectDto betProspectDto) {
        return new BetProspect(betProspectDto.getId(), betProspectDto.getSport_key(), betProspectDto.getTeams(),
                betProspectDto.getCommence_time(), betProspectDto.getH2h());
    }

    public BetProspectDto mapToBetProspectDto(BetProspect betProspect) {
        return new BetProspectDto(betProspect.getId(), betProspect.getSport_key(), betProspect.getTeams(),
                betProspect.getCommence_time(), betProspect.getH2h());
    }

}
