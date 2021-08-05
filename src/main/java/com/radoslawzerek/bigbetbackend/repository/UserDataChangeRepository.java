package com.radoslawzerek.bigbetbackend.repository;

import com.radoslawzerek.bigbetbackend.entity.UserDataChange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserDataChangeRepository extends CrudRepository<UserDataChange, Long> {

    @Override
    List<UserDataChange> findAll();

    @Override
    UserDataChange save(UserDataChange userDataChange);

    @Override
    Optional<UserDataChange> findById(Long id);

    @Query(nativeQuery = true)
    List<UserDataChange> getDataChangesOfUser(@Param("USER_ID") Long userId);
}
