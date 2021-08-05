package com.radoslawzerek.bigbetbackend.repository;

import com.radoslawzerek.bigbetbackend.entity.Bet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BetRepository extends CrudRepository<Bet, Long> {

    @Override
    List<Bet> findAll();

    @Override
    Bet save(Bet bet);

    @Override
    Optional<Bet> findById(Long id);

    @Query(nativeQuery = true)
    List<Bet> getAllBetsOfUser(@Param("USER_ID") Long userId);

    @Query(nativeQuery = true)
    List<Bet> getPendingBetsOfUser(@Param("USER_ID") Long userId);
}
