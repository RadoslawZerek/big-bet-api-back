package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.LogInAttemptDto;
import com.radoslawzerek.bigbetbackend.entity.LogInAttempt;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LogInAttemptMapperTests {

    @Autowired
    LogInAttemptMapper mapper;

    @Test
    public void testMapToLogInAttemptDto() {
        //Given
        User user = new User(1L, "Username1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        LogInAttempt attempt = new LogInAttempt(1L, user, user.getUsername(), false, LocalDateTime.now());

        //When
        LogInAttemptDto attemptDto = mapper.mapToLogInAttemptDto(attempt);

        //Then
        assertEquals(attempt.getId(), attemptDto.getId());
        assertEquals(attempt.getUser().getId(), attemptDto.getUserId());
        assertEquals(attempt.getLogin(), attemptDto.getLogin());
        assertEquals(attempt.getSuccessful(), attemptDto.getSuccessful());
        assertEquals(attempt.getAttemptTime(), attemptDto.getAttemptTime());
    }
}