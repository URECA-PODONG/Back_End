package com.ureca.sole_paradise.community.db.entity;

import java.time.OffsetDateTime;
import java.util.Base64;

import com.ureca.sole_paradise.user.db.entity.UserEntity;

import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "community_comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCommentEntity extends BaseTimeEntity {

    @Id
    @Column(name = "community_comment_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer communityCommentId;

    @Column
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
