package com.ureca.sole_paradise.community.db.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Base64;

import com.ureca.sole_paradise.user.db.entity.UserEntity;

import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "community_comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommunityCommentEntity {

    @Id
    @Column(name = "community_comment_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer communityCommentId;

    @Column
    private String comment;

    @CreatedDate
    @Column(updatable = false)  // 생성일은 업데이트되지 않도록 설정
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
