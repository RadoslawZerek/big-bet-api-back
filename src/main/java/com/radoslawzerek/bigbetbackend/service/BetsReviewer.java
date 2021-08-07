package com.radoslawzerek.bigbetbackend.service;

import com.radoslawzerek.bigbetbackend.api.TeamsMap;
import com.radoslawzerek.bigbetbackend.api.footballdata.FootballDataClient;
import com.radoslawzerek.bigbetbackend.api.footballdata.Match;
import com.radoslawzerek.bigbetbackend.entity.Bet;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.entity.UserBalanceChange;
import com.radoslawzerek.bigbetbackend.pojo.Mail;
import com.radoslawzerek.bigbetbackend.repository.BetRepository;
import com.radoslawzerek.bigbetbackend.repository.UserBalanceChangeRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@EnableAspectJAutoProxy
@Service
public class BetsReviewer {

    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final UserBalanceChangeRepository changeRepository;
    private final FootballDataClient dataClient;
    private final SimpleEmailService emailService;
    private final TeamsMap teamsMap;
    private final ChronoUnit minutes = ChronoUnit.MINUTES;


    @Autowired
    public BetsReviewer(BetRepository betRepository, UserRepository userRepository,
                        UserBalanceChangeRepository changeRepository, FootballDataClient dataClient,
                        SimpleEmailService emailService, TeamsMap teamsMap) {
        this.betRepository = betRepository;
        this.userRepository = userRepository;
        this.changeRepository = changeRepository;
        this.dataClient = dataClient;
        this.emailService = emailService;
        this.teamsMap = teamsMap;
    }

    public void updateBetsStatus(Long userId) {
        List<Bet> pendingBets = betRepository.getPendingBetsOfUser(userId);
        List<Bet> betsToReview = pendingBets.stream()
                .filter(bet -> minutes.between(bet.getBetProspect().getCommence_time(), ZonedDateTime.now()) > 105)
                .collect(Collectors.toList());
        synchronized (UserRepository.class){
            User user = userRepository.findById(userId).get();
            int finalized = 0;
            int won = 0;
            BigDecimal cashWin = BigDecimal.ZERO;
            for (Bet bet : betsToReview) {
                List<Match> matches = dataClient.getDailyMatchesResults(bet);
                for (Match match : matches) {
                    if (teamsMatchAndIsFinished(bet, match)) {
                        bet.setFinalized(true);
                        bet.setWinner(match.getScore().getWinner());
                        finalized++;
                        if (bet.getTippedWinner().equals(match.getScore().getWinner())) {
                            bet.setWon(true);
                            bet.setCashWin(bet.getOdd().multiply(bet.getStake()).setScale(2, RoundingMode.HALF_UP));
                            BigDecimal oldBalance = user.getBalance();
                            user.setBalance(user.getBalance().add(bet.getCashWin()));
                            bet.setUser(user);
                            userRepository.save(user);
                            betRepository.save(bet);
                            changeRepository.save(new UserBalanceChange(user, oldBalance, user.getBalance()));
                            won++;
                            cashWin = cashWin.add(bet.getCashWin());
                        } else {
                            bet.setWon(false);
                            bet.setCashWin(BigDecimal.ZERO);
                            betRepository.save(bet);
                        }
                    }
                }
            }
            if (won > 0) {
                sendBetsWonInfoEmail(user, finalized, won, cashWin);
            }
        }

    }

    private void sendBetsWonInfoEmail(User user, int finalized, int won, BigDecimal cashWin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String formattedDateTime = LocalDateTime.now().format(formatter);
        String betsOrBet = "bets";
        if (finalized == 1) {
            betsOrBet = "bet";
        }
        String message = finalized + " " + betsOrBet + " has been finalized at " + formattedDateTime + ". " +
                "Number of winning bets: " + won + ". " +
                "Congratulations! " + cashWin + " € has been added to your balance. " +
                "Your current balance: " + user.getBalance() + " €.";
        Mail mail = new Mail(user.getEmail(), "Bets won info", message);
        emailService.sendBetsWonInfo(mail, user.getUsername());
    }

    private boolean teamsMatchAndIsFinished(Bet bet, Match match) {
        return match.getHomeTeam().getId().equals(teamsMap.getTeams().get(bet.getBetProspect().getTeams().get(0)))
                && match.getAwayTeam().getId().equals(teamsMap.getTeams().get(bet.getBetProspect().getTeams().get(1)))
                && match.getStatus().equals("FINISHED");
    }
}
