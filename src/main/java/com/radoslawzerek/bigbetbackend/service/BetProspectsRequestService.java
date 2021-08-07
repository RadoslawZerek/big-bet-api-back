package com.radoslawzerek.bigbetbackend.service;

import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import com.radoslawzerek.bigbetbackend.entity.BetProspectsRequest;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.exception.UserNotFoundException;
import com.radoslawzerek.bigbetbackend.mapper.BetProspectsRequestMapper;
import com.radoslawzerek.bigbetbackend.repository.BetProspectsRequestRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetProspectsRequestService {

    private final BetProspectsRequestRepository prospectsRequestRepository;
    private final BetProspectsRequestMapper prospectsRequestMapper;
    private final UserRepository userRepository;


    public BetProspectsRequestService(BetProspectsRequestRepository prospectsRequestRepository,
                                      BetProspectsRequestMapper prospectsRequestMapper, UserRepository userRepository) {
        this.prospectsRequestRepository = prospectsRequestRepository;
        this.prospectsRequestMapper = prospectsRequestMapper;
        this.userRepository = userRepository;
    }

    public BetProspectsRequest addBetProspectsRequest(BetProspectsRequestDto prospectsRequestDto) throws UserNotFoundException {
        User user = userRepository.findById(prospectsRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        BetProspectsRequest request = prospectsRequestMapper.mapToBetProspectsRequest(prospectsRequestDto, user);
        return prospectsRequestRepository.save(request);
    }

    public List<BetProspectsRequest> getAllBetProspectsRequests() {
        return prospectsRequestRepository.findAll();
    }

    public List<BetProspectsRequest> getBetProspectsRequestsOfUser(Long userId) {
        return prospectsRequestRepository.getBetProspectsRequestsOfUser(userId);
    }
}

