package com.ureca.sole_paradise.pet.service;

import java.util.ArrayList;
import java.util.List;

import com.ureca.sole_paradise.s3.dto.FileInfoDto;
import com.ureca.sole_paradise.s3.dto.S3Option;
import com.ureca.sole_paradise.s3.service.S3Service;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ureca.sole_paradise.health.db.entity.HealthEntity;
import com.ureca.sole_paradise.health.db.repository.HealthRepository;
import com.ureca.sole_paradise.pet.db.dto.PetDTO;
import com.ureca.sole_paradise.pet.db.entity.PetEntity;
import com.ureca.sole_paradise.pet.db.repository.PetRepository;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.user.db.repository.UserRepository;

import com.ureca.sole_paradise.util.NotFoundException;
import com.ureca.sole_paradise.util.ReferencedWarning;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final HealthRepository healthRepository;
    private final S3Service s3service;

    public PetService(final PetRepository petRepository, final UserRepository userRepository,
                      final HealthRepository healthRepository, S3Service s3service) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.healthRepository = healthRepository;
        this.s3service = s3service;
    }

    public List<PetDTO> getMyPet(final Integer userId) {
        List<PetEntity> petEntityList = petRepository.findByUser_UserId(userId);
        List<PetDTO> petDTOList = new ArrayList<>();
        for (PetEntity petEntity : petEntityList) {
            petDTOList.add(mapToDTO(petEntity, new PetDTO()));
        }
        return petDTOList;
    }

    public List<PetDTO> findAll() {
        final List<PetEntity> pets = petRepository.findAll(Sort.by("petId"));
        return pets.stream()
            .map(pet -> mapToDTO(pet, new PetDTO()))
            .toList();
    }

    public PetDTO get(final Integer petId) {
        return petRepository.findById(petId)
            .map(pet -> mapToDTO(pet, new PetDTO()))
            .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PetDTO petDTO) {
        final PetEntity pet = new PetEntity();
        mapToEntity(petDTO, pet);
        return petRepository.save(pet).getPetId();
    }

    public void update(final Integer petId, final PetDTO petDTO) {
        final PetEntity pet = petRepository.findById(petId)
            .orElseThrow(NotFoundException::new);
        mapToEntity(petDTO, pet);
        petRepository.save(pet);
    }

    public void delete(final Integer petId) {
        petRepository.deleteById(petId);
    }

    private PetDTO mapToDTO(final PetEntity pet, final PetDTO petDTO) {
        petDTO.setPetId(pet.getPetId());
        petDTO.setPetName(pet.getPetName());
        petDTO.setPetAge(pet.getPetAge());
        petDTO.setDogOrCat(pet.getDogOrCat());
        petDTO.setPetType(pet.getPetType());
        petDTO.setPetPicture(pet.getPetPicture());
        petDTO.setPetWeight(pet.getPetWeight());
        petDTO.setNeutering(pet.getNeutering());
        petDTO.setPetAllergy(pet.getPetAllergy());
        petDTO.setGender(pet.getGender());
        petDTO.setUser(pet.getUser() == null ? null : pet.getUser().getUserId());
        return petDTO;
    }

    private PetEntity mapToEntity(final PetDTO petDTO, final PetEntity pet) {
        pet.setPetName(petDTO.getPetName());
        pet.setPetAge(petDTO.getPetAge());
        pet.setDogOrCat(petDTO.getDogOrCat());
        pet.setPetType(petDTO.getPetType());
        pet.setPetPicture(petDTO.getPetPicture());
        pet.setPetWeight(petDTO.getPetWeight());
        pet.setNeutering(petDTO.getNeutering());
        pet.setPetAllergy(petDTO.getPetAllergy());
        pet.setGender(petDTO.getGender());
        final UserEntity user = petDTO.getUser() == null ? null : userRepository.findById(petDTO.getUser())
            .orElseThrow(() -> new NotFoundException("user not found"));
        pet.setUser(user);
        return pet;
    }

    public ReferencedWarning getReferencedWarning(final Integer petId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PetEntity pet = petRepository.findById(petId)
            .orElseThrow(NotFoundException::new);

        final HealthEntity petHealth = healthRepository.findFirstByPet(pet);
        if (petHealth != null) {
            referencedWarning.setKey("pet.health.pet.referenced");
            referencedWarning.addParam(petHealth.getHealthId());
            return referencedWarning;
        }

        return null;
    }
    public String petImageUpload(MultipartFile multipartFile, int id) {
        FileInfoDto fileInfoDto = FileInfoDto.builder()
                .file(multipartFile)
                .allowedMimeTypes(List.of("image/jpg", "image/jpeg", "image/png"))
                .id(id)
                .s3Option(S3Option.petImgUpload).build();
        String uploadURL = s3service.fileUpload(fileInfoDto);
        return uploadURL;
    }

}
