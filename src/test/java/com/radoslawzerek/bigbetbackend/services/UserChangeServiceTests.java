package com.radoslawzerek.bigbetbackend.services;

import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.entity.UserBalanceChange;
import com.radoslawzerek.bigbetbackend.entity.UserDataChange;
import com.radoslawzerek.bigbetbackend.enums.ChangedValue;
import com.radoslawzerek.bigbetbackend.enums.Role;
import com.radoslawzerek.bigbetbackend.repository.UserBalanceChangeRepository;
import com.radoslawzerek.bigbetbackend.repository.UserDataChangeRepository;
import com.radoslawzerek.bigbetbackend.service.UserChangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserChangeServiceTests {

    @InjectMocks
    UserChangeService service;

    @Mock
    UserDataChangeRepository dataChangeRepository;

    @Mock
    UserBalanceChangeRepository balanceChangeRepository;

    @Test
    public void testAdUserDataChange() {
        //Given
        User user = new User(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        UserDataChange change = new UserDataChange(1L, user, ChangedValue.USERNAME,
                "Old_username", "New_username", LocalDateTime.now());

        when(dataChangeRepository.save(change)).thenReturn(change);

        //When
        UserDataChange retrievedChange = service.addUserDataChange(change);

        //Then
        assertEquals(change, retrievedChange);
    }

    @Test
    public void testGetAllUsersDataChanges() {
        //Given
        User user = new User(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        UserDataChange change1 = new UserDataChange(1L, user, ChangedValue.USERNAME,
                "Old_username", "New_username", LocalDateTime.now());
        UserDataChange change2 = new UserDataChange(2L, user, ChangedValue.EMAIL,
                "Old_email", "New_email", LocalDateTime.now());
        List<UserDataChange> changes = new ArrayList<>();
        changes.add(change1);
        changes.add(change2);

        when(dataChangeRepository.findAll()).thenReturn(changes);

        //When
        List<UserDataChange> retrievedChanges = service.getAllUsersDataChanges();

        //Then
        assertEquals(changes, retrievedChanges);
    }

    @Test
    public void testGetDataChangesOfUser() {
        //Given
        Long userId = 1L;
        User user = new User(userId, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        UserDataChange change1 = new UserDataChange(1L, user, ChangedValue.USERNAME,
                "Old_username", "New_username", LocalDateTime.now());
        UserDataChange change2 = new UserDataChange(2L, user, ChangedValue.EMAIL,
                "Old_email", "New_email", LocalDateTime.now());
        List<UserDataChange> changes = new ArrayList<>();
        changes.add(change1);
        changes.add(change2);

        when(dataChangeRepository.getDataChangesOfUser(userId)).thenReturn(changes);

        //When
        List<UserDataChange> retrievedChanges = service.getDataChangesOfUser(userId);

        //Then
        assertEquals(changes, retrievedChanges);
    }

    @Test
    public void testAddUserBalanceChange() {
        //Given
        User user = new User(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        UserBalanceChange change = new UserBalanceChange(1L, user,
                new BigDecimal("100"), new BigDecimal("50"), LocalDateTime.now());

        when(balanceChangeRepository.save(change)).thenReturn(change);

        //When
        UserBalanceChange retrievedChange = service.addUserBalanceChange(change);

        //Then
        assertEquals(change, retrievedChange);
    }

    @Test
    public void testGetAllUsersBalanceChanges() {
        //Given
        User user = new User(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        UserBalanceChange change1 = new UserBalanceChange(1L, user,
                new BigDecimal("100"), new BigDecimal("50"), LocalDateTime.now());
        UserBalanceChange change2 = new UserBalanceChange(2L, user,
                new BigDecimal("150"), new BigDecimal("200"), LocalDateTime.now());
        List<UserBalanceChange> changes = new ArrayList<>();
        changes.add(change1);
        changes.add(change2);

        when(balanceChangeRepository.findAll()).thenReturn(changes);

        //When
        List<UserBalanceChange> retrievedChanges = service.getAllUsersBalanceChanges();

        //Then
        assertEquals(changes, retrievedChanges);
    }

    @Test
    public void testGetBalanceChangesOfUser() {
        //Given
        Long userId = 1L;
        User user = new User(userId, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        UserBalanceChange change1 = new UserBalanceChange(1L, user,
                new BigDecimal("100"), new BigDecimal("50"), LocalDateTime.now());
        UserBalanceChange change2 = new UserBalanceChange(2L, user,
                new BigDecimal("150"), new BigDecimal("200"), LocalDateTime.now());
        List<UserBalanceChange> changes = new ArrayList<>();
        changes.add(change1);
        changes.add(change2);

        when(balanceChangeRepository.getBalanceChangesOfUser(userId)).thenReturn(changes);

        //When
        List<UserBalanceChange> retrievedChanges = service.getBalanceChangesOfUser(userId);

        //Then
        assertEquals(changes, retrievedChanges);
    }
}