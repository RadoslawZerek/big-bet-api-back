package com.radoslawzerek.bigbetbackend.repository;

import com.radoslawzerek.bigbetbackend.entity.UserBalanceChange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserBalanceChangeRepository extends CrudRepository<UserBalanceChange, Long> {

    @Override
    List<UserBalanceChange> findAll();

    @Override
    UserBalanceChange save(UserBalanceChange userBalanceChange);

    @Override
    Optional<UserBalanceChange> findById(Long id);

    @Query(nativeQuery = true)
    List<UserBalanceChange> getBalanceChangesOfUser(@Param("USER_ID") Long userId);
}
