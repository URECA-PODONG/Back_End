package com.ureca.sole_paradise.petItem.db.entity;

import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity(name = "pet_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetItemEntity extends BaseTimeEntity {

    @Id
    @Column(name = "pet_item_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer petItemId;

    @Column(nullable = false)
    private String name;

    @Column(
            nullable = false,
            name = "\"description\"",
            columnDefinition = "longtext"
    )
    private String description;

    @Column(columnDefinition = "longtext")
    private String imageUrl;

    @Column
    private Integer status;

    @Column
    private Integer price;

    @Column
    private Integer good;

    @Column
    private Integer sharing;

    @Column
    private String nanum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "petItem")
    private Set<PetItemCommentEntity> petItemPetItemComments;

}
