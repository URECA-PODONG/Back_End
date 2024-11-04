package com.ureca.sole_paradise.community.db.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityCommentDTO {

	
	private Integer communityCommentId;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Integer post;

    @NotNull
    private Integer user;

}
