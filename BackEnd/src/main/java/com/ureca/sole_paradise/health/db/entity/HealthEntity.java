package com.ureca.sole_paradise.health.db.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.ureca.sole_paradise.pet.db.entity.PetEntity;
import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HealthEntity extends BaseTimeEntity {
	
	@Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer healthId;

    @Column
    private LocalDateTime visitedDate;

    @Column(nullable = false, columnDefinition = "longtext")
    private String notes;

    @Column
    private LocalDateTime healthDate;

    @Column
    private LocalDateTime nextCheckupDate;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean alarmStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity pet;

}
