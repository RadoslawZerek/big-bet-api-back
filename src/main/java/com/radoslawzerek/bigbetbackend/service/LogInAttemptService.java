package com.radoslawzerek.bigbetbackend.service;

import com.radoslawzerek.bigbetbackend.entity.LogInAttempt;
import com.radoslawzerek.bigbetbackend.repository.LogInAttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogInAttemptService {

    private final LogInAttemptRepository repository;


    public LogInAttemptService(LogInAttemptRepository repository) {
        this.repository = repository;
    }

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

