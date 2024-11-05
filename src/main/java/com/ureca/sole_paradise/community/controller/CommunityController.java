package com.ureca.sole_paradise.community.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ureca.sole_paradise.community.db.dto.CommunityDTO;
import com.ureca.sole_paradise.community.service.CommunityService;
import com.ureca.sole_paradise.util.ReferencedException;
import com.ureca.sole_paradise.util.ReferencedWarning;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/communities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommunityController {


    private final CommunityService communityService;

    public CommunityController(final CommunityService communityService) {
        this.communityService = communityService;
    }
    //목록
    @GetMapping
    public ResponseEntity<List<CommunityDTO>> getAllCommunities() {
        return ResponseEntity.ok(communityService.findAll());
    }
    // 상세
    @GetMapping("/{postId}")
    public ResponseEntity<CommunityDTO> getCommunity(
            @PathVariable(name = "postId") final Integer postId) {
        return ResponseEntity.ok(communityService.get(postId));
    }
    //등록
//	    @PostMapping
//	    @ApiResponse(responseCode = "201")
//	    public ResponseEntity<Integer> createCommunity(
//	            @RequestBody @Valid final CommunityDTO communityDTO) {
//	        final Integer createdPostId = communityService.create(communityDTO);
//	        return new ResponseEntity<>(createdPostId, HttpStatus.CREATED);
//	    }
    //수정
    @PutMapping("/{postId}")
    public ResponseEntity<Integer> updateCommunity(
            @PathVariable(name = "postId") final Integer postId,
            @RequestBody @Valid final CommunityDTO communityDTO) {
        communityService.update(postId, communityDTO);
        return ResponseEntity.ok(postId);
    }
    //삭제
    @DeleteMapping("/{postId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCommunity(
            @PathVariable(name = "postId") final Integer postId) {
        final ReferencedWarning referencedWarning = communityService.getReferencedWarning(postId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        communityService.delete(postId);
        return ResponseEntity.noContent().build();
    }




    // 사진 업로드 등록

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";;
    @PostMapping
    public ResponseEntity<?> uploadPet(@RequestParam("title") String title,
                                       @RequestParam("contents") String contents,
                                       @RequestParam("user") Integer user,
//                                       @RequestParam("createdAt") OffsetDateTime createdAt,
                                       @RequestParam(value = "imageUrl", required = false) MultipartFile file){
        try {
            CommunityDTO communityDTO = new CommunityDTO();
            communityDTO.setTitle(title);
            communityDTO.setContents(contents);
//            communityDTO.setCreatedAt(createdAt);
            communityDTO.setUser(user);  // user ID 설정
            communityDTO.setCreatedAt(LocalDateTime.now());  // 현재 시간으로 생성일 설정

            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis()+"";// + "_" + file.getOriginalFilename();
                String[] exts = file.getOriginalFilename().split("\\.");
                String ext = exts[exts.length-1];//확장자
                Path filePath = Paths.get(UPLOAD_DIR + fileName+"."+ext);
                Files.createDirectories(filePath.getParent());
                Files.copy(file.getInputStream(), filePath);
                String fp = filePath.toString();
                System.out.println("fp="+fp);
                int staticIndex = fp.lastIndexOf("uploads");
                String ss = fp.substring(staticIndex+8);
                communityDTO.setImageUrl(ss); // 파일 이름만 저장
                System.out.println("ss="+ss);
            }

            final Integer createdpostId = communityService.create(communityDTO);
            return new ResponseEntity<>(createdpostId, HttpStatus.CREATED);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not upload the petItem: " + e.getMessage());
        }
    }



}
