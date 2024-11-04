package com.ureca.sole_paradise.walkRoute.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalkRouteDTO {

    private Integer walkrouteId;
    private String latitude;
    private String longitude;
    private String distanceKm;
    private String walkTime;
    private Integer userId;
}
