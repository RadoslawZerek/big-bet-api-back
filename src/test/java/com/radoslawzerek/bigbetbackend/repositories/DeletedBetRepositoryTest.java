package com.radoslawzerek.bigbetbackend.repositories;

import com.radoslawzerek.bigbetbackend.entity.Bet;
import com.radoslawzerek.bigbetbackend.entity.BetProspect;
import com.radoslawzerek.bigbetbackend.entity.DeletedBet;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.enums.Winner;
import com.radoslawzerek.bigbetbackend.repository.DeletedBetRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DeletedBetRepositoryTest {

    @Autowired
    DeletedBetRepository deletedBetRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testGetAllDeletedBetsOfUser() {
        //Given
        Integer sport_key1 = 2014;
        List<String> teams1 = new ArrayList<>();
        teams1.add("Valencia");
        teams1.add("Athletic Bilbao");
        ZonedDateTime commence_time1 = ZonedDateTime.parse("2020-12-12T13:00:00Z");
        List<BigDecimal> h2h1 = new ArrayList<>();
        h2h1.add(new BigDecimal("3.56"));
        h2h1.add(new BigDecimal("3.94"));
        h2h1.add(new BigDecimal("4.18"));

        BetProspect betProspect1 = new BetProspect(sport_key1, teams1, commence_time1, h2h1);

        User user1 = new User("Test_user1", "Test1@test.com", "test_password");
        userRepository.save(user1);
        Long user1Id = user1.getId();

        Bet bet1 = new Bet(null, user1, betProspect1, LocalDateTime.now(), Winner.HOME_TEAM,
                betProspect1.getH2h().get(0), new BigDecimal("20.00"),
                false, null, false, null);

        DeletedBet deletedBet1 = new DeletedBet(bet1);
        deletedBetRepository.save(deletedBet1);
        Long deletedBet1Id = deletedBet1.getId();


        Integer sport_key2 = 2014;
        List<String> teams2 = new ArrayList<>();
        teams2.add("Getafe");
        teams2.add("Sevilla");
        ZonedDateTime commence_time2 = ZonedDateTime.parse("2020-12-12T15:15:00Z");
        List<BigDecimal> h2h_2 = new ArrayList<>();
        h2h_2.add(new BigDecimal("4.32"));
        h2h_2.add(new BigDecimal("2.35"));
        h2h_2.add(new BigDecimal("2.79"));

        BetProspect betProspect2 = new BetProspect(sport_key2, teams2, commence_time2, h2h_2);

        User user2 = new User("Test_user2", "Test2@test.com", "test_password");
        userRepository.save(user2);
        Long user2Id = user2.getId();

        Bet bet2 = new Bet(null, user2, betProspect2, LocalDateTime.now(), Winner.AWAY_TEAM,
                betProspect2.getH2h().get(0), new BigDecimal("30.00"), false,
                null, false, null);

        DeletedBet deletedBet2 = new DeletedBet(bet2);
        deletedBetRepository.save(deletedBet2);
        Long deletedBet2Id = deletedBet2.getId();

        //When
        List<DeletedBet> deletedBetsOfUser1 = deletedBetRepository.getDeletedBetsOfUser(user1Id);
        List<DeletedBet> deletedBetsOfUser2 = deletedBetRepository.getDeletedBetsOfUser(user2Id);
        List<DeletedBet> allDeletedBets = deletedBetRepository.findAll();

        //Then
        assertEquals(1, deletedBetsOfUser1.size());
        assertEquals(1, deletedBetsOfUser2.size());
        assertTrue(allDeletedBets.size() >= 2);

        //Clean Up
        try {
            deletedBetRepository.deleteById(deletedBet1Id);
            deletedBetRepository.deleteById(deletedBet2Id);
            userRepository.deleteById(user1Id);
            userRepository.deleteById(user2Id);
        } catch (Exception e) {
            //do nothing
        }
    }
}