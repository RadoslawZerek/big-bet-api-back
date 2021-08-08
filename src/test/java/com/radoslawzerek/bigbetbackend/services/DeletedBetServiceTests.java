package com.radoslawzerek.bigbetbackend.services;

import com.radoslawzerek.bigbetbackend.entity.Bet;
import com.radoslawzerek.bigbetbackend.entity.BetProspect;
import com.radoslawzerek.bigbetbackend.entity.DeletedBet;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.enums.Role;
import com.radoslawzerek.bigbetbackend.enums.Winner;
import com.radoslawzerek.bigbetbackend.repository.DeletedBetRepository;
import com.radoslawzerek.bigbetbackend.service.DeletedBetService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeletedBetServiceTests {

    @InjectMocks
    DeletedBetService service;

    @Mock
    DeletedBetRepository repository;

    @Test
    public void testAddDeletedBet() {
        //Given
        List<DeletedBet> deletedBets = returnTestBets();
        DeletedBet deletedBet = deletedBets.get(0);

        when(repository.save(deletedBet)).thenReturn(deletedBet);

        //When
        DeletedBet retrievedDeletedBet = service.addDeletedBet(deletedBet);

        //Then
        assertEquals(deletedBet, retrievedDeletedBet);
    }

    @Test
    public void testGetAllDeletedBets() {
        //Given
        List<DeletedBet> deletedBets = returnTestBets();
        DeletedBet deletedBet1 = deletedBets.get(0);
        DeletedBet deletedBet2 = deletedBets.get(1);

        when(repository.findAll()).thenReturn(deletedBets);

        //When
        List<DeletedBet> retrievedDeletedBets = service.getAllDeletedBets();

        //Then
        assertEquals(2, retrievedDeletedBets.size());
        assertEquals(deletedBet1, retrievedDeletedBets.get(0));
        assertEquals(deletedBet2, retrievedDeletedBets.get(1));
    }

    @Test
    public void testGetAllDeletedBetsOfUser() {
        //Given
        Long userId = 1L;
        List<DeletedBet> deletedBets = returnTestBets();
        DeletedBet deletedBet1 = deletedBets.get(0);
        DeletedBet deletedBet2 = deletedBets.get(1);

        when(repository.getDeletedBetsOfUser(userId)).thenReturn(deletedBets);

        //When
        List<DeletedBet> retrievedDeletedBets = service.getDeletedBetsOfUser(userId);

        //Then
        assertEquals(2, retrievedDeletedBets.size());
        assertEquals(deletedBet1, retrievedDeletedBets.get(0));
        assertEquals(deletedBet2, retrievedDeletedBets.get(1));
    }

    public List<DeletedBet> returnTestBets() {

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

        List<DeletedBet> deletedBets = new ArrayList<>();
        deletedBets.add(new DeletedBet(bet1));
        deletedBets.add(new DeletedBet(bet2));

        return deletedBets;
    }
}