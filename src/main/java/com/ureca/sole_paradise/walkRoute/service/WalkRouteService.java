package com.ureca.sole_paradise.walkRoute.service;

import com.ureca.sole_paradise.walkRoute.db.dto.WalkRouteDTO;
import com.ureca.sole_paradise.walkRoute.db.entity.WalkRouteEntity;
import com.ureca.sole_paradise.walkRoute.db.repository.WalkRouteRepository;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.user.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalkRouteService {

    private final WalkRouteRepository walkRouteRepository;
    private final UserRepository userRepository;

    @Transactional
    public WalkRouteDTO registerWalkRoute(WalkRouteDTO walkRouteDTO) {
        UserEntity userEntity = userRepository.findById(walkRouteDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        WalkRouteEntity walkRouteEntity = WalkRouteEntity.builder()
                .Latitude(walkRouteDTO.getLatitude())
                .longitude(walkRouteDTO.getLongitude())
                .distanceKm(walkRouteDTO.getDistanceKm())
                .walkTime(walkRouteDTO.getWalkTime())
                .userEntity(userEntity)
                .build();

        walkRouteEntity = walkRouteRepository.save(walkRouteEntity);

        return WalkRouteDTO.builder()
                .walkrouteId(walkRouteEntity.getWalkrouteId())
                .latitude(walkRouteEntity.getLatitude())
                .longitude(walkRouteEntity.getLongitude())
                .distanceKm(walkRouteEntity.getDistanceKm())
                .walkTime(walkRouteEntity.getWalkTime())
                .userId(userEntity.getUserId())
                .createdAt(walkRouteEntity.getCreatedAt())
                .updatedAt(walkRouteEntity.getUpdatedAt())
                .build();
    }

    public List<WalkRouteDTO> getAllWalkRoutes() {
        return walkRouteRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void deleteWalkRoute(Integer walkrouteId) {
        walkRouteRepository.deleteById(walkrouteId);
    }

    public List<WalkRouteDTO> getWalkRoutesByUserId(Integer userId) {
        return walkRouteRepository.findByUserEntity_UserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    private WalkRouteDTO toDTO(WalkRouteEntity entity) {
        return WalkRouteDTO.builder()
                .walkrouteId(entity.getWalkrouteId())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .distanceKm(entity.getDistanceKm())
                .walkTime(entity.getWalkTime())
                .userId(entity.getUserEntity().getUserId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
