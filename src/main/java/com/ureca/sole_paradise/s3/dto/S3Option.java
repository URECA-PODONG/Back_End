package com.ureca.sole_paradise.s3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum S3Option {
    profileUpload("프로필 사진 업로드"),
    profileDelete("프로필 사진 삭제"),
    petImgUpload("펫 사진 업로드 "),
    petImgDelete("펫 사진 삭제 "),
    petItemImgUpload("펫 용품 사진 업로드"),
    petItemImgDelete("펫 용품 사진 삭제"),
    CommunityImgUpload("게시글 이미지 등록"),
    CommunityImgDelete("게시글 이미지 삭제");
    private final String name;

    public static S3Option getNextOption(S3Option currentOption) {
        S3Option[] options = S3Option.values();  // 모든 enum 값을 가져옴
        int currentIndex = currentOption.ordinal();  // 현재 enum 값의 인덱스 가져옴

        // 다음 인덱스 계산, 마지막 값일 경우 첫 번째 값으로 순환
        int nextIndex = (currentIndex + 1) % options.length;

        return options[nextIndex];
    }
}
