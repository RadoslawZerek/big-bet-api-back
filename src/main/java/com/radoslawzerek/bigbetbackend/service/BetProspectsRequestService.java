package com.radoslawzerek.bigbetbackend.service;

import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import com.radoslawzerek.bigbetbackend.entity.BetProspectsRequest;
import com.radoslawzerek.bigbetbackend.entity.User;
import com.radoslawzerek.bigbetbackend.exception.UserNotFoundException;
import com.radoslawzerek.bigbetbackend.mapper.BetProspectsRequestMapper;
import com.radoslawzerek.bigbetbackend.repository.BetProspectsRequestRepository;
import com.radoslawzerek.bigbetbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BetProspectsRequestService {

    @Autowired
    private final BetProspectsRequestRepository prospectsRequestRepository;

    @Autowired
    private final BetProspectsRequestMapper prospectsRequestMapper;

    @Autowired
    private final UserRepository userRepository;

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

