package com.tenpo.challenge.models.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "audit_history")
public class AuditHistoryPersistence {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "operation", nullable = false)
    private String operation;

    @Column(name = "request", columnDefinition="TEXT",  nullable = true)
    private String request;

    @Column(name = "response", columnDefinition="TEXT", nullable = true)
    private String response;

    public AuditHistoryPersistence(){
    }
}
