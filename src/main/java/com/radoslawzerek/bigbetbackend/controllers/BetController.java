package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.dto.BetDto;
import com.radoslawzerek.bigbetbackend.dto.BetDtoList;
import com.radoslawzerek.bigbetbackend.mapper.BetMapper;
import com.radoslawzerek.bigbetbackend.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/bigbet")
@CrossOrigin(origins = "*")
public class BetController {

    @Autowired
    private final BetService service;

    @Autowired
    private final BetMapper mapper;

    @GetMapping("/allBets")
    public BetDtoList getAllBets() {
        return new BetDtoList(mapper.mapToBetDtoList(service.getAllBets()));
    }

    @GetMapping("/bets/{userId}/{onlyPending}")
    public BetDtoList getBetsOfUser(@PathVariable Long userId, @PathVariable Boolean onlyPending) {
        if (onlyPending) {
            return new BetDtoList(mapper.mapToBetDtoList(service.getPendingBetsOfUser(userId)));
        }
        return new BetDtoList(mapper.mapToBetDtoList(service.getAllBetsOfUser(userId)));
    }


    @PostMapping(value = "/addBet", consumes = APPLICATION_JSON_VALUE)
    public void addBet(@RequestBody BetDto betDto) {
        service.addBet(mapper.mapToBet(betDto));
    }

    @DeleteMapping("/{betId}")
    public void deleteBet(@PathVariable Long betId) {
        service.deleteBet(betId);
    }
}
