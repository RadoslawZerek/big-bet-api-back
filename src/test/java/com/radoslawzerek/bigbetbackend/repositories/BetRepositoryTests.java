package com.radoslawzerek.bigbetbackend.repositories;

import com.radoslawzerek.bigbetbackend.entity.Bet;
import com.radoslawzerek.bigbetbackend.entity.BetProspect;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.enums.Winner;
import com.radoslawzerek.bigbetbackend.repository.BetProspectRepository;
import com.radoslawzerek.bigbetbackend.repository.BetRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BetRepositoryTests {

    @Autowired
    BetProspectRepository betProspectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BetRepository betRepository;

    @Test
    public void testGetPendingBetsOfUser() {
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

        User user1 = new User("Test_user", "Test12@test.com", "test_password");
        userRepository.save(user1);
        Long user1Id = user1.getId();

        Bet bet1 = new Bet(null, user1, betProspect1, LocalDateTime.now(), Winner.HOME_TEAM,
                betProspect1.getH2h().get(0), new BigDecimal("20.00"),
                false, null, false, null);

        betRepository.save(bet1);
        Long bet1Id = bet1.getId();


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

        Bet bet2 = new Bet(null, user1, betProspect2, LocalDateTime.now(), Winner.AWAY_TEAM,
                betProspect2.getH2h().get(0), new BigDecimal("30.00"), true,
                Winner.AWAY_TEAM, true,
                betProspect2.getH2h().get(0).multiply(new BigDecimal("30.00")).setScale(2, RoundingMode.HALF_UP));

        betRepository.save(bet2);
        Long bet2Id = bet2.getId();

        //When
        List<Bet> allUserBets = betRepository.getAllBetsOfUser(user1Id);
        List<Bet> pendingBetsOfUser = betRepository.getPendingBetsOfUser(user1Id);

        //Then
        assertEquals(2, allUserBets.size());
        assertEquals(1, pendingBetsOfUser.size());

        //Clean Up
        try {
            betRepository.deleteById(bet1Id);
            betRepository.deleteById(bet2Id);
            userRepository.deleteById(user1Id);
        } catch (Exception e) {
            //do nothing
        }
    }
}