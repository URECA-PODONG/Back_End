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
    private String profileNickname;

    @Column(columnDefinition = "longtext", nullable = true)
    private String userPicture;

    @Column(length = 50, nullable = true)  // 별명은 사용자가 나중에 설정할 수 있도록 null 허용
    private String nickname;

    @Column(nullable = true, length = 500)  // VARCHAR(500) 설정
    private String address;  // Boolean -> String으로 변경

    @Column(length = 500, nullable = true)  // Refresh Token 필드 추가
    private String refreshToken;  // Refresh Token 저장

    @Column(length = 20, nullable = true) // 전화번호 추가
    private String phoneNumber;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Cart> userCarts;
}
