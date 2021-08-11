package com.radoslawzerek.bigbetbackend.service;

import com.radoslawzerek.bigbetbackend.entity.Bet;
import com.radoslawzerek.bigbetbackend.entity.DeletedBet;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.entity.UserBalanceChange;
import com.radoslawzerek.bigbetbackend.repository.BetRepository;
import com.radoslawzerek.bigbetbackend.repository.DeletedBetRepository;
import com.radoslawzerek.bigbetbackend.repository.UserBalanceChangeRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BetService {

    @Autowired
    private final BetRepository betRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserBalanceChangeRepository balanceChangeRepository;

    @Autowired
    private final DeletedBetRepository deletedBetRepository;

    @Autowired
    private final BetsReviewer betsReviewer;

    public void addBet(Bet bet) {
        BigDecimal oldBalance = userRepository.findById(bet.getUser().getId()).get().getBalance();
        userRepository.save(bet.getUser());
        betRepository.save(bet);
        balanceChangeRepository.save(new UserBalanceChange(bet.getUser(), oldBalance, bet.getUser().getBalance()));
    }

    public List<Bet> getAllBets() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            betsReviewer.updateBetsStatus(user.getId());
        }
        return betRepository.findAll();
    }

    public List<Bet> getAllBetsOfUser(Long userId) {
        betsReviewer.updateBetsStatus(userId);
        return betRepository.getAllBetsOfUser(userId);
    }

    public List<Bet> getPendingBetsOfUser(Long userId) {
        betsReviewer.updateBetsStatus(userId);
        return betRepository.getPendingBetsOfUser(userId);
    }

    public void deleteBet(Long betId) {
        if (betRepository.existsById(betId)) {
            Bet bet = betRepository.findById(betId).get();
            deletedBetRepository.save(new DeletedBet(bet));
        }
        betRepository.deleteById(betId);
    }
}
