package com.radoslawzerek.bigbetbackend.services;

import com.radoslawzerek.bigbetbackend.entity.Bet;
import com.radoslawzerek.bigbetbackend.entity.BetProspect;
import com.radoslawzerek.bigbetbackend.entity.DeletedBet;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.entity.UserBalanceChange;
import com.radoslawzerek.bigbetbackend.enums.Role;
import com.radoslawzerek.bigbetbackend.enums.Winner;
import com.radoslawzerek.bigbetbackend.repository.BetRepository;
import com.radoslawzerek.bigbetbackend.repository.DeletedBetRepository;
import com.radoslawzerek.bigbetbackend.repository.UserBalanceChangeRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import com.radoslawzerek.bigbetbackend.service.BetService;
import com.radoslawzerek.bigbetbackend.service.BetsReviewer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BetServiceTests {

    @InjectMocks
    BetService betService;

    @Mock
    private BetRepository betRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBalanceChangeRepository balanceChangeRepository;

    @Mock
    private DeletedBetRepository deletedBetRepository;

    @Mock
    private BetsReviewer betsReviewer;

    @Test
    public void testAddBet() {
        //Given
        List<Bet> bets = returnTestBets();
        Bet bet = bets.get(0);

        User beforeBetUser = new User(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("200"));
        Optional<User> optionalUser = Optional.of(beforeBetUser);

        when(userRepository.findById(bet.getUser().getId())).thenReturn(optionalUser);

        //When
        betService.addBet(bet);

        //Then
        verify(userRepository, times(1)).save(bet.getUser());
        verify(betRepository, times(1)).save(bet);
        verify(balanceChangeRepository, times(1)).save(any(UserBalanceChange.class));

    }

    @Test
    public void testGetAllBets() {
        //Given
        List<Bet> bets = returnTestBets();
        Bet bet1 = bets.get(0);
        Bet bet2 = bets.get(1);

        List<User> users = new ArrayList<>();
        users.add(bet1.getUser());

        when(betRepository.findAll()).thenReturn(bets);
        when(userRepository.findAll()).thenReturn(users);

        //When
        List<Bet> retrievedBets = betService.getAllBets();

        //Then
        assertEquals(2, retrievedBets.size());
        assertEquals(bet1, retrievedBets.get(0));
        assertEquals(bet2, retrievedBets.get(1));

        verify(betsReviewer, times(1)).updateBetsStatus(bet1.getId());
    }

    @Test
    public void testGetAllBetsOfUser() {
        //Given
        List<Bet> bets = returnTestBets();
        Bet bet1 = bets.get(0);
        Bet bet2 = bets.get(1);

        Long userId = bet1.getUser().getId();

        when(betRepository.getAllBetsOfUser(userId)).thenReturn(bets);

        //When
        List<Bet> retrievedBets = betService.getAllBetsOfUser(userId);

        //Then
        assertEquals(2, retrievedBets.size());
        assertEquals(bet1, retrievedBets.get(0));
        assertEquals(bet2, retrievedBets.get(1));

        verify(betsReviewer, times(1)).updateBetsStatus(bet1.getId());
    }

    @Test
    public void testGetPendingBetsOfUser() {
        //Given
        List<Bet> bets = returnTestBets();
        Bet bet1 = bets.get(0);
        Bet bet2 = bets.get(1);

        Long userId = bet1.getUser().getId();

        when(betRepository.getPendingBetsOfUser(userId)).thenReturn(bets);

        //When
        List<Bet> retrievedBets = betService.getPendingBetsOfUser(userId);

        //Then
        assertEquals(2, retrievedBets.size());
        assertEquals(bet1, retrievedBets.get(0));
        assertEquals(bet2, retrievedBets.get(1));

        verify(betsReviewer, times(1)).updateBetsStatus(bet1.getId());
    }

    @Test
    public void testDeleteBet() {

        //Given
        List<Bet> bets = returnTestBets();
        Bet bet = bets.get(0);
        Long betId = bet.getId();
        Optional<Bet> optionalBet = Optional.of(bet);

        when(betRepository.existsById(betId)).thenReturn(true);
        when(betRepository.findById(betId)).thenReturn(optionalBet);

        //When
        betService.deleteBet(betId);

        //Then
        verify(deletedBetRepository, times(1)).save(any(DeletedBet.class));
        verify(betRepository, times(1)).deleteById(betId);
    }

    public List<Bet> returnTestBets() {

        User user = new User(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        Integer sport_key1 = 2014;
        List<String> teams1 = new ArrayList<>();
        teams1.add("Valencia");
        teams1.add("Athletic Bilbao");
        ZonedDateTime commence_time1 = ZonedDateTime.parse("2020-12-20T13:00:00Z");
        List<BigDecimal> h2h1 = new ArrayList<>();
        h2h1.add(new BigDecimal("3.56"));
        h2h1.add(new BigDecimal("3.94"));
        h2h1.add(new BigDecimal("4.18"));

        BetProspect betProspect1 = new BetProspect(1L, sport_key1, teams1, commence_time1, h2h1);

        Bet bet1 = new Bet(1L, user, betProspect1, LocalDateTime.now(), Winner.HOME_TEAM,
                betProspect1.getH2h().get(0), new BigDecimal("20.00"),
                false, null, false, null);

        Bet bet2 = new Bet(2L, user, betProspect1, LocalDateTime.now(), Winner.AWAY_TEAM,
                betProspect1.getH2h().get(0), new BigDecimal("50.00"),
                false, null, false, null);

        List<Bet> bets = new ArrayList<>();
        bets.add(bet1);
        bets.add(bet2);

        return bets;
    }
}