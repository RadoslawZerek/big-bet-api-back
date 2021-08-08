package com.radoslawzerek.bigbetbackend.repositories;

import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.entity.UserBalanceChange;
import com.radoslawzerek.bigbetbackend.repository.UserBalanceChangeRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBalanceChangeRepositoryTest {

    @Autowired
    UserBalanceChangeRepository changeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testGetAllBalanceChangesOfUser() {

        //Given
        User user1 = new User("Test_user1", "Test1@test.com", "test_password");
        userRepository.save(user1);
        Long user1Id = user1.getId();

        UserBalanceChange change1 = new UserBalanceChange(user1, new BigDecimal("100"), new BigDecimal("80"));
        changeRepository.save(change1);
        Long change1Id = change1.getId();

        UserBalanceChange change2 = new UserBalanceChange(user1, new BigDecimal("80"), new BigDecimal("120"));
        changeRepository.save(change2);
        Long change2Id = change2.getId();

        User user2 = new User("Test_user2", "Test2@test.com", "test_password");
        userRepository.save(user2);
        Long user2Id = user2.getId();

        UserBalanceChange change3 = new UserBalanceChange(user2, new BigDecimal("150"), new BigDecimal("120"));
        changeRepository.save(change3);
        Long change3Id = change3.getId();

        //When
        List<UserBalanceChange> balanceChangesOfUser1 = changeRepository.getBalanceChangesOfUser(user1Id);
        List<UserBalanceChange> balanceChangesOfUser2 = changeRepository.getBalanceChangesOfUser(user2Id);
        List<UserBalanceChange> allBalanceChanges = changeRepository.findAll();

        //Then

        assertEquals(2, balanceChangesOfUser1.size());
        assertEquals(1, balanceChangesOfUser2.size());
        assertTrue(allBalanceChanges.size() >= 3);

        //Clean Up
        try {
            changeRepository.deleteById(change1Id);
            changeRepository.deleteById(change2Id);
            changeRepository.deleteById(change3Id);
            userRepository.deleteById(user1Id);
            userRepository.deleteById(user2Id);

        } catch (Exception e) {
            //do nothing
        }
    }
}