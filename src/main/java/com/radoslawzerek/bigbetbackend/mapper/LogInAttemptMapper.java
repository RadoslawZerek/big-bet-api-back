package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.LogInAttemptDto;
import com.radoslawzerek.bigbetbackend.entity.LogInAttempt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LogInAttemptMapper {

    public LogInAttemptDto mapToLogInAttemptDto(LogInAttempt logInAttempt) {
        return new LogInAttemptDto(logInAttempt.getId(), logInAttempt.getUser().getId(),
                logInAttempt.getLogin(), logInAttempt.getSuccessful(), logInAttempt.getAttemptTime());
    }

    public List<LogInAttemptDto> mapToLogInAttemptDtoList(List<LogInAttempt> logInAttemptList) {
        return logInAttemptList.stream().map(this::mapToLogInAttemptDto)
                .collect(Collectors.toList());
    }
}
