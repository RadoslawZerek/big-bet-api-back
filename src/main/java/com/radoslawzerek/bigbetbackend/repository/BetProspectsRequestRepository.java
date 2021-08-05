package com.radoslawzerek.bigbetbackend.repository;

import com.radoslawzerek.bigbetbackend.entity.BetProspectsRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BetProspectsRequestRepository extends CrudRepository<BetProspectsRequest, Long> {

    @Override
    List<BetProspectsRequest> findAll();

    @Override
    BetProspectsRequest save(BetProspectsRequest prospectsRequest);

    @Override
    Optional<BetProspectsRequest> findById(Long id);

    @Query(nativeQuery = true)
    List<BetProspectsRequest> getBetProspectsRequestsOfUser(@Param("USER_ID") Long userId);
}
