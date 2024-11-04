package com.ureca.sole_paradise.missing.db.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissingDTO {

    private Integer missingId;

    @NotNull
    @Size(max = 100)
    private String alarmName;

    @NotNull
    @Size(max = 100)
    private String location;

    private Integer alertRadiusKm;

    @NotNull
    private OffsetDateTime missingDate;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotNull
    @Size(max = 2000)
    private String missingDetails;

    private Integer missingStatus;

    @NotNull
    private String alarmPicture;

    @NotNull
    private String contactNumber;

    @NotNull
    private Integer pet;

    @NotNull
    private Integer walkroute;

}
