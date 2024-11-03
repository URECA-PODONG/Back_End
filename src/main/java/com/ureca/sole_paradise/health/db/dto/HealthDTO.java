package com.ureca.sole_paradise.health.db.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthDTO {

    private Integer healthId;

    private LocalDateTime visitedDate;

    @NotNull
    private String notes;

    private LocalDateTime healthDate;

    private LocalDateTime nextCheckupDate;

    private Boolean alarmStatus;

    @NotNull
    private Integer pet;

}
