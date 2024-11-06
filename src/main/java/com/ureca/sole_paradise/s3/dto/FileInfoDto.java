package com.ureca.sole_paradise.s3.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
public class FileInfoDto {

        private MultipartFile file;
        private String deleteURL;
        private List<String> allowedMimeTypes;
        private long id;
        private LocalDate date;

        private S3Option s3Option;

        public static FileInfoDto fromDelete(String deleteURL, S3Option s3Option) {
        return FileInfoDto.builder()
                .deleteURL(deleteURL)
                .s3Option(s3Option)
                .build();
    }
}

