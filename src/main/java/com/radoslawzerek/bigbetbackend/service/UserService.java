package com.radoslawzerek.bigbetbackend.service;

import com.radoslawzerek.bigbetbackend.dto.UserDto;
import com.radoslawzerek.bigbetbackend.entity.LogInAttempt;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.entity.UserBalanceChange;
import com.radoslawzerek.bigbetbackend.entity.UserDataChange;
import com.radoslawzerek.bigbetbackend.enums.ChangedValue;
import com.radoslawzerek.bigbetbackend.enums.Role;
import com.radoslawzerek.bigbetbackend.exception.UserNotFoundException;
import com.radoslawzerek.bigbetbackend.mapper.UserMapper;
import com.radoslawzerek.bigbetbackend.pojo.LogInFeedback;
import com.radoslawzerek.bigbetbackend.pojo.SignUpFeedback;
import com.radoslawzerek.bigbetbackend.repository.LogInAttemptRepository;
import com.radoslawzerek.bigbetbackend.repository.UserBalanceChangeRepository;
import com.radoslawzerek.bigbetbackend.repository.UserDataChangeRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserDataChangeRepository dataChangeRepository;

    @Autowired
    private final UserBalanceChangeRepository balanceChangeRepository;

    @Autowired
    private final LogInAttemptRepository logInAttemptRepository;

    @Autowired
    private final UserMapper mapper;

    @Autowired
    private final StrongPasswordEncryptor encryptor;

    @Autowired
    private final BetsReviewer betsReviewer;

    public LogInFeedback logUserIn(String login, String password) {

        if (userRepository.findByUsername(login).isPresent()) {
            User user = userRepository.findByUsername(login).get();
            if (encryptor.checkPassword(password, user.getPassword())) {
                betsReviewer.updateBetsStatus(user.getId());
                user = userRepository.findByUsername(login).get();
                logInAttemptRepository.save(new LogInAttempt(user, login, true));
                return new LogInFeedback(mapper.mapToUserDto(user), "User is logged in.");
            } else {
                logInAttemptRepository.save(new LogInAttempt(user, login, false));
                return new LogInFeedback(null, "Wrong user password!");
            }
        } else if (userRepository.findByEmail(login).isPresent()) {
            User user = userRepository.findByEmail(login).get();
            if (encryptor.checkPassword(password, user.getPassword())) {
                betsReviewer.updateBetsStatus(user.getId());
                user = userRepository.findByEmail(login).get();
                logInAttemptRepository.save(new LogInAttempt(user, login, true));
                return new LogInFeedback(mapper.mapToUserDto(user), "User is logged in.");
            } else {
                logInAttemptRepository.save(new LogInAttempt(user, login, false));
                return new LogInFeedback(null, "Wrong user password!");
            }
        } else {
            return new LogInFeedback(null, "User with pointed login does not exist!");
        }
    }

    public SignUpFeedback signUserUp(User user) {
        String username = user.getUsername();
        String email = user.getEmail();
        if (userRepository.findByUsername(username).isPresent()) {
            return new SignUpFeedback(null, "User with pointed username already exists!");
        } else if (userRepository.findByEmail(email).isPresent()) {
            return new SignUpFeedback(null, "User with pointed email already exists!");
        }
        user.setPassword(encryptor.encryptPassword(user.getPassword()));
        if (user.getUsername().equals("Admin")) {
            user.setRole(Role.ADMIN);
        }
        UserDto createdUser = mapper.mapToUserDto(userRepository.save(user));
        return new SignUpFeedback(createdUser, "Sign Up successful");
    }

    public User updateUser(User updatedUser) throws UserNotFoundException {
        User oldUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        if (!updatedUser.getUsername().equals(oldUser.getUsername())) {
            String oldUsername = oldUser.getUsername();
            dataChangeRepository.save(
                    new UserDataChange(updatedUser, ChangedValue.USERNAME, oldUsername, updatedUser.getUsername()));
        } else if (!updatedUser.getEmail().equals(oldUser.getEmail())) {
            String oldEmail = oldUser.getEmail();
            dataChangeRepository.save(
                    new UserDataChange(updatedUser, ChangedValue.EMAIL, oldEmail, updatedUser.getEmail()));
        } else if (!updatedUser.getBalance().equals(oldUser.getBalance())) {
            BigDecimal oldBalance = oldUser.getBalance();
            balanceChangeRepository.save(new UserBalanceChange(updatedUser, oldBalance, updatedUser.getBalance()));
        }
        return userRepository.save(updatedUser);
    }

    public User updateUserPassword(Long userId, String newPassword) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        String oldPassword = user.getPassword();
        user.setPassword(encryptor.encryptPassword(newPassword));
        dataChangeRepository.save(new UserDataChange(user, ChangedValue.PASSWORD, oldPassword, user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Boolean checkIfUserExists(String login) {
        if (userRepository.findByUsername(login).isPresent()) {
            return true;
        } else return userRepository.findByEmail(login).isPresent();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
