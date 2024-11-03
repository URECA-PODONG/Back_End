package com.ureca.sole_paradise.community.db.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityCommentDTO {

	
	private Integer commentId;

    @Size(max = 255)
    private String comment;

    private OffsetDateTime updatedAt;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    private Integer post;

    @NotNull
    private Integer user;

}
