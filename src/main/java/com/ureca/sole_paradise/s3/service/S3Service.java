package com.ureca.sole_paradise.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.ureca.sole_paradise.s3.dto.FileInfoDto;
import com.ureca.sole_paradise.s3.dto.S3Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName; //버킷 이름

    public String fileUpload(FileInfoDto fileInfoDto) {
        MultipartFile file = fileInfoDto.getFile();
        ObjectMetadata metadata = createMetadata(fileInfoDto);
        String changedName = createS3Path(fileInfoDto);
        try {
            PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(
                    bucketName, changedName, file.getInputStream(), metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            log.error("file upload error " + e.getMessage());
            throw new RuntimeException("file upload error " + e.getMessage());
        }

        return amazonS3.getUrl(bucketName, changedName).toString();
    }

    public void deleteFile(String fileName, S3Option s3Option) {
        String deleteName = createS3Path(FileInfoDto.fromDelete(fileName, s3Option));
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, deleteName));
    }

    public void deleteGallery(String url) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, url));
    }


    public String changeFile(String originName, FileInfoDto fileInfoDto) {
        deleteFile(originName, S3Option.getNextOption(fileInfoDto.getS3Option()));
        return fileUpload(fileInfoDto);
    }

    private String createS3Path(FileInfoDto fileInfoDto) {
        String originName = fileInfoDto.getFile() == null ? fileInfoDto.getDeleteURL() : fileInfoDto.getFile().getOriginalFilename();
        String ext = originName.substring(originName.lastIndexOf(".")); //확장자
        switch (fileInfoDto.getS3Option()) {
            case profileUpload -> {     //프로필 사진 업로드 경로
                return "ProfileImage/" + fileInfoDto.getId() + "/" + UUID.randomUUID().toString()
                        + ext;    //프로필 이미지 저장경로 생성
            }
            case profileDelete -> {     //프로필 사진 삭제 경로
                String splitStr = ".com/";
                return originName.substring(originName.lastIndexOf(splitStr) + splitStr.length());    //프로필 이미지 삭제경로 생성
            }

            case petImgUpload -> {
                return "pet/" + fileInfoDto.getId() + "/" + UUID.randomUUID().toString();
            }
            case petImgDelete -> {
                String splitStr = ".com/";
                return originName.substring(originName.lastIndexOf(splitStr) + splitStr.length());
            }
            case petItemImgUpload -> {     //프로필 사진 업로드 경로
                return "petItem/" + fileInfoDto.getId() + "/" + UUID.randomUUID().toString()
                        + ext;    //프로필 이미지 저장경로 생성
            }
            case  petItemImgDelete -> {     //프로필 사진 삭제 경로
                String splitStr = ".com/";
                return originName.substring(originName.lastIndexOf(splitStr) + splitStr.length());    //프로필 이미지 삭제경로 생성
            }
            case CommunityImgUpload -> {     //프로필 사진 업로드 경로
                return "community/" + fileInfoDto.getId() + "/" + UUID.randomUUID().toString()
                        + ext;    //프로필 이미지 저장경로 생성
            }
            case CommunityImgDelete -> {     //프로필 사진 삭제 경로
                String splitStr = ".com/";
                return originName.substring(originName.lastIndexOf(splitStr) + splitStr.length());    //프로필 이미지 삭제경로 생성
            }


            default -> {
                return null;
            }
        }
    }

    private ObjectMetadata createMetadata(FileInfoDto fileInfoDto) {
        MultipartFile file = fileInfoDto.getFile();

        // 허용되지 않는 MIME 타입의 파일은 처리하지 않음
        String fileContentType = file.getContentType();
        if (!fileInfoDto.getAllowedMimeTypes().contains(fileContentType)) {
            throw new RuntimeException("형식 불일치");
        }

        ObjectMetadata metadata = new ObjectMetadata(); //메타데이터

        metadata.setContentLength(file.getSize()); // 파일 크기 명시
        metadata.setContentType(fileContentType);   // 파일 확장자 명시

        return metadata;
    }
}