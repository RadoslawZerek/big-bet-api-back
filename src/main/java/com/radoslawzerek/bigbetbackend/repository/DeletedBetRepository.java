package com.radoslawzerek.bigbetbackend.repository;

import com.radoslawzerek.bigbetbackend.entity.DeletedBet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface DeletedBetRepository extends CrudRepository<DeletedBet, Long> {

    @Override
    List<DeletedBet> findAll();

    @Override
    DeletedBet save(DeletedBet deletedBet);

    @Override
    Optional<DeletedBet> findById(Long id);

    @Query(nativeQuery = true)
    List<DeletedBet> getDeletedBetsOfUser(@Param("USER_ID") Long userId);

}
