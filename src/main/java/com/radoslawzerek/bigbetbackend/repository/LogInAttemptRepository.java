package com.radoslawzerek.bigbetbackend.repository;

import com.radoslawzerek.bigbetbackend.entity.LogInAttempt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface LogInAttemptRepository extends CrudRepository<LogInAttempt, Long> {

    @Override
    List<LogInAttempt> findAll();

    @Override
    LogInAttempt save(LogInAttempt logInAttempt);

    @Override
    Optional<LogInAttempt> findById(Long id);

    @Query(nativeQuery = true)
    List<LogInAttempt> getAllLogInAttemptsOfUser(@Param("USER_ID") Long userId);

    @Query(nativeQuery = true)
    List<LogInAttempt> getFailedLogInAttemptsOfUser(@Param("USER_ID") Long userId);
}
