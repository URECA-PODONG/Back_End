package com.ureca.sole_paradise.health.db.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthDTO {

    private Integer healthId;

    private OffsetDateTime visitedDate;

    @NotNull
    private String notes;

    private OffsetDateTime healthDate;

    private OffsetDateTime nextCheckupDate;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Boolean alarmStatus;

    @NotNull
    private Integer pet;

}
