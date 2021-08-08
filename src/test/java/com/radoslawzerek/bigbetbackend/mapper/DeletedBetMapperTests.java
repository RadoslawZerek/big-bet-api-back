package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.DeletedBetDto;
import com.radoslawzerek.bigbetbackend.entity.DeletedBet;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.enums.Role;
import com.radoslawzerek.bigbetbackend.enums.Winner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DeletedBetMapperTests {

    @Autowired
    DeletedBetMapper mapper;

    @Test
    public void testMapToDeletedBetDto() {
        //Given
        User user = new User(1L, "Username1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        DeletedBet deletedBet = new DeletedBet(1L, user, "Barcelona", "Real Madrid",
                ZonedDateTime.parse("2020-11-28T18:30:50Z"), Winner.HOME_TEAM, new BigDecimal("1.33"),
                new BigDecimal("50"), LocalDateTime.now());

        //When
        DeletedBetDto deletedBetDto = mapper.mapToDeletedBetDto(deletedBet);

        //Then
        assertEquals(deletedBet.getId(), deletedBetDto.getId());
        assertEquals(deletedBet.getUser().getId(), deletedBetDto.getUserId());
        assertEquals(deletedBet.getHomeTeam(), deletedBetDto.getHomeTeam());
        assertEquals(deletedBet.getAwayTeam(), deletedBetDto.getAwayTeam());
        assertEquals(deletedBet.getCommence_time(), deletedBetDto.getCommence_time());
        assertEquals(deletedBet.getTippedWinner(), deletedBetDto.getTippedWinner());
        assertEquals(deletedBet.getOdd(), deletedBetDto.getOdd());
        assertEquals(deletedBet.getStake(), deletedBetDto.getStake());
        assertEquals(deletedBet.getDeleteTime(), deletedBetDto.getDeleteTime());
    }
}