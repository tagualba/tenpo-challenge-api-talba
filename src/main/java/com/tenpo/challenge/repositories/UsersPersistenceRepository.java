package com.tenpo.challenge.repositories;

import com.tenpo.challenge.models.persistence.UserPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersPersistenceRepository extends JpaRepository<UserPersistence, Long> {

    Optional<UserPersistence> findByEmail(String email);

    Optional<UserPersistence> findByLastTokenApi(String lastTokenApi);

}
