package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.dto.LogInAttemptDto;
import com.radoslawzerek.bigbetbackend.mapper.LogInAttemptMapper;
import com.radoslawzerek.bigbetbackend.service.LogInAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class LogInAttemptController {

    @Autowired
    private final LogInAttemptService service;

    @Autowired
    private final LogInAttemptMapper mapper;

    @GetMapping("/login_attampts")
    public List<LogInAttemptDto> getLoginAttempts() {
        return mapper.mapToLogInAttemptDtoList(service.getAllLogInAttempts());
    }

    @GetMapping("/login_attampts/{userId}/{onlySuccessful}")
    public List<LogInAttemptDto> getLoginAttemptsOfUser(@PathVariable Long userId, @PathVariable Boolean onlySuccessful) {
        if (onlySuccessful) {
            return mapper.mapToLogInAttemptDtoList(service.getFailedLogInAttemptsOfUser(userId));
        }
        return mapper.mapToLogInAttemptDtoList(service.getAllLogInAttemptsOfUser(userId));
    }

}
