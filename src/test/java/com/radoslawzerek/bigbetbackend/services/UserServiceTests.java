package com.radoslawzerek.bigbetbackend.services;

import com.radoslawzerek.bigbetbackend.dto.UserDto;
import com.radoslawzerek.bigbetbackend.entity.LogInAttempt;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.entity.UserBalanceChange;
import com.radoslawzerek.bigbetbackend.entity.UserDataChange;
import com.radoslawzerek.bigbetbackend.enums.Role;
import com.radoslawzerek.bigbetbackend.exception.UserNotFoundException;
import com.radoslawzerek.bigbetbackend.mapper.UserMapper;
import com.radoslawzerek.bigbetbackend.pojo.LogInFeedback;
import com.radoslawzerek.bigbetbackend.pojo.SignUpFeedback;
import com.radoslawzerek.bigbetbackend.repository.LogInAttemptRepository;
import com.radoslawzerek.bigbetbackend.repository.UserBalanceChangeRepository;
import com.radoslawzerek.bigbetbackend.repository.UserDataChangeRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import com.radoslawzerek.bigbetbackend.service.BetsReviewer;
import com.radoslawzerek.bigbetbackend.service.UserService;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDataChangeRepository dataChangeRepository;

    @Mock
    private UserBalanceChangeRepository balanceChangeRepository;

    @Mock
    private LogInAttemptRepository logInAttemptRepository;

    @Mock
    private UserMapper mapper;

    @Mock
    private StrongPasswordEncryptor encryptor;

    @Mock
    private BetsReviewer betsReviewer;

    @Test
    public void testLogUserIn() {

        //Given
        String login = "Test_user";
        String password = "test_password";

        User user = new User(1L, login, "Test1@test.com",
                password, Role.USER, LocalDate.now(), new BigDecimal("100"));
        Optional<User> optionalUser = Optional.of(user);

        UserDto userDto = new UserDto(1L, login, "Test1@test.com",
                password, Role.USER, LocalDate.now(), new BigDecimal("100"));

        when(userRepository.findByUsername(login)).thenReturn(optionalUser);
        when(encryptor.checkPassword(password, user.getPassword())).thenReturn(true);
        when(mapper.mapToUserDto(user)).thenReturn(userDto);

        //When
        LogInFeedback feedback = userService.logUserIn(login, password);

        //Then
        assertEquals(userDto, feedback.getUser());
        assertEquals("User is logged in.", feedback.getMessage());
        verify(betsReviewer, times(1)).updateBetsStatus(user.getId());
        verify(logInAttemptRepository, times(1)).save(any(LogInAttempt.class));
    }

    @Test
    public void testTryLogUserInWithNotExistingLogin() {
        //Given
        String login = "Test_user";
        String password = "test_password";

        when(userRepository.findByUsername(login)).thenReturn(Optional.empty());

        //When
        LogInFeedback feedback = userService.logUserIn(login, password);

        //Then
        assertNull(feedback.getUser());
        assertEquals("User with pointed login does not exist!", feedback.getMessage());
    }

    @Test
    public void testTryLogUserInWithWrongPassword() {
        //Given
        String login = "Test_user";
        String password = "test_password";

        User user = new User(1L, login, "Test1@test.com",
                password, Role.USER, LocalDate.now(), new BigDecimal("100"));
        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findByUsername(login)).thenReturn(optionalUser);
        when(encryptor.checkPassword(password, user.getPassword())).thenReturn(false);

        //When
        LogInFeedback feedback = userService.logUserIn(login, password);

        //Then
        assertNull(feedback.getUser());
        assertEquals("Wrong user password!", feedback.getMessage());
        verify(logInAttemptRepository, times(1)).save(any(LogInAttempt.class));
    }

    @Test
    public void testSignUserUp() {
        //Given

        User user = new User(1L, "Test_user", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        UserDto userDto = new UserDto(1L, "Test_user", "Test1@test.com",
                "encrypted_test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(encryptor.encryptPassword(user.getPassword())).thenReturn("encrypted_test_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(mapper.mapToUserDto(any(User.class))).thenReturn(userDto);

        //When
        SignUpFeedback feedback = userService.signUserUp(user);

        //Then
        assertEquals(userDto, feedback.getUser());
        assertEquals("Sign Up successful", feedback.getMessage());
    }

    @Test
    public void testTrySignUserUpWithExistingLogin() {
        //Given
        User user = new User(1L, "Test_user", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        //When
        SignUpFeedback feedback = userService.signUserUp(user);

        //Then
        assertNull(feedback.getUser());
        assertEquals("User with pointed email already exists!", feedback.getMessage());

    }

    @Test
    public void updateUserName() throws UserNotFoundException {
        //Given
        User user = new User(1L, "Test_user", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        User updatedUser = new User(1L, "Updated_user", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        when(userRepository.findById(updatedUser.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        //When
        User userAfterUpdate = userService.updateUser(updatedUser);

        //Then
        assertEquals(updatedUser, userAfterUpdate);
        verify(dataChangeRepository, times(1)).save(any(UserDataChange.class));
    }

    @Test
    public void updateUserEmail() throws UserNotFoundException {
        //Given
        User user = new User(1L, "Test_user", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        User updatedUser = new User(1L, "Test_user", "newTest1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));

        when(userRepository.findById(updatedUser.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        //When
        User userAfterUpdate = userService.updateUser(updatedUser);

        //Then
        assertEquals(updatedUser, userAfterUpdate);
        verify(dataChangeRepository, times(1)).save(any(UserDataChange.class));
    }

    @Test
    public void updateUserBalance() throws UserNotFoundException {
        //Given
        User user = new User(1L, "Test_user", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        User updatedUser = new User(1L, "Test_user", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("200"));

        when(userRepository.findById(updatedUser.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        //When
        User userAfterUpdate = userService.updateUser(updatedUser);

        //Then
        assertEquals(updatedUser, userAfterUpdate);
        verify(balanceChangeRepository, times(1)).save(any(UserBalanceChange.class));
    }

    @Test
    public void updateUserPassword() throws UserNotFoundException {
        //Given
        Long userId = 1L;
        String newPassword = "new_password";
        User user = new User(userId, "Test_user", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        User updatedUser = new User(userId, "Test_user", "Test1@test.com",
                newPassword, Role.USER, LocalDate.now(), new BigDecimal("100"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(encryptor.encryptPassword(newPassword)).thenReturn(newPassword);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        //When
        User userAfterUpdate = userService.updateUserPassword(userId, newPassword);

        //Then
        assertEquals(updatedUser, userAfterUpdate);
        verify(dataChangeRepository, times(1)).save(any(UserDataChange.class));
    }

    @Test
    public void getUserById() throws UserNotFoundException {
        //Given
        Long userId = 1L;
        User user = new User(userId, "Test_user", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));


        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        //When
        User retrievedUser = userService.getUserById(userId);

        //Then
        assertEquals(user, retrievedUser);
    }

    @Test
    public void getAllUsers() {
        //Given
        User user1 = new User(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("100"));
        User user2 = new User(2L, "Test_user2", "Test2@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("200"));
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        //When
        List<User> retrievedUsers = userService.getAllUsers();

        //Then
        assertEquals(2, retrievedUsers.size());
        assertEquals(user1, retrievedUsers.get(0));
        assertEquals(user2, retrievedUsers.get(1));
    }
}