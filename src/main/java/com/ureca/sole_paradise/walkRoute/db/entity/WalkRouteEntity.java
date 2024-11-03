package com.ureca.sole_paradise.walkRoute.db.entity;

import com.ureca.sole_paradise.user.db.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalkRouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walk_route_id", nullable = false, updatable = false)
    private Integer walkrouteId;

    @Column(nullable = false)
    private String walkrouteName;

    private String  location;

    private int distanceKm;

    private int popularity;

    private boolean walkStart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
}
