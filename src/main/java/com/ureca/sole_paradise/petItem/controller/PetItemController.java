package com.ureca.sole_paradise.petItem.controller;

import com.ureca.sole_paradise.petItem.db.dto.PetItemDTO;
import com.ureca.sole_paradise.petItem.service.PetItemService;
import com.ureca.sole_paradise.util.ReferencedException;
import com.ureca.sole_paradise.util.ReferencedWarning;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/petItems", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetItemController {


    @Autowired
    private final PetItemService petItemService;

    public PetItemController(final PetItemService petItemService) {
        this.petItemService = petItemService;
    }

    @GetMapping
    public ResponseEntity<List<PetItemDTO>> getAllPetItems() {
        return ResponseEntity.ok(petItemService.findAll());
    }

    @GetMapping("/{petItemId}")
    public ResponseEntity<PetItemDTO> getPetItem(
            @PathVariable(name = "petItemId") final Integer petItemId) {
        return ResponseEntity.ok(petItemService.get(petItemId));
    }

    //수정
    @PutMapping("/{petItemId}")
    public ResponseEntity<Integer> updatePetItem(
            @PathVariable(name = "petItemId") final Integer petItemId,
            @RequestBody @Valid final PetItemDTO petItemDTO) {
        petItemService.update(petItemId, petItemDTO);
        return ResponseEntity.ok(petItemId);
    }
    //삭제
    @DeleteMapping("/{petItemId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePetItem(
            @PathVariable(name = "petItemId") final Integer petItemId) {
        final ReferencedWarning referencedWarning = petItemService.getReferencedWarning(petItemId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        petItemService.delete(petItemId);
        return ResponseEntity.noContent().build();
    }

    // 사진 업로드 등록

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    @PostMapping
    public ResponseEntity<?> uploadPet(@RequestParam("name") String name,
                                       @RequestParam("description") String description,
                                       @RequestParam("user") Integer user,
                                       @RequestParam("price") Integer price,
                                       @RequestParam("sharing") Integer sharing,
                                       @RequestParam(value = "imageUrl", required = false) MultipartFile file){
        try {
            PetItemDTO petItemDTO = new PetItemDTO();
            petItemDTO.setName(name);
            petItemDTO.setDescription(description);
            petItemDTO.setPrice(price);
            petItemDTO.setSharing(sharing);
            petItemDTO.setUser(user);  // user ID 설정
            petItemDTO.setCreatedAt(LocalDateTime.now());  // 현재 시간으로 생성일 설정

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
                petItemDTO.setImageUrl(ss); // 파일 이름만 저장
                System.out.println("ss="+ss);
            }

            final Integer createdPetItemId = petItemService.create(petItemDTO);
            return new ResponseEntity<>(createdPetItemId, HttpStatus.CREATED);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not upload the petItem: " + e.getMessage());
        }
    }


}
