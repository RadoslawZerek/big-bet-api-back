package com.radoslawzerek.bigbetbackend.service;

import com.radoslawzerek.bigbetbackend.entity.DeletedBet;
import com.radoslawzerek.bigbetbackend.repository.DeletedBetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeletedBetService {

    private final DeletedBetRepository repository;


    public DeletedBetService(DeletedBetRepository repository) {
        this.repository = repository;
    }

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

