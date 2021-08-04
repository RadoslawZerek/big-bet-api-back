package com.radoslawzerek.bigbetbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import java.time.LocalDateTime;

@NamedNativeQuery(
        name = "LogInAttempt.getAllLogInAttemptsOfUser",
        query = "SELECT * FROM LOG_IN_ATTEMPTS " +
                "WHERE USER_ID = :USER_ID " +
                "ORDER BY ATTEMPT_TIME DESC",
        resultClass = LogInAttempt.class
)

@NamedNativeQuery(
        name = "LogInAttempt.getFailedLogInAttemptsOfUser",
        query = "SELECT * FROM LOG_IN_ATTEMPTS " +
                "WHERE SUCCESSFUL = FALSE AND USER_ID = :USER_ID " +
                "ORDER BY ATTEMPT_TIME DESC",
        resultClass = LogInAttempt.class
)

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "LOG_IN_ATTEMPTS")
public class LogInAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "SUCCESSFUL")
    private Boolean successful;

    @Column(name = "ATTEMPT_TIME")
    private LocalDateTime attemptTime;

    public LogInAttempt(User user, String login, Boolean successful) {
        this.user = user;
        this.login = login;
        this.successful = successful;
        this.attemptTime = LocalDateTime.now();
    }
}
