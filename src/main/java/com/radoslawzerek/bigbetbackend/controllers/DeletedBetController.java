package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.dto.DeletedBetDto;
import com.radoslawzerek.bigbetbackend.mapper.DeletedBetMapper;
import com.radoslawzerek.bigbetbackend.service.DeletedBetService;
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
public class DeletedBetController {

    @Autowired
    private final DeletedBetService service;

    @Autowired
    private final DeletedBetMapper mapper;

    @GetMapping("/deleted_bets")
    public List<DeletedBetDto> getAllDeletedBets() {
        return mapper.mapToDeletedBetDtoList(service.getAllDeletedBets());
    }

    @GetMapping("/deleted_bets/{userId}")
    public List<DeletedBetDto> getAllDeletedBetsOfUser(@PathVariable Long userId) {
        return mapper.mapToDeletedBetDtoList(service.getDeletedBetsOfUser(userId));
    }

}
