package com.ureca.sole_paradise.petItem.db.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetItemCommentDTO {

	
	 private Integer commentId;//글 id

	    @NotNull
	    private String comment; //내용

	    @NotNull
	    private OffsetDateTime createdAt;//등록시간

	    private OffsetDateTime updatedAt;

	    @NotNull
	    private Integer petItem;//등록 글 번호

	    @NotNull
	    private Integer user;//사용자 번호
}
