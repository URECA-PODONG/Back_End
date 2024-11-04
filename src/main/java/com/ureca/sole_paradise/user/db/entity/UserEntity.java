package com.ureca.sole_paradise.user.db.entity;

import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer userId;

    @Column(nullable = false)
    private String accountEmail;

    @Column(nullable = false)
    private String profileNickname = "기본 닉네임"; // 기본값 설정

    @Column(columnDefinition = "longtext", nullable = true)
    private String userPicture;

    @Column(length = 50, nullable = true)
    private String nickname;

    @Column(nullable = true, length = 500)
    private String address;

    @Column(length = 20, nullable = true)
    private String phoneNumber;

    private boolean health;

    private boolean petCare;

    private boolean missing;
}
