package com.ureca.sole_paradise.health.db.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.ureca.sole_paradise.pet.db.entity.PetEntity;
import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "health")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class HealthEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer healthId;

    @Column
    private LocalDate visitedDate;

    @Column(columnDefinition = "longtext")
    private String notes;

    @Column
    private LocalDate healthDate;

    @Column
    private LocalDate nextCheckupDate;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean alarmStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity pet;

}
