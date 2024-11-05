package com.ureca.sole_paradise.community.db.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityCommentDTO  {

	
	private Integer communityCommentId;

    @Size(max = 255)
    private String comment;

    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @NotNull
    private Integer post;

    @NotNull
    private Integer user;

}
