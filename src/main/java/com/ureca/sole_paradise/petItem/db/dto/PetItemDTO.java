package com.ureca.sole_paradise.petItem.db.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class PetItemDTO {

    private Integer petItemId;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private String description;

    private String imageUrl;//사진

    private Integer status;

    private Integer price;

    private Integer good;

    private Integer sharing;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @Size(max = 255)
    private String nanum;// 나눔 여부??

    @NotNull
    private Integer user;
}
