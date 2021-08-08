package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.api.theoddsapi.facade.BetProspectFacade;
import com.radoslawzerek.bigbetbackend.dto.BetProspectDtoList;
import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import com.radoslawzerek.bigbetbackend.exception.UserNotFoundException;
import com.radoslawzerek.bigbetbackend.service.BetProspectsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bigbet")
@CrossOrigin(origins = "*")
public class BetProspectController {

    private final BetProspectFacade prospectFacade;
    private final BetProspectsRequestService prospectsRequestService;


    @Autowired
    public BetProspectController(BetProspectFacade prospectFacade,
                                 BetProspectsRequestService prospectsRequestService) {
        this.prospectFacade = prospectFacade;
        this.prospectsRequestService = prospectsRequestService;
    }

    @PostMapping("/prospects")
    public BetProspectDtoList getCurrentBetProspects(@RequestBody BetProspectsRequestDto prospectsRequestDto) throws UserNotFoundException {
        prospectsRequestService.addBetProspectsRequest(prospectsRequestDto);
        return new BetProspectDtoList(prospectFacade.getCurrentBetProspectDtoList(prospectsRequestDto));
    }
}
