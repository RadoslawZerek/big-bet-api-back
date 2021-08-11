package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import com.radoslawzerek.bigbetbackend.mapper.BetProspectsRequestMapper;
import com.radoslawzerek.bigbetbackend.service.BetProspectsRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/bigbet")
@CrossOrigin(origins = "*")
public class BetProspectsRequestController {

    @Autowired
    private final BetProspectsRequestService service;

    @Autowired
    private final BetProspectsRequestMapper mapper;

    @GetMapping("/requests")
    public List<BetProspectsRequestDto> getAllBetProspectsRequests() {
        return mapper.mapToBetProspectsRequestDtoList(service.getAllBetProspectsRequests());
    }

    @GetMapping("/bet_prospect_for_user/{userId}")
    public List<BetProspectsRequestDto> getBetProspectsRequestsOfUser(@PathVariable Long userId) {
        return mapper.mapToBetProspectsRequestDtoList(service.getBetProspectsRequestsOfUser(userId));
    }

}
