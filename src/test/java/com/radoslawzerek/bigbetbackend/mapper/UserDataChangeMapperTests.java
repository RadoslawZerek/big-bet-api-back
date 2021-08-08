package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.UserDataChangeDto;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.entity.UserDataChange;
import com.radoslawzerek.bigbetbackend.enums.ChangedValue;
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
public class UserDataChangeMapperTests {

    @Autowired
    UserDataChangeMapper mapper;

    @Test
    public void mapToUserDataChangeDto() {
        //Given
        User user = new User(1L, "Username1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        UserDataChange change = new UserDataChange(1L, user, ChangedValue.USERNAME,
                "Old_username", "New_username", LocalDateTime.now());

        //When
        UserDataChangeDto mappedChangeDto = mapper.mapToUserDataChangeDto(change);

        //Then
        assertEquals(change.getId(), mappedChangeDto.getId());
        assertEquals(change.getUser().getId(), mappedChangeDto.getUserId());
        assertEquals(change.getChangedValue(), mappedChangeDto.getChangedValue());
        assertEquals(change.getChangedValue(), mappedChangeDto.getChangedValue());
        assertEquals(change.getOldValue(), mappedChangeDto.getOldValue());
        assertEquals(change.getNewValue(), mappedChangeDto.getNewValue());
        assertEquals(change.getChangeTime(), mappedChangeDto.getChangeTime());
    }

    @Test
    public void mapToUserDataChangeDtoList() {
        //Given
        User user = new User(1L, "Username1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        UserDataChange change1 = new UserDataChange(1L, user, ChangedValue.USERNAME,
                "Old_username", "New_username", LocalDateTime.now());
        UserDataChange change2 = new UserDataChange(1L, user, ChangedValue.EMAIL,
                "Old_email", "New_email", LocalDateTime.now());

        List<UserDataChange> changes = new ArrayList<>();
        changes.add(change1);
        changes.add(change2);

        //When
        List<UserDataChangeDto> mappedList = mapper.mapToUserDataChangeDtoList(changes);

        //Then
        assertEquals(change1.getId(), mappedList.get(0).getId());
        assertEquals(change1.getUser().getId(), mappedList.get(0).getUserId());
        assertEquals(change1.getChangedValue(), mappedList.get(0).getChangedValue());
        assertEquals(change1.getChangedValue(), mappedList.get(0).getChangedValue());
        assertEquals(change1.getOldValue(), mappedList.get(0).getOldValue());
        assertEquals(change1.getNewValue(), mappedList.get(0).getNewValue());
        assertEquals(change1.getChangeTime(), mappedList.get(0).getChangeTime());
        assertEquals(change2.getId(), mappedList.get(1).getId());
        assertEquals(change2.getUser().getId(), mappedList.get(1).getUserId());
        assertEquals(change2.getChangedValue(), mappedList.get(1).getChangedValue());
        assertEquals(change2.getChangedValue(), mappedList.get(1).getChangedValue());
        assertEquals(change2.getOldValue(), mappedList.get(1).getOldValue());
        assertEquals(change2.getNewValue(), mappedList.get(1).getNewValue());
        assertEquals(change2.getChangeTime(), mappedList.get(1).getChangeTime());
    }
}