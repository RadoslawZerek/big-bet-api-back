package com.radoslawzerek.bigbetbackend.repositories;

import com.radoslawzerek.bigbetbackend.entity.BetProspectsRequest;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.repository.BetProspectsRequestRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BetProspectsRequestRepositoryTest {

    @Autowired
    BetProspectsRequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testGetBetProspectsRequestsOfUser() {

        //Given
        User user1 = new User("Test_user1", "Test1@test.com", "test_password");
        userRepository.save(user1);
        Long user1Id = user1.getId();

        List<String> leagues = new ArrayList<>();
        leagues.add("test league 1");
        leagues.add("test league 2");

        BetProspectsRequest request1 = new BetProspectsRequest(user1, leagues, LocalDateTime.now());
        requestRepository.save(request1);
        Long request1Id = request1.getId();

        BetProspectsRequest request2 = new BetProspectsRequest(user1, leagues, LocalDateTime.now());
        requestRepository.save(request2);
        Long request2Id = request2.getId();

        User user2 = new User("Test_user2", "Test2@test.com", "test_password");
        userRepository.save(user2);
        Long user2Id = user2.getId();

        BetProspectsRequest request3 = new BetProspectsRequest(user2, leagues, LocalDateTime.now());
        requestRepository.save(request3);
        Long request3Id = request3.getId();

        //When

        List<BetProspectsRequest> requestOfUser1 = requestRepository.getBetProspectsRequestsOfUser(user1Id);
        List<BetProspectsRequest> requestOfUser2 = requestRepository.getBetProspectsRequestsOfUser(user2Id);
        List<BetProspectsRequest> allRequest = requestRepository.findAll();

        //Then
        assertEquals(2, requestOfUser1.size());
        assertEquals(1, requestOfUser2.size());
        assertTrue(allRequest.size() >= 3);

        //Clean Up
        try {
            requestRepository.deleteById(request1Id);
            requestRepository.deleteById(request2Id);
            requestRepository.deleteById(request3Id);
            userRepository.deleteById(user1Id);
            userRepository.deleteById(user2Id);
        } catch (Exception e) {
            //do nothing
        }
    }
}