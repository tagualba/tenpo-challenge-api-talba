package com.tenpo.challenge.repositories;

import com.tenpo.challenge.models.persistence.AuditHistoryPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditHistoryPersistenceRepository extends JpaRepository<AuditHistoryPersistence, Long> {
}
