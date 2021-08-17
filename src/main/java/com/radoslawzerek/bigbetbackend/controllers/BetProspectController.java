package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.api.theoddsapi.facade.BetProspectFacade;
import com.radoslawzerek.bigbetbackend.dto.BetProspectDtoList;
import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import com.radoslawzerek.bigbetbackend.exception.UserNotFoundException;
import com.radoslawzerek.bigbetbackend.service.BetProspectsRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class BetProspectController {

    @Autowired
    private final BetProspectFacade prospectFacade;

    @Autowired
    private final BetProspectsRequestService prospectsRequestService;

    @PostMapping("/prospects")
    public BetProspectDtoList getCurrentBetProspects(@RequestBody BetProspectsRequestDto prospectsRequestDto) throws UserNotFoundException {
        prospectsRequestService.addBetProspectsRequest(prospectsRequestDto);
        return new BetProspectDtoList(prospectFacade.getCurrentBetProspectDtoList(prospectsRequestDto));
    }
}
