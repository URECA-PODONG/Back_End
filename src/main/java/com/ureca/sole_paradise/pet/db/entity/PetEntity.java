package com.ureca.sole_paradise.pet.db.entity;
import java.time.OffsetDateTime;
import java.util.Set;

import com.ureca.sole_paradise.health.db.entity.HealthEntity;
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
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity(name = "pet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity extends BaseTimeEntity {

  @Id
  @Column(name = "pet_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer petId;

  @Column(nullable = false, length = 100)
  private String petName;

  @Column(nullable = false)
  private Integer petAge;

  @Column(nullable = false, length = 100)
  private String dogOrCat;

  @Column(nullable = false, length = 100)
  private String petType;

  @Column(columnDefinition = "longtext")
  private String petPicture;

  @Column(nullable = false)
  private Integer petWeight;

  @Column(nullable = false, columnDefinition = "tinyint", length = 1)
  private Boolean neutering;

  @Column(nullable = false, columnDefinition = "tinyint", length = 1)
  private Boolean petAllergy;

  @Column(nullable = false, columnDefinition = "tinyint", length = 1)
  private Boolean gender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

/*@OneToMany(mappedBy = "pet")
private Set<MissingEntity> petMissings;*/

  @OneToMany(mappedBy = "pet")
  private Set<HealthEntity> petHealths;
}