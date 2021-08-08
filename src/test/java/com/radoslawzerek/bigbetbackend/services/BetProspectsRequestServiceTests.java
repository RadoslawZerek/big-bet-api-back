package com.radoslawzerek.bigbetbackend.services;

import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import com.radoslawzerek.bigbetbackend.entity.BetProspectsRequest;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.enums.Role;
import com.radoslawzerek.bigbetbackend.exception.UserNotFoundException;
import com.radoslawzerek.bigbetbackend.mapper.BetProspectsRequestMapper;
import com.radoslawzerek.bigbetbackend.repository.BetProspectsRequestRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import com.radoslawzerek.bigbetbackend.service.BetProspectsRequestService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BetProspectsRequestServiceTests {

    @InjectMocks
    private BetProspectsRequestService requestService;

    @Mock
    private BetProspectsRequestRepository prospectsRequestRepository;

    @Mock
    private BetProspectsRequestMapper prospectsRequestMapper;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testAddBetProspectsRequest() throws UserNotFoundException {

        //Given
        List<String> leagues = new ArrayList<>();
        leagues.add("La Liga - Spain");

        BetProspectsRequestDto requestDto = new BetProspectsRequestDto(1L, 1L, leagues, LocalDateTime.now());
        User user = new User(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("200"));

        BetProspectsRequest request = new BetProspectsRequest(1L, user, leagues, LocalDateTime.now());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(prospectsRequestMapper.mapToBetProspectsRequest(requestDto, user)).thenReturn(request);
        when(prospectsRequestRepository.save(request)).thenReturn(request);

        //When
        BetProspectsRequest retrievedRequest = requestService.addBetProspectsRequest(requestDto);

        //Then

        assertEquals(request, retrievedRequest);
    }

    @Test
    public void testGetAllBetProspectsRequests() {
        //Given
        User user = new User(1L, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("200"));

        List<String> leagues = new ArrayList<>();
        leagues.add("La Liga - Spain");

        BetProspectsRequest request1 = new BetProspectsRequest(1L, user, leagues, LocalDateTime.now());
        BetProspectsRequest request2 = new BetProspectsRequest(2L, user, leagues, LocalDateTime.now().minusHours(2));

        List<BetProspectsRequest> requests = new ArrayList<>();
        requests.add(request1);
        requests.add(request2);

        when(prospectsRequestRepository.findAll()).thenReturn(requests);

        //When
        List<BetProspectsRequest> retrievedRequests = requestService.getAllBetProspectsRequests();

        //Then
        assertEquals(2, retrievedRequests.size());
        assertEquals(request1, retrievedRequests.get(0));
        assertEquals(request2, retrievedRequests.get(1));
    }

    @Test
    public void testGetBetProspectsRequestsOfUser() {
        //Given
        Long userId = 1L;
        User user = new User(userId, "Test_user1", "Test1@test.com",
                "test_password", Role.USER, LocalDate.now(), new BigDecimal("200"));

        List<String> leagues = new ArrayList<>();
        leagues.add("La Liga - Spain");

        BetProspectsRequest request1 = new BetProspectsRequest(1L, user, leagues, LocalDateTime.now());
        BetProspectsRequest request2 = new BetProspectsRequest(2L, user, leagues, LocalDateTime.now().minusHours(2));

        List<BetProspectsRequest> requests = new ArrayList<>();
        requests.add(request1);
        requests.add(request2);

        when(prospectsRequestRepository.getBetProspectsRequestsOfUser(userId)).thenReturn(requests);

        //When
        List<BetProspectsRequest> retrievedRequests = requestService.getBetProspectsRequestsOfUser(userId);

        //Then
        assertEquals(2, retrievedRequests.size());
        assertEquals(request1, retrievedRequests.get(0));
        assertEquals(request2, retrievedRequests.get(1));
    }
}