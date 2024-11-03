package com.ureca.sole_paradise.petItem.db.entity;


import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "pet_item_comment")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetItemCommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_item_comment_id", nullable = false, updatable = false)
    private Integer petItemCommentId;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_item_id")
    private PetItemEntity petItem;
}
