package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.UserBalanceChangeDto;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.entity.UserBalanceChange;
import com.radoslawzerek.bigbetbackend.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBalanceChangeMapperTests {

    @Autowired
    UserBalanceChangeMapper mapper;

    @Test
    public void mapToUserBalanceChangeDto() {
        //Given
        User user = new User(1L, "Username1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        UserBalanceChange change = new UserBalanceChange(1L, user, new BigDecimal("80"),
                new BigDecimal("100"), LocalDateTime.now());

        //When
        UserBalanceChangeDto mappedChangeDto = mapper.mapToUserBalanceChangeDto(change);

        //Then
        assertEquals(change.getId(), mappedChangeDto.getId());
        assertEquals(change.getUser().getId(), mappedChangeDto.getUserId());
        assertEquals(change.getOldValue(), mappedChangeDto.getOldValue());
        assertEquals(change.getNewValue(), mappedChangeDto.getNewValue());
        assertEquals(change.getChangeTime(), mappedChangeDto.getChangeTime());
    }

    @Test
    public void mapToUserDataChangeDtoList() {
        //Given
        User user = new User(1L, "Username1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        UserBalanceChange change1 = new UserBalanceChange(1L, user, new BigDecimal("80"),
                new BigDecimal("100"), LocalDateTime.now());
        UserBalanceChange change2 = new UserBalanceChange(2L, user, new BigDecimal("150"),
                new BigDecimal("100"), LocalDateTime.now());

        List<UserBalanceChange> changes = new ArrayList<>();
        changes.add(change1);
        changes.add(change2);

        //When
        List<UserBalanceChangeDto> mappedList = mapper.mapToUserBalanceChangeDtoList(changes);

        //Then
        assertEquals(change1.getId(), mappedList.get(0).getId());
        assertEquals(change1.getUser().getId(), mappedList.get(0).getUserId());
        assertEquals(change1.getOldValue(), mappedList.get(0).getOldValue());
        assertEquals(change1.getNewValue(), mappedList.get(0).getNewValue());
        assertEquals(change1.getChangeTime(), mappedList.get(0).getChangeTime());
        assertEquals(change2.getId(), mappedList.get(1).getId());
        assertEquals(change2.getUser().getId(), mappedList.get(1).getUserId());
        assertEquals(change2.getOldValue(), mappedList.get(1).getOldValue());
        assertEquals(change2.getNewValue(), mappedList.get(1).getNewValue());
        assertEquals(change2.getChangeTime(), mappedList.get(1).getChangeTime());
    }
}