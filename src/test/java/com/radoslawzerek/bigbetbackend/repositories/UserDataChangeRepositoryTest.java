package com.radoslawzerek.bigbetbackend.repositories;

import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.entity.UserDataChange;
import com.radoslawzerek.bigbetbackend.enums.ChangedValue;
import com.radoslawzerek.bigbetbackend.repository.UserDataChangeRepository;
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
public class UserDataChangeRepositoryTest {

    @Autowired
    UserDataChangeRepository changeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testGetAllBalanceChangesOfUser() {

        //Given
        User user1 = new User("Test_user1", "Test1@test.com", "test_password");
        userRepository.save(user1);
        Long user1Id = user1.getId();

        UserDataChange change1 = new UserDataChange(user1, ChangedValue.USERNAME,
                user1.getUsername(), "New name");
        changeRepository.save(change1);
        Long change1Id = change1.getId();

        UserDataChange change2 = new UserDataChange(user1, ChangedValue.EMAIL,
                user1.getEmail(), "New email");
        changeRepository.save(change2);
        Long change2Id = change2.getId();

        User user2 = new User("Test_user2", "Test2@test.com", "test_password");
        userRepository.save(user2);
        Long user2Id = user2.getId();

        UserDataChange change3 = new UserDataChange(user2, ChangedValue.PASSWORD,
                user1.getPassword(), "New_password");
        changeRepository.save(change3);
        Long change3Id = change3.getId();

        //When
        List<UserDataChange> dataChangesOfUser1 = changeRepository.getDataChangesOfUser(user1Id);
        List<UserDataChange> dataChangesOfUser2 = changeRepository.getDataChangesOfUser(user2Id);
        List<UserDataChange> allBalanceChanges = changeRepository.findAll();

        //Then

        assertEquals(2, dataChangesOfUser1.size());
        assertEquals(1, dataChangesOfUser2.size());
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