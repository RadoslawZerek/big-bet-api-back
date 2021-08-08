package com.radoslawzerek.bigbetbackend.repositories;

import com.radoslawzerek.bigbetbackend.entity.LogInAttempt;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.repository.LogInAttemptRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LogInAttemptRepositoryTest {

    @Autowired
    LogInAttemptRepository logInAttemptRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testGetFailedLogInAttemptsOfUser() {

        //Given
        User user1 = new User("Test_user1", "Test1@test.com", "test_password");
        userRepository.save(user1);
        Long user1Id = user1.getId();

        LogInAttempt logInAttempt1 = new LogInAttempt(user1, user1.getUsername(), false);
        logInAttemptRepository.save(logInAttempt1);
        Long logInAttempt1Id = logInAttempt1.getId();

        LogInAttempt logInAttempt2 = new LogInAttempt(user1, user1.getUsername(), true);
        logInAttemptRepository.save(logInAttempt2);
        Long logInAttempt2Id = logInAttempt2.getId();


        User user2 = new User("Test_user2", "Test2@test.com", "test_password");
        userRepository.save(user2);
        Long user2Id = user2.getId();

        LogInAttempt logInAttempt3 = new LogInAttempt(user2, user2.getUsername(), true);
        logInAttemptRepository.save(logInAttempt3);
        Long logInAttempt3Id = logInAttempt3.getId();

        //When

        List<LogInAttempt> allLogInAttemptsOfUser1 = logInAttemptRepository.getAllLogInAttemptsOfUser(user1Id);
        List<LogInAttempt> failedLogInAttemptsOfUser1 = logInAttemptRepository.getFailedLogInAttemptsOfUser(user1Id);
        List<LogInAttempt> allLogInAttempts = logInAttemptRepository.findAll();

        //Then

        assertEquals(2, allLogInAttemptsOfUser1.size());
        assertEquals(1, failedLogInAttemptsOfUser1.size());
        assertTrue(allLogInAttempts.size() >= 3);

        //CleanUp
        try {
            logInAttemptRepository.deleteById(logInAttempt1Id);
            logInAttemptRepository.deleteById(logInAttempt2Id);
            logInAttemptRepository.deleteById(logInAttempt3Id);
            userRepository.deleteById(user1Id);
            userRepository.deleteById(user2Id);

        } catch (Exception e) {
            //do nothing
        }
    }
}