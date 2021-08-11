package com.radoslawzerek.bigbetbackend.service;

import com.radoslawzerek.bigbetbackend.entity.DeletedBet;
import com.radoslawzerek.bigbetbackend.repository.DeletedBetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeletedBetService {

    @Autowired
    private final DeletedBetRepository repository;

    public DeletedBet addDeletedBet(DeletedBet deletedBet) {
        return repository.save(deletedBet);
    }

    public List<DeletedBet> getAllDeletedBets() {
        return repository.findAll();
    }

    public List<DeletedBet> getDeletedBetsOfUser(Long userId) {
        return repository.getDeletedBetsOfUser(userId);
    }
}

