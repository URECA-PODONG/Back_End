package com.ureca.sole_paradise.community.db.entity;

import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity(name = "community")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommunityEntity extends BaseTimeEntity {

    @Id
    @Column(name = "post_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "longtext")
    private String contents;

    @Column(columnDefinition = "longtext")
    private String imageUrl;


    @CreatedDate
    @Column(updatable = false)  // 생성일은 업데이트되지 않도록 설정
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    @Column
    private Integer good;

    @Column(length = 225)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "post")
    private Set<CommunityCommentEntity> postCommunityComments;
}
