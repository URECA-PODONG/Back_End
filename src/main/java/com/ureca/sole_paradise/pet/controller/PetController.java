package com.ureca.sole_paradise.pet.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ureca.sole_paradise.pet.db.dto.PetDTO;
import com.ureca.sole_paradise.pet.service.PetService;
import com.ureca.sole_paradise.util.ReferencedException;
import com.ureca.sole_paradise.util.ReferencedWarning;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/pets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetController {

	private final PetService petService;

	public PetController(final PetService petService) {
		this.petService = petService;
	}

	@GetMapping
	public ResponseEntity<List<PetDTO>> getAllPets() {
		return ResponseEntity.ok(petService.findAll());
	}

	@GetMapping("/{petId}")
	public ResponseEntity<PetDTO> getPet(@PathVariable(name = "petId") final Integer petId) {
		return ResponseEntity.ok(petService.get(petId));
	}


   /*@PostMapping
   @ApiResponse(responseCode = "201")
   public ResponseEntity<Integer> createPet(@RequestBody @Valid final PetDTO petDTO) {
       final Integer createdPetId = petService.create(petDTO);
       return new ResponseEntity<>(createdPetId, HttpStatus.CREATED);
   }*/

	@PutMapping("/{petId}")
	public ResponseEntity<Integer> updatePet(
			@PathVariable(name = "petId") final Integer petId,
			@RequestParam("petName") String petName,
			@RequestParam("petAge") int petAge,
			@RequestParam("dogOrCat") String dogOrCat,
			@RequestParam("petType") String petType,
			@RequestParam("petWeight") int petWeight,
			@RequestParam("neutering") boolean neutering,
			@RequestParam("petAllergy") boolean petAllergy,
			@RequestParam("gender") boolean gender,
			@RequestParam("user") int user,
			@RequestParam("createdAt") @NotNull OffsetDateTime createdAt,
			@RequestParam(value = "updatedAt", required = false) OffsetDateTime updatedAt,
			@RequestParam(value = "image", required = false) MultipartFile file) {

		PetDTO petDTO = new PetDTO();
		petDTO.setPetName(petName);
		petDTO.setPetAge(petAge);
		petDTO.setDogOrCat(dogOrCat);
		petDTO.setPetType(petType);
		petDTO.setPetWeight(petWeight);
		petDTO.setNeutering(neutering);
		petDTO.setPetAllergy(petAllergy);
		petDTO.setGender(gender);
		petDTO.setCreatedAt(createdAt);
		petDTO.setUser(user);
		petDTO.setUpdatedAt(updatedAt != null ? updatedAt : OffsetDateTime.now());

		petDTO.setPetPicture(petService.petImageUpload(file, petId));
		// 파일 처리 로직 추가
		/*if (file != null && !file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR + fileName);
			try {
				Files.createDirectories(filePath.getParent());
				Files.copy(file.getInputStream(), filePath);
				petDTO.setPetPicture("uploads/" + fileName);
			} catch (IOException e) {

			}
		}*/

		// 업데이트 호출
		petService.update(petId, petDTO);
		return ResponseEntity.ok(petId);
	}

	@DeleteMapping("/{petId}")
	@ApiResponse(responseCode = "204")
	public ResponseEntity<Void> deletePet(@PathVariable(name = "petId") final Integer petId) {
		final ReferencedWarning referencedWarning = petService.getReferencedWarning(petId);
		if (referencedWarning != null) {
			throw new ReferencedException(referencedWarning);
		}
		petService.delete(petId);
		return ResponseEntity.noContent().build();
	}

	private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

	@PostMapping
	public ResponseEntity<?> uploadPet(
			@RequestParam("petName") String petName,
			@RequestParam("petAge") int petAge,
			@RequestParam("dogOrCat") String dogOrCat,
			@RequestParam("petType") String petType,
			@RequestParam("petWeight") int petWeight,
			@RequestParam("neutering") boolean neutering,
			@RequestParam("petAllergy") boolean petAllergy,
			@RequestParam("gender") boolean gender,
			@RequestParam("user") int user,  // User ID를 받음\
			@RequestParam("createdAt") @NotNull OffsetDateTime createdAt,
			@RequestParam(value = "image", required = false)
			MultipartFile file) {
        PetDTO petDTO = new PetDTO();
        petDTO.setPetName(petName);
        petDTO.setPetAge(petAge);
        petDTO.setDogOrCat(dogOrCat);
        petDTO.setPetType(petType);
        petDTO.setPetWeight(petWeight);
        petDTO.setNeutering(neutering);
        petDTO.setPetAllergy(petAllergy);
        petDTO.setGender(gender);
        petDTO.setCreatedAt(createdAt);
        petDTO.setUser(user);  // user ID 설정

			/*if (file != null && !file.isEmpty()) {
				String fileName = System.currentTimeMillis() + "";
				String[] exts = file.getOriginalFilename().split("\\\\.");
				String ext = exts[exts.length - 1]; // 확장자
				Path filePath = Paths.get(UPLOAD_DIR + fileName + "." + ext);
				Files.createDirectories(filePath.getParent());
				Files.copy(file.getInputStream(), filePath);
				String fp = filePath.toString();
				System.out.println("fp=" + fp);
				int staticIndex = fp.lastIndexOf("uploads");
				String ss = fp.substring(staticIndex + 8);
				petDTO.setPetPicture(ss);
				System.out.println("ss=" + ss);
			}*/


        final Integer createdPetId = petService.create(petDTO);

        petDTO.setPetPicture(petService.petImageUpload(file, createdPetId));
		petService.update(createdPetId, petDTO);
        return new ResponseEntity<>(createdPetId, HttpStatus.CREATED);

    }
}


