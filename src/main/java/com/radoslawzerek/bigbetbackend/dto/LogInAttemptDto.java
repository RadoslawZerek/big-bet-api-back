package com.radoslawzerek.bigbetbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LogInAttemptDto {

    private Long id;
    private Long userId;
    private String login;
    private Boolean successful;
    private LocalDateTime attemptTime;
}
