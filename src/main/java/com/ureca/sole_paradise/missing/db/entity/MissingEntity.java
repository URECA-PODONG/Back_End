package com.ureca.sole_paradise.missing.db.entity;

import java.time.OffsetDateTime;

import com.ureca.sole_paradise.pet.db.entity.PetEntity;

import com.ureca.sole_paradise.util.BaseTimeEntity;
import com.ureca.sole_paradise.walkRoute.db.entity.WalkRouteEntity;
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

@Entity(name = "missing")
@Getter
@Setter

public class MissingEntity extends BaseTimeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer missingId;

    @Column(nullable = false, length = 100)
    private String alarmName;

    @Column(nullable = false, length = 100)
    private String location;

    @Column
    private Integer alertRadiusKm;

    @Column(nullable = false)
    private OffsetDateTime missingDate;

    @Column(nullable = false, length = 2000)
    private String missingDetails;

    @Column
    private Integer missingStatus;

    @Column(nullable = false, columnDefinition = "longtext")
    private String alarmPicture;

    @Column(nullable = false, length = 20)
    private String contactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walk_route_id", nullable = false)
    private WalkRouteEntity walkroute;

}