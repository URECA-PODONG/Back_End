package com.ureca.sole_paradise.community.db.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityDTO {

    private Integer postId;

    @NotNull
    @Size(max = 255)
    private String title;

    private String imageUrl;//사진

    @NotNull
    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer good;

    @Size(max = 225)
    private String category;

    @NotNull
    private Integer user;

}
