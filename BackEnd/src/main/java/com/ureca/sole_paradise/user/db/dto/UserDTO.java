package com.ureca.sole_paradise.user.db.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Integer userId;


    @Size(max = 255)
    private String accountEmail;


    @Size(max = 255)
    private String profileNickname;

    private String userPicture;


    @Size(max = 50)
    private String nickname;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    private String address;

    @Size(max = 20) // 전화번호 최대 길이 제한
    private String phoneNumber; // 전화번호 필드 추가

}
