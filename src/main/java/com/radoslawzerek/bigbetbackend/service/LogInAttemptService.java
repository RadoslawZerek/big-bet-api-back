package com.radoslawzerek.bigbetbackend.service;

import com.radoslawzerek.bigbetbackend.entity.LogInAttempt;
import com.radoslawzerek.bigbetbackend.repository.LogInAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogInAttemptService {

    @Autowired
    private final LogInAttemptRepository repository;

    public LogInAttempt addLogInAttempt(LogInAttempt logInAttempt) {
        return repository.save(logInAttempt);
    }

    public List<LogInAttempt> getAllLogInAttempts() {
        return repository.findAll();
    }

    public List<LogInAttempt> getAllLogInAttemptsOfUser(Long userId) {
        return repository.getAllLogInAttemptsOfUser(userId);
    }

    public List<LogInAttempt> getFailedLogInAttemptsOfUser(Long userId) {
        return repository.getFailedLogInAttemptsOfUser(userId);
    }
}

