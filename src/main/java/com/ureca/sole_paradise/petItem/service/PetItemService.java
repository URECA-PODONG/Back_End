package com.ureca.sole_paradise.petItem.service;

import com.ureca.sole_paradise.petItem.db.dto.PetItemDTO;
import com.ureca.sole_paradise.petItem.db.entity.PetItemCommentEntity;
import com.ureca.sole_paradise.petItem.db.entity.PetItemEntity;
import com.ureca.sole_paradise.petItem.db.repository.PetItemCommentRepository;
import com.ureca.sole_paradise.petItem.db.repository.PetItemRepository;
import com.ureca.sole_paradise.s3.dto.FileInfoDto;
import com.ureca.sole_paradise.s3.dto.S3Option;
import com.ureca.sole_paradise.s3.service.S3Service;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.user.db.repository.UserRepository;
import com.ureca.sole_paradise.util.NotFoundException;
import com.ureca.sole_paradise.util.ReferencedWarning;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PetItemService {

    private final S3Service s3service;
    private final PetItemRepository petItemRepository;
    private final UserRepository userRepository;
    private final PetItemCommentRepository petItemCommentRepository;

    public PetItemService(S3Service s3service, final PetItemRepository petItemRepository,
                          final UserRepository userRepository,
                          final PetItemCommentRepository petItemCommentRepository) {
        this.s3service = s3service;
        this.petItemRepository = petItemRepository;
        this.userRepository = userRepository;
        this.petItemCommentRepository = petItemCommentRepository;
    }

    public List<PetItemDTO> findAll() {
        final List<PetItemEntity> petItemEntites = petItemRepository.findAll(Sort.by("petItemId"));
        return petItemEntites.stream()
                .map(petItemEntity -> mapToDTO(petItemEntity, new PetItemDTO()))
                .toList();
    }

    public PetItemDTO get(final Integer petItemId) {
        return petItemRepository.findById(petItemId)
                .map(petItemEntity -> mapToDTO(petItemEntity, new PetItemDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PetItemDTO petItemDTO) {
        final PetItemEntity petItemEntity = new PetItemEntity();
        mapToEntity(petItemDTO, petItemEntity);
        return petItemRepository.save(petItemEntity).getPetItemId();
    }

    public void update(final Integer petItemId, final PetItemDTO petItemDTO) {
        final PetItemEntity petItemEntity = petItemRepository.findById(petItemId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(petItemDTO, petItemEntity);
        petItemRepository.save(petItemEntity);
    }

    public void delete(final Integer petItemId) {
        petItemRepository.deleteById(petItemId);
    }

    private PetItemDTO mapToDTO(final PetItemEntity petItemEntity, final PetItemDTO petItemDTO) {
        petItemDTO.setPetItemId(petItemEntity.getPetItemId());
        petItemDTO.setName(petItemEntity.getName());
        petItemDTO.setDescription(petItemEntity.getDescription());
        petItemDTO.setImageUrl(petItemEntity.getImageUrl());
        petItemDTO.setStatus(petItemEntity.getStatus());
        petItemDTO.setPrice(petItemEntity.getPrice());
        petItemDTO.setGood(petItemEntity.getGood());
        petItemDTO.setSharing(petItemEntity.getSharing());
        petItemDTO.setNanum(petItemEntity.getNanum());
        petItemDTO.setCreatedAt(petItemEntity.getCreatedAt());  // createdAt 매핑 추가
        petItemDTO.setUpdatedAt(petItemEntity.getUpdatedAt());  // updatedAt 매핑 추가
        petItemDTO.setUser(petItemEntity.getUser() == null ? null : petItemEntity.getUser().getUserId());
        return petItemDTO;
    }

    private PetItemEntity mapToEntity(final PetItemDTO petItemDTO, final PetItemEntity petItemEntity) {
        petItemEntity.setName(petItemDTO.getName());
        petItemEntity.setDescription(petItemDTO.getDescription());
        petItemEntity.setImageUrl(petItemDTO.getImageUrl());
        petItemEntity.setStatus(petItemDTO.getStatus());
        petItemEntity.setPrice(petItemDTO.getPrice());
        petItemEntity.setGood(petItemDTO.getGood());
        petItemEntity.setSharing(petItemDTO.getSharing());
        petItemEntity.setNanum(petItemDTO.getNanum());
        final UserEntity user = petItemDTO.getUser() == null ? null : userRepository.findById(petItemDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        petItemEntity.setUser(user);
        return petItemEntity;
    }

    public ReferencedWarning getReferencedWarning(final Integer petItemId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PetItemEntity petItem = petItemRepository.findById(petItemId)
                .orElseThrow(NotFoundException::new);
        final PetItemCommentEntity petItemPetItemComment = petItemCommentRepository.findFirstByPetItem(petItem);
        if (petItemPetItemComment != null) {
            referencedWarning.setKey("petItem.petItemComment.petItem.referenced");
            referencedWarning.addParam(petItemPetItemComment.getPetItemCommentId());
            return referencedWarning;
        }
        return null;
    }

    public String petItemImgUpload(MultipartFile multipartFile, int id) {
        FileInfoDto fileInfoDto = FileInfoDto.builder()
                .file(multipartFile)
                .allowedMimeTypes(List.of("image/jpg", "image/jpeg", "image/png"))
                .id(id)
                .s3Option(S3Option.petItemImgUpload).build();
        String uploadURL = s3service.fileUpload(fileInfoDto);
        return uploadURL;
    }

}
