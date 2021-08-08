package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.UserDto;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserMapperTests {

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final String USERNAME_1 = "Username1";
    private static final String USERNAME_2 = "Username2";

    @Autowired
    UserMapper mapper;

    @Test
    public void testMapToUser() {
        //Given
        UserDto userDto = new UserDto(ID_1, USERNAME_1, "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        //When
        User mappedUser = mapper.mapToUser(userDto);

        //Then
        assertEquals(ID_1, mappedUser.getId());
        assertEquals(USERNAME_1, mappedUser.getUsername());

    }

    @Test
    public void testMapToUserDto() {
        //Given
        User user = new User(ID_1, USERNAME_1, "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        //When
        UserDto mappedUserDto = mapper.mapToUserDto(user);

        //Then
        assertEquals(ID_1, mappedUserDto.getId());
        assertEquals(USERNAME_1, mappedUserDto.getUsername());
    }

    @Test
    public void testMapToUserDtoList() {
        //Given
        User user1 = new User(ID_1, USERNAME_1, "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        User user2 = new User(ID_2, USERNAME_2, "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        //When
        List<UserDto> mappedUserDtoList = mapper.mapToUserDtoList(users);

        //Then
        assertEquals(2, mappedUserDtoList.size());
        assertEquals(ID_1, mappedUserDtoList.get(0).getId());
        assertEquals(USERNAME_1, mappedUserDtoList.get(0).getUsername());
        assertEquals(ID_2, mappedUserDtoList.get(1).getId());
        assertEquals(USERNAME_2, mappedUserDtoList.get(1).getUsername());
    }
}