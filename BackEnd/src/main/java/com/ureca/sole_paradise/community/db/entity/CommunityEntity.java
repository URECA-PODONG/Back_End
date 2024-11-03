package com.ureca.sole_paradise.community.db.entity;

import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity(name = "community")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
